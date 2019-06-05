package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.dao.SystemCataLogMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.Dictionary;
import com.wsda.project.model.GroupMode;
import com.wsda.project.model.Tree;
import com.wsda.project.service.TableViewService;
import com.wsda.project.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Resource
    private SystemCataLogMapper systemCataLogMapper ;


    /**
     * 获取当前实体表列
     *
     * @param tableCode
     * @return
     */
    @Override
    public Map<String,Object> getTableView(String tableCode, int pageNum, int PageSize, List<Map<String,String>>conditions, List<Map<String,String>> sorts,String type) {
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
            StringBuffer whereSql=new StringBuffer();//查询条件   RECORDCODE添加的唯一主键
            StringBuffer sortSql=new StringBuffer();//排序条件
            Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
            dataType.put("1", "VARCHAR2");
            dataType.put("2", "NUMBER");
            dataType.put("3", "NUMBER");
            dataType.put("4", "DATE");
            dataType.put("5", "VARCHAR2");
            //处理查询的条件
            if(conditions!=null&&conditions.size()>0){
                whereSql.append(" AND ");
                for (int i = 0; i <conditions.size() ; i++) {
                    for (String cod:conditions.get(i).keySet()) {
                        String startColumn=cod.substring(0,cod.lastIndexOf("-"));//获取数据列名
                        String endType=cod.substring(cod.lastIndexOf("-")+1,cod.length());//获取数据类型
                        String valueStr=conditions.get(i).get(cod);//键对应值
                        if(dataType.containsKey(endType)){//包涵当前的类型
                            if("1".equals(endType)||"2".equals(endType)||"3".equals(endType)){
                                whereSql.append(startColumn);//列名
                                whereSql.append(" like ");
                                whereSql.append("'%"+valueStr+"%'");//值
                                if(i!=conditions.size()-1){
                                    whereSql.append(" or ");
                                }
                            }else if("4".equals(endType)){
                                String valueStart=valueStr.substring(0,valueStr.lastIndexOf("@"));
                                String valueEnd=valueStr.substring(valueStr.lastIndexOf("@")+1,valueStr.length());//获取数据类型
                                whereSql.append(" to_date(to_char("+startColumn+", 'yyyy-MM-dd'), 'yyyy-mm-dd') BETWEEN to_date('"+valueStart+"', 'yyyy-mm-dd') AND to_date('"+valueEnd+"', 'yyyy-mm-dd') ");//值
                                if(i!=conditions.size()-1){
                                    whereSql.append(" or ");
                                }
                            }else if("5".equals(endType)){
                                String valueStart=valueStr.substring(0,valueStr.lastIndexOf("@"));
                                String valueEnd=valueStr.substring(valueStr.lastIndexOf("@")+1,valueStr.length());//获取数据类型
                                whereSql.append(" to_date(to_char("+startColumn+", 'yyyy-MM-dd'), 'yyyy-mm-dd') BETWEEN TO_DATE('"+valueStart+"', 'yyyy-mm-dd') AND TO_DATE('"+valueEnd+"','yyyy-mm-dd') ");//值
                                if(i!=conditions.size()-1){
                                    whereSql.append(" or ");
                                }
                            }
                        }
                    }
                }
            }
            //处理排序的条件
            if(sorts!=null&&sorts.size()>0){
                sortSql.append(" ORDER BY ");
                for (int i = 0; i <sorts.size() ; i++) {
                    for (String so:sorts.get(i).keySet()) {
                        sortSql.append(so);//列名
                        sortSql.append(" "+sorts.get(i).get(so));
                        if(i!=sorts.size()-1){
                            sortSql.append(", ");
                        }
                    }
                }
            }
            System.err.println(whereSql+"---\n"+sortSql);
            //End

            //业务
            String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//获取实体表名称
            if(tableName!=null){
                columnMap.put("RECORDCODE","RECORDCODE");
                PageHelper.startPage(pageNum, PageSize);//分页
                PageInfo<Map<String,String>> listPageInfo=new PageInfo<>(tableViewMapper.getTableInfo(tableName,columnMap,String.valueOf(whereSql),String.valueOf(sortSql),type));//存放入分页的pageInfo中
                mapObj.put("tableColums",arrayList);//展示列
                setDepartementName(listPageInfo);//转换查询列的部门编号为部门名称
                toDataByTime(arrayList,listPageInfo);//转换日期格式
                mapObj.put("pageInfo",listPageInfo);//实体表内容
                mapObj.put("sorts",sorts);//排序纪录
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
                                        }else if("CANNULL".equals(input)&&inputTypelist.get(j).get(input)!=null){
                                            stringMap.put("CANNULL",inputTypelist.get(j).get(input));
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
     * 添加档案
     * @param infoMap
     * @return
     */
    @Transactional
    @Override
    public boolean addTableInfo(Map<String, String> infoMap) {
        boolean bool=true;
        Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
        dataType.put("1", "VARCHAR2");
        dataType.put("2", "NUMBER");
        dataType.put("3", "NUMBER");
        dataType.put("4", "DATE");
        dataType.put("5", "VARCHAR2");
        String tableCode=infoMap.get("tableCode");//获取表编号
        infoMap.remove("tableCode");
        //获取表名
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        List<String> columnList=new ArrayList<>();//添加表 字段列集合
        List<String> valuesList=new ArrayList<>();//添加表 字段列对应值集合
        columnList.add("RECORDCODE");//主键
        valuesList.add("'"+ StringUtil.getRandomStr(8)+"'");//唯一主键

        infoMap.put("TRASHSTATUS-1","0");//回收站标志 0默认，1删除
        infoMap.put("OPERATETIME-5",StringUtil.getDate(1));//操作日期
        for (String column:infoMap.keySet()) {
            //例：RECORDCODE-1
            String startColumn=column.substring(0,column.lastIndexOf("-"));//获取数据列名
            if(infoMap.get(column)!=null&&!"".equals(infoMap.get(column))){
                columnList.add(startColumn);//添加表字段列
                String endType=column.substring(column.lastIndexOf("-")+1,column.length());//获取数据类型
                if(dataType.containsKey(endType)){//包涵当前的类型
                    String typeName=dataType.get(endType);//类型名称
                    String value=infoMap.get(column);//获得值
                    if("VARCHAR2".equals(typeName)){
                        valuesList.add("'"+value+"'");
                    }else if("NUMBER".equals(typeName)){
                        valuesList.add(value);
                    }else if("DATE".equals(typeName)){
                        valuesList.add("TO_DATE('"+value+"','YYYY-MM-DD')");
                    }
                }
            }
        }
        bool=tableViewMapper.addTableInfo(tableName,columnList,valuesList);
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
        List<Map<String, String>> departementList=systemCataLogMapper.getAllSystemCataLogByFondsCode(null,fondsCode);
        List<Tree> treeList=new ArrayList<>(departementList.size());//存放节点树
        if(departementList!=null&&departementList.size()>0){
            for (int i = 0; i < departementList.size(); i++) {
                Tree rootTree=new Tree();//根节点
                rootTree.setId(departementList.get(i).get("DEPARTMENTCODE"));
                rootTree.setText(departementList.get(i).get("NAME"));
                rootTree.setLi_attr(departementList.get(i));
                treeList.add(rootTree);
                List<Tree> treeListChildren=getSystemFondsTree(departementList.get(i).get("DEPARTMENTCODE"));//获取旗下子节点
                if(treeListChildren!=null&&treeListChildren.size()>0){//获取旗下子节点
                    rootTree.setChildren(treeListChildren);//存放节点
                }else{
                    rootTree.setChildren(null);//存放节点
                }
            }
            return treeList;
        }else{
            return null;
        }
    }

    /**
     * 删除恢复当前档案
     * @param tableCode 表编号
     * @param recordCode 修改条目的主键
     * @param trashStatus 0恢复，1删除
     * @return
     */
    @Transactional
    @Override
    public boolean upArchives(String tableCode, List<String> recordCode,String trashStatus)throws Exception {
        boolean bool=true;
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        if(recordCode!=null){
            for (int i = 0; i < recordCode.size(); i++) {
                if(bool){
                    bool=tableViewMapper.upArchives(tableName,recordCode.get(i),trashStatus);
                }else {
                    return false;
                }
            }
        }
        return bool;
    }

    /**
     * 获取档案条目
     * @param tableCode
     * @param recordCode
     * @return
     */
    @Override
    public Map<String,String> getArchives(String tableCode, String recordCode) {
        Map<String,String> parmsMap=new HashMap<>();
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        List<Map<String,Object>> columnList=tableViewMapper.getTypeByTableCode(tableCode);
        Map<String,String> archives=tableViewMapper.getArchivesByRecordCode(tableName,recordCode);
        if(columnList!=null&&columnList.size()>0){
            for (int i = 0; i < columnList.size(); i++) {
                for (String column:columnList.get(i).keySet()) {
                    if(column!=null&&"NAME".equals(column)){
                        for (String str:archives.keySet()) {
                            if(columnList.get(i).get(column).toString().equalsIgnoreCase(str)){//判断是否相等
                                String value=null;
                                if("4".equals(String.valueOf(columnList.get(i).get("TYPE")))){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    value=formatter.format(archives.get(str));
                                }else{
                                    Object val=archives.get(str);
                                    value=String.valueOf(val);
                                }
                                String key=columnList.get(i).get(column).toString()+"-"+columnList.get(i).get("TYPE").toString();
                                parmsMap.put(key,value);
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
     * @param tableCode
     * @param recordCode
     * @param params
     * @return
     */
    @Override
    public boolean upArchivesByRecordCode(String tableCode, String recordCode, Map<String, String> params) {
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);
        Map<String, String> dataType = new HashMap();//数据库为Oracle字段类型
        dataType.put("1", "VARCHAR2");
        dataType.put("2", "NUMBER");
        dataType.put("3", "NUMBER");
        dataType.put("4", "DATE");
        dataType.put("5", "VARCHAR2");
        Map<String,String> parmsMap=new HashMap<>();//修改参数
        for (String key:params.keySet()) {
            String startColumn=key.substring(0,key.lastIndexOf("-"));//获取数据列名
            String endType=key.substring(key.lastIndexOf("-")+1,key.length());//获取数据类型
            for (String type:dataType.keySet()) {
                if(type.equalsIgnoreCase(endType)){
                   String typeName =dataType.get(endType);
                    if("VARCHAR2".equals(typeName)){
                        parmsMap.put(startColumn,"'"+params.get(key)+"'");
                    }else if("NUMBER".equals(typeName)){
                        parmsMap.put(startColumn,params.get(key));
                    }else if("DATE".equals(typeName)){
                        String date="TO_DATE('"+params.get(key)+"', 'YYYY-MM-DD')";
                        parmsMap.put(startColumn,date);
                    }
                }
            }
        }
        boolean bool=tableViewMapper.upArchivesByRecordCode(tableName,recordCode,parmsMap);
        return bool;
    }

    /**
     * 分组
     * @param tableCode
     * @param group
     * @return
     */
    @Override
    public List<Object> getGroup(String tableCode, List<String> group) {
        List<Object> listMap=new ArrayList<>();
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//添加表 表名称
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if(group!=null&&group.size()>0){
            for (int i = 0; i < group.size(); i++) {

                String groupName=group.get(i).substring(0,group.get(i).indexOf("-"));//获取数据列名中文
                String groupColumn=group.get(i).substring(group.get(i).indexOf("-")+1,group.get(i).lastIndexOf("-"));//获取数据列英文
                String groupType=group.get(i).substring(group.get(i).lastIndexOf("-")+1,group.get(i).length());//获取数据类型

                List<String> groupList=tableViewMapper.getGroup(tableName,groupColumn);
                while (groupList.remove(null));
                for (int j = 0; j <groupList.size() ; j++) {
                    if(StringUtil.isValidDate(groupList.get(i))){
                        groupList.set(i,formatter.format(groupList.get(i)));
                    }
                }
                GroupMode groupMode=new GroupMode();
                groupMode.setChineseName(groupName);
                groupMode.setEnglishName(groupColumn);
                groupMode.setColumnType(groupType);
                groupMode.setGroupList(groupList);
                listMap.add(groupMode);
            }
        }else{
            listMap=null;
        }
        return listMap;
    }


    /**
     * 获取旗下子节点
     * @param departmentCode 部门编号
     * @return
     */
    private  List<Tree> getSystemFondsTree(String departmentCode){
        List<Map<String,String>> list=systemCataLogMapper.getAllSystemCataLogByFondsCode(departmentCode,null);
        List<Tree> treeList=new ArrayList<>(list.size());//存放节点树
        if(list!=null&&list.size()>0){
            for (int i = 0; i <list.size() ; i++) {
                Tree rootTree=new Tree();
                rootTree.setId(list.get(i).get("FONDSDEPARTMENTCODE"));
                rootTree.setText(list.get(i).get("NAME"));
                rootTree.setLi_attr(list.get(i));
                treeList.add(rootTree);
                List<Tree> treeListChildren= getSystemFondsTree(list.get(i).get("DEPARTMENTCODE"));
                if(treeListChildren!=null&&treeListChildren.size()>0){
                    rootTree.setChildren(treeListChildren);//存放节点
                }else{
                    rootTree.setChildren(null);//存放节点
                }
            }
            return  treeList;
        }
        return treeList;
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
    /**
     * 日期转换为标准格式
     * @param listPageInfo
     */
    private  void toDataByTime(List<Map<String, String>> arrayList,PageInfo<Map<String,String>> listPageInfo){
        if(arrayList!=null&&arrayList.size()>0&&listPageInfo.getList()!=null&&listPageInfo.getList().size()>0){
            for (int i = 0; i <arrayList.size() ; i++) {
                for (String cloumn:arrayList.get(i).keySet()) {
                    if("INPUTTYPE".equals(cloumn)&&"D".equals(arrayList.get(i).get(cloumn))){
                        String cloumnName=arrayList.get(i).get("NAME");//列英文名
                        for (int j = 0; j < listPageInfo.getList().size() ;j++) {
                            for (String infoE:listPageInfo.getList().get(j).keySet()) {
                                if(cloumnName.equals(infoE)){
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    String time=formatter.format(listPageInfo.getList().get(j).get(infoE));
                                    listPageInfo.getList().get(j).put(infoE,time);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

