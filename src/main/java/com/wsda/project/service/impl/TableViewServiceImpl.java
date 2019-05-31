package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.Dictionary;
import com.wsda.project.model.Tree;
import com.wsda.project.service.TableViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableViewServiceImpl implements TableViewService {

    @Resource
    private TableViewMapper tableViewMapper;
    @Resource
    private ClassTreeMapper classTreeMapper;

    @Resource
    private ClassTreeServiceImpl classTreeService ;
    @Resource
    private DepartementServiceImpl  departementService ;

    @Resource
    private DictionaryServiceImpl  dictionaryService ;

    /**
     * 获取当前实体表列
     *
     * @param tableCode
     * @return
     */
    @Override
    public Map<String,Object> getTableView(String tableCode, int pageNum, int PageSize, List<Map<String,String>>conditions, List<Map<String,String>> sorts) {
        Map<String,Object> mapObj=new HashMap<>();//返回结果集
        List<Map<String, String>> arrayList = new ArrayList<>();
        try {
            arrayList = tableViewMapper.getTableView(tableCode);//获取展示列
            Map<String,String> columnMap=new HashMap<>();
            if(arrayList.size()>0){
                for (int i = 0; i <arrayList.size() ; i++) {
                    for (String str:arrayList.get(i).keySet()) {
                        if("NAME".equals(str)){
                            arrayList.get(i).put(str,arrayList.get(i).get(str).toUpperCase());//转小写
                            columnMap.put(arrayList.get(i).get(str),arrayList.get(i).get(str));
                        }
                    }
                }
            }
            //Start
            StringBuffer whereSql=new StringBuffer();//查询条件
            StringBuffer sortSql=new StringBuffer();//排序条件
            //处理查询的条件
            if(conditions!=null&&conditions.size()>0){
                whereSql.append(" WHERE ");
                whereSql.append("");//列名
                whereSql.append(" =");
                whereSql.append("'%"+""+"%'");//值
                whereSql.append(",");
            }
            //处理排序的条件
            if(sorts!=null&&sorts.size()>0){
                sortSql.append(" ORDER BY ");
                sortSql.append(",");
            }
            //End

            //业务
            String tableName=tableViewMapper.getTableByTableCode(tableCode);//获取实体表名称
            if(tableName!=null){
                columnMap.put("RECORDCODE","RECORDCODE");
                PageHelper.startPage(pageNum, PageSize);//分页
                PageInfo<Map<String,String>> listPageInfo=new PageInfo<>(tableViewMapper.getTableInfo(tableName,columnMap,String.valueOf(whereSql),String.valueOf(sortSql)));//存放入分页的pageInfo中
                mapObj.put("tableColums",arrayList);//展示列
                setDepartementName(listPageInfo);
                mapObj.put("pageInfo",listPageInfo);//实体表内容
            }else{
                return null;
            }
        } catch (Exception e) {
            System.err.println("查询视图列失败：" + e.getMessage() + "tableCode");
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
        List<Map<String, String>> listTop=classTreeMapper.getClassITop();//获取顶级节点
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
                                if(newList!=null&&newList.size()>0){
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
                                }else{
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
     * @param tableCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getInputCard(String tableCode) {
        List<Map<String, Object>> inputCardList=tableViewMapper.getInputCard(tableCode);
        List<Map<String, Object>> newInputCardList=new ArrayList<>();//返回集合
            if(inputCardList!=null&&inputCardList.size()>0){
                for (int i = 0; i <inputCardList.size() ; i++) {
                    Map<String,Object> stringMap=inputCardList.get(i);
                    for (String name:stringMap.keySet()) {
                        if(stringMap.get(name)!=null&&"FIELDNAME".equals(name)&&!"null".equals(stringMap.get(name))){
                            List<Map<String,String>> inputTypelist=tableViewMapper.getInputTypeByTableCodeAndName(tableCode,String.valueOf(stringMap.get(name)));
                            if(inputTypelist!=null&&inputTypelist.size()>0){
                                for (int j = 0; j <inputTypelist.size() ; j++) {
                                    for (String input:inputTypelist.get(j).keySet()) {
                                        if("TYPE".equals(input)&&inputTypelist.get(j).get(input)!=null){
                                            stringMap.put("TYPE",inputTypelist.get(j).get(input));
                                        }else if("INPUTTYPE".equals(input)&&inputTypelist.get(j).get(input)!=null){
                                            stringMap.put("INPUTTYPE",inputTypelist.get(j).get(input));
                                            if("S".equals(inputTypelist.get(j).get(input))){
                                                String dictName=String.valueOf(stringMap.get("PROPERTIESINFO1"));
                                                if(dictName!=null&&!"".equals(dictName)){
                                                    List<Dictionary> dictionaryList=dictionaryService.getAllDictionaryData(dictName);
                                                    stringMap.put("PROPERTIESINFO2",dictionaryList);
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
     * 转换查询列的部门编号为部门名称
     * @param listPageInfo
     */
    private  void setDepartementName(PageInfo<Map<String,String>> listPageInfo){
        for (int i = 0; i <listPageInfo.getList().size() ; i++) {
            for (String ss:listPageInfo.getList().get(i).keySet()) {
                if("DEPARTMENTCODE".equals(ss)&&listPageInfo.getList().get(i).get(ss)!=null&&!"".equals(listPageInfo.getList().get(i).get(ss))){
                    String departementName=departementService.getDepartementNameByDepartementCode(listPageInfo.getList().get(i).get(ss));//获取部门名称
                    if(departementName!=null&&!"".equals(departementName)){
                        listPageInfo.getList().get(i).put("DEPARTMENTCODE",departementName);
                    }
                    break;
                }
            }
        }
    }

}

