package com.wsda.project.service.impl;

import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.dao.TableViewMapper;
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

    /**
     * 获取当前实体表列
     *
     * @param tableCode
     * @return
     */
    @Override
    public List<Map<String, Object>> getTableView(String tableCode) {
        List<Map<String, Object>> parms = null;
        try {
            parms = tableViewMapper.getTableView(tableCode);
        } catch (Exception e) {
            System.err.println("查询视图列失败：" + e.getMessage() + "tableCode");
        }
        return parms;
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




}

