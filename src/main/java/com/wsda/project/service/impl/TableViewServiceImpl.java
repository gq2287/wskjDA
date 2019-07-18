package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.dao.SystemCataLogMapper;
import com.wsda.project.dao.SystemNoFormatMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.Dictionary;
import com.wsda.project.model.*;
import com.wsda.project.service.TableViewService;
import com.wsda.project.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TableViewServiceImpl implements TableViewService {
    private Logger logger = LoggerFactory.getLogger(TableViewServiceImpl.class);
    @Resource
    private TableViewMapper tableViewMapper;
    @Resource
    private ClassTreeMapper classTreeMapper;
    @Resource
    private ClassTreeServiceImpl classTreeService;
    @Resource
    private DepartementServiceImpl departementService;
    @Resource
    private DictionaryServiceImpl dictionaryService;
    @Resource
    private SystemCataLogMapper systemCataLogMapper;
    @Resource
    private SystemNoFormatMapper systemNoFormatMapper;


    /**
     * 获取当前实体表列
     *
     * @param tableCode
     * @return
     */
    @Override
    public Map<String, Object> getTableView(String tableCode, int pageNum, int PageSize, List<Map<String, String>> conditions, List<Map<String, String>> sorts, String type, HttpServletRequest request) {
        HttpSession session = request.getSession();
        SystemUser systemUser = (SystemUser) session.getAttribute("user");//获取当前登录用户 权限时会用到
        List<SystemNoFormat> systemNoFormatList = systemNoFormatMapper.getSystemNoFormatListByTableCode(tableCode);//获取档号
        List<String> inputCardFieldNameList = tableViewMapper.getInputCardFieldName(tableCode);
        Map<String, Object> mapObj = new HashMap<>();//返回结果集
        String archiveFlagType = null;//当前查询归档类型
        List<Map<String, String>> arrayList = new ArrayList<>();
        try {
            arrayList = tableViewMapper.getTableView(tableCode);//获取展示列
            Map<String, String> columnMap = new HashMap<>();
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    for (String str : arrayList.get(i).keySet()) {
                        if ("NAME".equals(str)) {
                            arrayList.get(i).put(str, arrayList.get(i).get(str).toUpperCase());//转小写
                            columnMap.put(arrayList.get(i).get(str), arrayList.get(i).get(str));
                        }
                    }
                }
            }

            //Start
            StringBuffer whereSql = new StringBuffer();//查询条件   RECORDCODE添加的唯一主键
            StringBuffer sortSql = new StringBuffer();//排序条件
            Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
            dataType.put("1", "VARCHAR2");
            dataType.put("2", "NUMBER");
            dataType.put("3", "NUMBER");
            dataType.put("4", "DATE");
            dataType.put("5", "VARCHAR2");
            //处理查询的条件
            if (conditions != null && conditions.size() > 0) {
                for (int i = 0; i < conditions.size(); i++) {
                    for (String cod : conditions.get(i).keySet()) {
                        String startColumn = cod.substring(0, cod.lastIndexOf("-"));//获取数据列名
                        String endType = cod.substring(cod.lastIndexOf("-") + 1, cod.length());//获取数据类型
                        String valueStr = conditions.get(i).get(cod);//键对应值
                        if ("待整理".equals(valueStr)) {
                            //判断未整理
                            if (systemNoFormatList != null) {
                                for (int j = 0; j < inputCardFieldNameList.size(); j++) {
                                    if ("ARCHIVE_FLAG".equalsIgnoreCase(inputCardFieldNameList.get(j)) || "ARCHIVEFLAG".equalsIgnoreCase(inputCardFieldNameList.get(j))) {
                                        whereSql.append(" " + inputCardFieldNameList.get(j) + " != ");
                                        whereSql.append("'已归档' AND　" + inputCardFieldNameList.get(j) + " != '不归档'");//值
                                        if (j != inputCardFieldNameList.size() - 1) {
                                            whereSql.append(" AND ");
                                        }
                                    }
                                }
                                //判断档号项是否完整
                                whereSql.append("(");
                                for (int j = 0; j < systemNoFormatList.size(); j++) {
                                    if ("pieceNo".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) || "yearfolderno".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) || "pageno1".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) || "jh".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName())) {
                                        continue;//判断件号
                                    } else if ("pageNumber".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "quantity".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "YESHU".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName())) {
                                        continue;//判断页号
                                    } else {
                                        whereSql.append(systemNoFormatList.get(j).getColumnName());//列名
                                        whereSql.append(" IS  NULL ");//如果档号项为空
                                        whereSql.append(" OR ");
                                    }
                                }
                                String strTemp = whereSql.toString().replace(" ", "");
                                strTemp = strTemp.substring(strTemp.length() - 2, strTemp.length());
                                if ("OR".equalsIgnoreCase(strTemp)) {//判断最后循环结束多加了OR
                                    whereSql = whereSql.replace(whereSql.lastIndexOf("OR "), whereSql.length(), "");
                                }
                                whereSql.append(")");
                            }
                            logger.info("待整理SQL:{}", whereSql);
                        } else if ("待归档".equals(valueStr)) {
                            archiveFlagType = "待归档";
                            //判断未整理
                            if (systemNoFormatList != null) {
                                columnMap = new HashMap<>();//重置查询列为档号+页数+题名
                                columnMap.put("mainTitle".toLowerCase(), "mainTitle".toLowerCase());
                                for (int j = 0; j < inputCardFieldNameList.size(); j++) {
                                    if ("pageNumber".equalsIgnoreCase(inputCardFieldNameList.get(j)) ||
                                            "quantity".equalsIgnoreCase(inputCardFieldNameList.get(j)) ||
                                            "YESHU".equalsIgnoreCase(inputCardFieldNameList.get(j))) {
                                        columnMap.put(inputCardFieldNameList.get(j).toLowerCase(), inputCardFieldNameList.get(j).toLowerCase());
                                    }
                                }
                                for (int j = 0; j < inputCardFieldNameList.size(); j++) {
                                    if ("ARCHIVE_FLAG".equalsIgnoreCase(inputCardFieldNameList.get(j)) || "ARCHIVEFLAG".equalsIgnoreCase(inputCardFieldNameList.get(j))) {
                                        whereSql.append(" " + inputCardFieldNameList.get(j) + " = ");
                                        whereSql.append("'未归档'");//值
                                        if (j != inputCardFieldNameList.size() - 1) {
                                            whereSql.append(" AND ");
                                        }
                                    }
                                }
                                //判断档号项是否完整
                                whereSql.append("(");
                                for (int j = 0; j < systemNoFormatList.size(); j++) {
                                    //添加档号为查询列
                                    columnMap.put(systemNoFormatList.get(j).getColumnName().toLowerCase(), systemNoFormatList.get(j).getColumnName().toLowerCase());
                                    if ("pieceNo".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "yearfolderno".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "pageno1".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "jh".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName())) {
                                        continue;//判断件号
                                    } else if ("pageNumber".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "quantity".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName()) ||
                                            "YESHU".equalsIgnoreCase(systemNoFormatList.get(j).getColumnName())) {
                                        continue;//判断页号
                                    } else {
                                        whereSql.append(systemNoFormatList.get(j).getColumnName());//列名
                                        whereSql.append(" IS NOT NULL ");//如果档号项为空
                                        whereSql.append(" AND ");
                                    }
                                }
                                String strTemp = whereSql.toString().replace(" ", "");
                                strTemp = strTemp.substring(strTemp.length() - 3, strTemp.length());
                                if ("AND".equalsIgnoreCase(strTemp)) {//判断最后循环结束多加了OR
                                    whereSql = whereSql.replace(whereSql.lastIndexOf("AND "), whereSql.length(), "");
                                }
                                whereSql.append(")");
                            }
                            logger.info("待归档SQL:{}", whereSql);
                        } else if ("不归档".equals(valueStr)) {
                            for (int j = 0; j < inputCardFieldNameList.size(); j++) {
                                if ("ARCHIVE_FLAG".equalsIgnoreCase(inputCardFieldNameList.get(j)) || "ARCHIVEFLAG".equalsIgnoreCase(inputCardFieldNameList.get(j))) {
                                    whereSql.append(" " + inputCardFieldNameList.get(j) + " = ");
                                    whereSql.append("'不归档'");//值
                                    if (j != inputCardFieldNameList.size() - 1) {
                                        whereSql.append(" AND ");
                                    }
                                }
                            }
                            String strTemp = whereSql.toString().replace(" ", "");
                            strTemp = strTemp.substring(strTemp.length() - 3, strTemp.length());
                            if ("AND".equalsIgnoreCase(strTemp)) {//判断最后循环结束多加了OR
                                whereSql = whereSql.replace(whereSql.lastIndexOf("AND "), whereSql.length(), "");
                            } else {
                                whereSql.append(")");
                            }
                            logger.info("不归档SQL:{}", whereSql);
                        } else if (dataType.containsKey(endType)) {//包涵当前的类型
                            if ("1".equals(endType) || "2".equals(endType) || "3".equals(endType)) {
                                if ("ARCHIVE_FLAG".equalsIgnoreCase(startColumn) || "ARCHIVEFLAG".equalsIgnoreCase(startColumn)) {
                                    whereSql.append(" " + startColumn + " = ");
                                    whereSql.append("'" + valueStr + "'");//值
                                    if (i != conditions.size() - 1) {
                                        whereSql.append(" AND ");
                                    }
                                } else {
                                    whereSql.append(startColumn);//列名
                                    whereSql.append(" like ");
                                    whereSql.append("'%" + valueStr + "%'");//值
                                }
                                if (i != conditions.size() - 1) {
                                    whereSql.append(" AND ");
                                }
                            } else if ("4".equals(endType)) {
                                String valueStart = valueStr.substring(0, valueStr.lastIndexOf("@"));
                                String valueEnd = valueStr.substring(valueStr.lastIndexOf("@") + 1, valueStr.length());//获取数据类型
                                whereSql.append(" to_date(to_char(" + startColumn + ", 'yyyy-MM-dd'), 'yyyy-mm-dd') BETWEEN to_date('" + valueStart + "', 'yyyy-mm-dd') AND to_date('" + valueEnd + "', 'yyyy-mm-dd') ");//值
                                if (i != conditions.size() - 1) {
                                    whereSql.append(" or ");
                                }
                            } else if ("5".equals(endType)) {
                                String valueStart = valueStr.substring(0, valueStr.lastIndexOf("@"));
                                String valueEnd = valueStr.substring(valueStr.lastIndexOf("@") + 1, valueStr.length());//获取数据类型
                                whereSql.append(" to_date(to_char(" + startColumn + ", 'yyyy-MM-dd'), 'yyyy-mm-dd') BETWEEN TO_DATE('" + valueStart + "', 'yyyy-mm-dd') AND TO_DATE('" + valueEnd + "','yyyy-mm-dd') ");//值
                                if (i != conditions.size() - 1) {
                                    whereSql.append(" or ");
                                }
                            }
                            logger.info("已归档SQL:{}", whereSql);
                        }
                    }
                }
            }
            //处理排序的条件
            if (sorts != null && sorts.size() > 0) {
                sortSql.append(" ORDER BY ");
                for (int i = 0; i < sorts.size(); i++) {
                    for (String so : sorts.get(i).keySet()) {
                        sortSql.append(so);//列名
                        sortSql.append(" " + sorts.get(i).get(so));
                        if (i != sorts.size() - 1) {
                            sortSql.append(", ");
                        }
                    }
                }
            }
            logger.info("查询档案SQL:{}", whereSql);
            logger.info("查询档案排序SQL:{}", sortSql);
            //End

            //业务
            String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//获取实体表名称
            if (tableName != null) {
                columnMap.put("RECORDCODE", "RECORDCODE");
                PageHelper.startPage(pageNum, PageSize);//分页
                PageInfo<Map<String, String>> listPageInfo;
                listPageInfo = new PageInfo<>(tableViewMapper.getTableInfo(tableName, columnMap, String.valueOf(whereSql), String.valueOf(sortSql), type, null));//存放入分页的pageInfo中
                mapObj.put("tableColums", arrayList);//展示列
                if (archiveFlagType == null) {
                    setDepartementName(listPageInfo);
                }//转换查询列的部门编号为部门名称
                toDataByTime(arrayList, listPageInfo);//转换日期格式
                mapObj.put("pageInfo", listPageInfo);//实体表内容
                mapObj.put("sorts", sorts);//排序纪录
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("查询视图列失败:" + e.getMessage() + "tableCode");
        }
        return mapObj;
    }


    /**
     * 获取视图树
     *
     * @return
     */
    @Override
    public Tree getTreeMenu() {
        //获取树菜单，根据树菜单获取底层门类获取旗下实体表table
        List<Map<String, String>> listTop = classTreeMapper.getClassITop();//获取顶级节点
        Map<String, String> parmMap = new HashMap<>();//参数map
        List<Tree> treeList = new ArrayList<>();//节点集合
        Tree rootTree = new Tree();
        for (int i = 0; i < listTop.size(); i++) {
            parmMap.put("TYPE", listTop.get(i).get("TYPE"));
            parmMap.put("nodeCode", listTop.get(i).get("NODECODE"));
            rootTree.setId(listTop.get(i).get("NODECODE"));//编号
            rootTree.setText(listTop.get(i).get("NAME"));//名称
        }
        List<Map<String, String>> classI = classTreeMapper.getClassI(parmMap);//根节点 5棵
        for (int i = 0; i < classI.size(); i++) {
            Map<String, String> classIMap = classI.get(i);
            Tree treeI = new Tree();
            Map<String, String> attrIs = new HashMap<>();// treeI树其他属性
            for (String cI : classIMap.keySet()) {
                attrIs.put(cI, classIMap.get(cI));//当前树全部属性
                if ("NODECODE".equals(cI)) {
                    treeI.setId(classIMap.get("NODECODE"));//获取树编号
                    treeI.setText(classIMap.get("NAME"));//获取树名称
                    List<Map<String, String>> classCL = classTreeMapper.getClassCL(classIMap.get("NODECODE"));// 获取到 treeI 子节点
                    List<Tree> treeCLList = new ArrayList<>();//treeI二级节点集合
                    for (int j = 0; j < classCL.size(); j++) {
                        Tree treeCL = new Tree();//当前树
                        for (String jm : classCL.get(j).keySet()) {
                            treeCL.setText(classCL.get(j).get("NAME"));//获取treeCL树名称
                            treeCL.setId(classCL.get(j).get("NODECODE"));//获取树编号
                            if ("NODECODE".equals(jm)) {
                                List<Tree> newList = classTreeService.getCLBynodeCode(classCL.get(j).get(jm));//获取当前节点nodecode
                                if (newList != null && newList.size() > 0) {
                                    for (int k = 0; k < newList.size(); k++) {
                                        Map<String, String> newMap = (Map<String, String>) newList.get(k).getLi_attr();
                                        for (String et : newMap.keySet()) {
                                            if (newMap.get(et) != null && "C".equals(newMap.get(et))) {
                                                List<Tree> treeELists = new ArrayList<>();//treeE节点集合
                                                List<Map<String, String>> ETreeList = classTreeService.getTableByNodeCode(newMap.get("NODECODE"));//获取旗下实体表
                                                //获取nodecode全部实体
                                                for (int l = 0; l < ETreeList.size(); l++) {
                                                    Tree treeEE = new Tree();
                                                    treeEE.setId(ETreeList.get(l).get("NODECODE"));
                                                    treeEE.setText(ETreeList.get(l).get("NAME"));
                                                    treeEE.setLi_attr(ETreeList.get(l));//获取全部属性
                                                    treeELists.add(treeEE);
                                                }
                                                newList.get(k).setChildren(treeELists);
                                            } else {
                                                continue;
                                            }
                                        }
                                    }
                                    treeCL.setChildren(newList);
                                } else {
                                    if (classCL.get(j).get(jm) != null && "C".equals(classCL.get(j).get("TYPE"))) {
                                        List<Tree> treeELists = new ArrayList<>();//treeE节点集合
                                        List<Map<String, String>> ETreeList = classTreeService.getTableByNodeCode(classCL.get(j).get("NODECODE"));//获取旗下实体表
                                        //获取nodecode全部实体
                                        for (int l = 0; l < ETreeList.size(); l++) {
                                            Tree treeEE = new Tree();
                                            treeEE.setId(ETreeList.get(l).get("NODECODE"));
                                            treeEE.setText(ETreeList.get(l).get("NAME"));
                                            treeEE.setLi_attr(ETreeList.get(l));//获取全部属性
                                            treeELists.add(treeEE);
                                        }
                                        treeCL.setChildren(treeELists);
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                        treeCL.setLi_attr(classCL.get(j));
                        treeCLList.add(treeCL);
                        treeI.setChildren(treeCLList);
                    }
                }
            }
            treeI.setLi_attr(attrIs);
            treeList.add(treeI);
        }
        rootTree.setLi_attr(parmMap);
        rootTree.setChildren(treeList);//节点集合放入根节点
        return rootTree;
    }


    /**
     * 获取录入界面
     *
     * @param tableCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getInputCard(String tableCode) {
        List<Map<String, Object>> inputCardList = tableViewMapper.getInputCard(tableCode);//获取录入列表
        List<Map<String, Object>> newInputCardList = new ArrayList<>();//返回集合
        if (inputCardList != null && inputCardList.size() > 0) {
            for (int i = 0; i < inputCardList.size(); i++) {
                Map<String, Object> stringMap = inputCardList.get(i);
                for (String name : stringMap.keySet()) {
                    if (stringMap.get(name) != null && "FIELDNAME".equals(name) && !"null".equals(stringMap.get(name))) {
                        //获取录入类型
                        List<Map<String, String>> inputTypelist = tableViewMapper.getInputTypeByTableCodeAndName(tableCode, String.valueOf(stringMap.get(name)));
                        if (inputTypelist != null && inputTypelist.size() > 0) {
                            for (int j = 0; j < inputTypelist.size(); j++) {
                                for (String input : inputTypelist.get(j).keySet()) {
                                    if ("TYPE".equals(input) && inputTypelist.get(j).get(input) != null) {
                                        stringMap.put("TYPE", inputTypelist.get(j).get(input));
                                    } else if ("CANNULL".equals(input) && inputTypelist.get(j).get(input) != null) {
                                        stringMap.put("CANNULL", inputTypelist.get(j).get(input));
                                    } else if ("INPUTTYPE".equals(input) && inputTypelist.get(j).get(input) != null) {
                                        stringMap.put("INPUTTYPE", inputTypelist.get(j).get(input));
                                        if ("S".equals(inputTypelist.get(j).get(input))) {
                                            String dictName = String.valueOf(stringMap.get("PROPERTIESINFO1"));
                                            if (dictName != null && !"".equals(dictName)) {
//                                                根据名称查询对应的字典
                                                List<Dictionary> dictionaryList = dictionaryService.getAllDictionaryData(dictName);
                                                stringMap.put("PROPERTIESINFO2", dictionaryList);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        newInputCardList.add(stringMap);
                        break;
                    }
                }
            }
        }
        return newInputCardList;
    }

    /**
     * 添加档案
     *
     * @param infoMap
     * @return
     */
    @Transactional
    @Override
    public boolean addTableInfo(Map<String, String> infoMap) {
        boolean bool = true;
        Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
        dataType.put("1", "VARCHAR2");
        dataType.put("2", "NUMBER");
        dataType.put("3", "NUMBER");
        dataType.put("4", "DATE");
        dataType.put("5", "VARCHAR2");
        String tableCode = infoMap.get("tableCode");//获取表编号
        infoMap.remove("tableCode");
        //获取表名
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        List<String> columnList = new ArrayList<>();//添加表 字段列集合
        List<String> valuesList = new ArrayList<>();//添加表 字段列对应值集合
        columnList.add("RECORDCODE");//主键
        valuesList.add("'" + StringUtil.getRandomStr(8) + "'");//唯一主键

        infoMap.put("TRASHSTATUS-1", "0");//回收站标志 0默认，1删除
        infoMap.put("OPERATETIME-5", StringUtil.getDate(1));//操作日期
        for (String column : infoMap.keySet()) {
            //例：RECORDCODE-1
            String startColumn = column.substring(0, column.lastIndexOf("-"));//获取数据列名
            if (infoMap.get(column) != null && !"".equals(infoMap.get(column))) {
                columnList.add(startColumn);//添加表字段列
                String endType = column.substring(column.lastIndexOf("-") + 1, column.length());//获取数据类型
                if (dataType.containsKey(endType)) {//包涵当前的类型
                    String typeName = dataType.get(endType);//类型名称
                    String value = infoMap.get(column);//获得值
                    if ("VARCHAR2".equals(typeName)) {
                        valuesList.add("'" + value + "'");
                    } else if ("NUMBER".equals(typeName)) {
                        valuesList.add(value);
                    } else if ("DATE".equals(typeName)) {
                        if ("createDate".equalsIgnoreCase(startColumn)) {
                            valuesList.add("TO_DATE('" + value + "','YYYYMMDD')");
                        } else {
                            valuesList.add("TO_DATE('" + value + "','YYYY-MM-DD')");
                        }

                    }
                }
            }
        }
        bool = tableViewMapper.addTableInfo(tableName, columnList, valuesList);
        return bool;
    }

    @Override
    public List<Map<String, String>> getAllSystemFonds() {
        return systemCataLogMapper.getAllSystemFonds();
    }

    /**
     * 查询实体分类号
     */
    @Override
    public List<Tree> getAllSystemFondsTree(String fondsCode) {
        List<Map<String, String>> departementList = systemCataLogMapper.getAllSystemCataLogByFondsCode(null, fondsCode);
        List<Tree> treeList = new ArrayList<>(departementList.size());//存放节点树
        if (departementList != null && departementList.size() > 0) {
            for (int i = 0; i < departementList.size(); i++) {
                Tree rootTree = new Tree();//根节点
                rootTree.setId(departementList.get(i).get("DEPARTMENTCODE"));
                rootTree.setText(departementList.get(i).get("NAME"));
                rootTree.setLi_attr(departementList.get(i));
                treeList.add(rootTree);
                List<Tree> treeListChildren = getSystemFondsTree(departementList.get(i).get("DEPARTMENTCODE"));//获取旗下子节点
                if (treeListChildren != null && treeListChildren.size() > 0) {//获取旗下子节点
                    rootTree.setChildren(treeListChildren);//存放节点
                } else {
                    rootTree.setChildren(null);//存放节点
                }
            }
            return treeList;
        } else {
            return null;
        }
    }

    /**
     * 删除恢复当前档案
     *
     * @param tableCode   表编号
     * @param recordCode  修改条目的主键
     * @param trashStatus 0恢复，1删除
     * @param type        判断是档案条目还是亚原文条目（0，1）
     * @return
     */
    @Transactional
    @Override
    public boolean upArchives(String tableCode, List<String> recordCode, String trashStatus, int type) throws Exception {
        boolean bool = true;
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        if (recordCode != null) {
            for (int i = 0; i < recordCode.size(); i++) {
                if (bool) {
                    if (type == 0) {//档案表
                        bool = tableViewMapper.upArchives(tableName, recordCode.get(i), trashStatus, "RECORDCODE");
                    } else if (type == 1) {//原文表
                        bool = tableViewMapper.upArchives(tableName, recordCode.get(i), trashStatus, "FILECODE");
                    }
                } else {
                    return false;
                }
            }
        }
        return bool;
    }

    /**
     * 获取档案条目
     *
     * @param tableCode
     * @param recordCode
     * @param type       判断是档案条目还是原文条目（0，1）
     * @return
     */
    @Override
    public Map<String, String> getArchives(String tableCode, String recordCode, int type) {
        Map<String, String> parmsMap = new HashMap<>();
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        List<Map<String, Object>> columnList = tableViewMapper.getTypeByTableCode(tableCode);
        Map<String, String> archives = null;
        if (type == 0) {
            archives = tableViewMapper.getArchivesByRecordCode(tableName, recordCode, "RECORDCODE");
        } else if (type == 1) {
            archives = tableViewMapper.getArchivesByRecordCode(tableName, recordCode, "FILECODE");
        }
        if (columnList != null && columnList.size() > 0) {
            for (int i = 0; i < columnList.size(); i++) {
                for (String column : columnList.get(i).keySet()) {
                    if (column != null && "NAME".equals(column)) {
                        for (String str : archives.keySet()) {
                            if (columnList.get(i).get(column).toString().equalsIgnoreCase(str)) {//判断是否相等
                                String value = null;
                                if ("4".equals(String.valueOf(columnList.get(i).get("TYPE")))) {
                                    SimpleDateFormat formatter = null;
                                    if ("createDate".equalsIgnoreCase(str)) {
                                        formatter = new SimpleDateFormat("yyyyMMdd");
                                    } else {
                                        formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    }
                                    value = formatter.format(archives.get(str));
                                } else {
                                    Object val = archives.get(str);
                                    value = String.valueOf(val);
                                }
                                String key = columnList.get(i).get(column).toString() + "-" + columnList.get(i).get("TYPE").toString();
                                parmsMap.put(key, value);
                            }
                        }
                    }
                }
            }
        }
        return parmsMap;
    }

    /**
     * 修改档案条目
     *
     * @param tableCode
     * @param recordCode
     * @param params
     * @param type       判断是档案条目还是原文条目（0，1）
     * @return
     */
    @Override
    public boolean upArchivesByRecordCode(String tableCode, String recordCode, Map<String, String> params, int type) {
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);
        Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
        dataType.put("1", "VARCHAR2");
        dataType.put("2", "NUMBER");
        dataType.put("3", "NUMBER");
        dataType.put("4", "DATE");
        dataType.put("5", "VARCHAR2");
        Map<String, String> parmsMap = new HashMap<>();//修改参数
        for (String key : params.keySet()) {
            String startColumn = key.substring(0, key.lastIndexOf("-"));//获取数据列名
            String endType = key.substring(key.lastIndexOf("-") + 1, key.length());//获取数据类型
            for (String type1 : dataType.keySet()) {
                if (type1.equalsIgnoreCase(endType)) {
                    String typeName = dataType.get(endType);
                    if ("VARCHAR2".equals(typeName)) {
                        parmsMap.put(startColumn, "'" + params.get(key) + "'");
                    } else if ("NUMBER".equals(typeName)) {
                        parmsMap.put(startColumn, params.get(key));
                    } else if ("DATE".equals(typeName)) {
                        String date = null;
                        if ("createDate".equalsIgnoreCase(startColumn)) {
                            date = "TO_DATE('" + params.get(key) + "', 'YYYYMMDD')";
                        } else {
                            date = "TO_DATE('" + params.get(key) + "', 'YYYY-MM-DD')";
                        }
                        parmsMap.put(startColumn, date);
                    }
                }
            }
        }
        boolean bool = true;
        if (type == 0) {
            bool = tableViewMapper.upArchivesByRecordCode(tableName, recordCode, parmsMap, "RECORDCODE");
        } else if (type == 1) {
            bool = tableViewMapper.upArchivesByRecordCode(tableName, recordCode, parmsMap, "FILECODE");
        }
        return bool;
    }

    /**
     * 分组
     *
     * @param tableCode
     * @param group
     * @return
     */
    @Override
    public List<Object> getGroup(String tableCode, List<String> group) {
        List<Object> listMap = new ArrayList<>();
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (group != null && group.size() > 0) {
            for (int i = 0; i < group.size(); i++) {

                String groupName = group.get(i).substring(0, group.get(i).indexOf("-"));//获取数据列名中文
                String groupColumn = group.get(i).substring(group.get(i).indexOf("-") + 1, group.get(i).lastIndexOf("-"));//获取数据列英文
                String groupType = group.get(i).substring(group.get(i).lastIndexOf("-") + 1, group.get(i).length());//获取数据类型

                List<String> groupList = tableViewMapper.getGroup(tableName, groupColumn);
                while (groupList.remove(null)) ;
                for (int j = 0; j < groupList.size(); j++) {
                    if (StringUtil.isValidDate(groupList.get(j))) {
                        groupList.set(j, formatter.format(groupList.get(j)));
                    }
                }
                GroupMode groupMode = new GroupMode();
                groupMode.setChineseName(groupName);
                groupMode.setEnglishName(groupColumn);
                groupMode.setColumnType(groupType);
                groupMode.setGroupList(groupList);
                listMap.add(groupMode);
            }
        } else {
            listMap = null;
        }
        return listMap;
    }

    /**
     * 待归档
     *
     * @param tableCode 表编号
     * @param parms
     * @return
     */
    @Transactional
    @Override
    public boolean updateArchivesFiledByRecordCode(String tableCode, List<Map<String, String>> parms) {
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);
        try {
            int result = tableViewMapper.updateArchivesFiledByRecordCode(tableName, parms);
            System.err.println("批量放入待归档:" + result);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 已归档
     *
     * @param tableCode 表编号
     * @param parms
     * @return
     */
    @Transactional
    @Override
    public boolean updateArchivesByRecordCode(String tableCode, List<Map<String, String>> parms) {
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);
        try {
            int result = tableViewMapper.updateArchivesByRecordCode(tableName, parms);
            System.err.println("批量放入已归档:" + result);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 不归档
     *
     * @param tableCode 表编号
     * @param parms
     * @return
     */
    @Transactional
    @Override
    public boolean updateNoArchivesByRecordCode(String tableCode, List<Map<String, String>> parms) {
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);
        try {
            int result = tableViewMapper.updateArchivesByRecordCode(tableName, parms);
            System.err.println("批量放入不归档:" + result);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 查询页数为空或为0的
     *
     * @param tableCode
     * @param parms
     * @param quantity
     * @return
     */
    @Override
    public List<String> getYSByRecordCode(String tableCode, List<String> parms, String quantity) {
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);
        return tableViewMapper.getYSByRecordCode(tableName, parms, quantity);
    }

    /**
     * 查询最大件号或页数
     *
     * @param YSMax
     * @param tableCode
     * @param parmsList
     * @return
     */
    @Override
    public int getYSOrJHMaxBysystemNoFormat(String YSMax, String tableCode, Map<String, String> parmsList) {
        int result = 0;
        String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//获取表名
        if (parmsList != null && parmsList.size() > 0) {
            result = tableViewMapper.getYSOrJHMaxBysystemNoFormat(YSMax, tableName, parmsList);
            System.out.println("最大件号或页数为:" + result);
        }
        return result;
    }


    /**
     * 获取旗下子节点
     *
     * @param departmentCode 部门编号
     * @return
     */
    private List<Tree> getSystemFondsTree(String departmentCode) {
        List<Map<String, String>> list = systemCataLogMapper.getAllSystemCataLogByFondsCode(departmentCode, null);
        List<Tree> treeList = new ArrayList<>(list.size());//存放节点树
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Tree rootTree = new Tree();
                rootTree.setId(list.get(i).get("FONDSDEPARTMENTCODE"));
                rootTree.setText(list.get(i).get("NAME"));
                rootTree.setLi_attr(list.get(i));
                treeList.add(rootTree);
                List<Tree> treeListChildren = getSystemFondsTree(list.get(i).get("DEPARTMENTCODE"));
                if (treeListChildren != null && treeListChildren.size() > 0) {
                    rootTree.setChildren(treeListChildren);//存放节点
                } else {
                    rootTree.setChildren(null);//存放节点
                }
            }
            return treeList;
        }
        return treeList;
    }

    /**
     * 转换查询列的部门编号为部门名称
     *
     * @param listPageInfo
     */
    private void setDepartementName(PageInfo<Map<String, String>> listPageInfo) {
        for (int i = 0; i < listPageInfo.getList().size(); i++) {
            for (String ss : listPageInfo.getList().get(i).keySet()) {
                if ("DEPARTMENTCODE".equals(ss) && listPageInfo.getList().get(i).get(ss) != null && !"".equals(listPageInfo.getList().get(i).get(ss))) {
                    String departementName = departementService.getDepartementNameByDepartementCode(listPageInfo.getList().get(i).get(ss));//获取部门名称
                    if (departementName != null && !"".equals(departementName)) {
                        listPageInfo.getList().get(i).put("DEPARTMENTCODE", departementName);
                    }
                    break;
                }
            }
        }
    }

    /**
     * 日期转换为标准格式
     *
     * @param listPageInfo
     */
    public void toDataByTime(List<Map<String, String>> arrayList, PageInfo<Map<String, String>> listPageInfo) throws ParseException {
        if (arrayList != null && arrayList.size() > 0 && listPageInfo.getList() != null && listPageInfo.getList().size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                for (String cloumn : arrayList.get(i).keySet()) {
                    if ("INPUTTYPE".equals(cloumn) && "D".equals(arrayList.get(i).get(cloumn)) || "A".equals(arrayList.get(i).get(cloumn))) {
                        String cloumnName = arrayList.get(i).get("NAME");//列英文名
                        for (int j = 0; j < listPageInfo.getList().size(); j++) {
                            for (String infoE : listPageInfo.getList().get(j).keySet()) {
                                if (cloumnName.equals(infoE)) {
                                    String dateStr = String.valueOf(listPageInfo.getList().get(j).get(infoE));
                                    if (!dateStr.contains("-")) {
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                        formatter.setLenient(false);
                                        Date newDate = formatter.parse(listPageInfo.getList().get(j).get(infoE));
                                        formatter = new SimpleDateFormat("yyyy-MM-dd");
                                        listPageInfo.getList().get(j).put(infoE, formatter.format(newDate));
                                    } else {
//                                        SimpleDateFormat SFDate = null;
                                        if ("createDate".equalsIgnoreCase(infoE) && dateStr.contains(" 00:00:00.0")) {
                                            dateStr = dateStr.replace(" 00:00:00.0", "");
                                            dateStr = dateStr.replace("-", "");
//                                            SFDate = new SimpleDateFormat("yyyyMMdd");
                                        } else if ("createDate".equalsIgnoreCase(infoE) && dateStr.contains(" 00:00")) {
                                            dateStr = dateStr.replace(" 00:00", "");
//                                            SFDate = new SimpleDateFormat("yyyy-MM-dd");
                                        } else {
                                            dateStr = dateStr.replace(" 00:00:00.0", "");
                                            dateStr = dateStr.replace(" 00:00", "");
//                                            SFDate = new SimpleDateFormat("yyyy-MM-dd");
                                        }
                                        listPageInfo.getList().get(j).put(infoE, dateStr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}

