package com.wsda.project.service.impl;

import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.model.Tree;
import com.wsda.project.service.ClassTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassTreeServiceImpl implements ClassTreeService {
    @Resource
    private ClassTreeMapper classTreeMapper;


    @Override
    public Tree getTreeMenu() {
        List<Map<String, String>> listTop=classTreeMapper.getClassITop();//获取顶级节点
        Map<String, String> parmMap = new HashMap<>();//参数map
        List<Tree> treeList=new ArrayList<>();//节点集合
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
            Tree treeI=new Tree();
            Map<String,String> attrIs=new HashMap<>();// treeI树其他属性
            for (String cI : classIMap.keySet()) {
                attrIs.put(cI,classIMap.get(cI));//当前树全部属性
                treeI.setText(classIMap.get("NAME"));//获取树名称
                if("NODECODE".equals(cI)){
                    treeI.setId(classIMap.get("NODECODE"));//获取树编号
                    List<Map<String, String>> classCL = classTreeMapper.getClassCL(classIMap.get("NODECODE"));// 获取到 5大基础门类treeI 子节点
                    List<Tree> treeCLList=new ArrayList<>();//treeI二级节点集合
                    for (int j = 0; j < classCL.size(); j++) {
                        Tree treeCL=new Tree();//当前中间门类和底层门类树
                        for (String jm:classCL.get(j).keySet()) {
                            treeCL.setText(classCL.get(j).get("NAME"));//获取treeCL树名称
                            treeCL.setId(classCL.get(j).get("NODECODE"));//获取树编号
                            if("NODECODE".equals(jm)){
                                Integer count=classTreeMapper.getClassCLCount(classCL.get(j).get(jm));//判断旗下是否有节点
                                if(count!=null&&count>0){
                                    List<Tree> newList=getCLBynodeCode( classCL.get(j).get(jm));//获取当前节点nodecode
                                    treeCL.setChildren(newList);
                                }
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
     * 根据nodecode获取其子节点
     * @param nodeCode
     * @return
     */
    public  List<Tree> getCLBynodeCode(String nodeCode){
        List<Map<String, String>> treeList = classTreeMapper.getClassCL(nodeCode);//获取子节点
        List<Tree> trees=new ArrayList<>();
        for (int i = 0; i <treeList.size() ; i++) {
            Tree tree1=new Tree();
            for (String cls:treeList.get(i).keySet()) {
                tree1.setId(treeList.get(i).get("NODECODE"));
                tree1.setText(treeList.get(i).get("NAME"));
                break;
            }
            tree1.setLi_attr(treeList.get(i));
            trees.add(tree1);
        }
        return trees;
    }


    /**
     * 根据父节点nodecode获取旗下实体表
     * @param nodeCode
     * @return
     */
    public  List<Map<String,String>> getTableByNodeCode(String nodeCode){
        List<Map<String,String>> listMap=new ArrayList<>();
        for(int i=1;i>0;i++){
            List<Map<String,String>> list=classTreeMapper.getTableByNodeCode(nodeCode);
            if(list!=null&&list.size()!=0){
                for (int j = 0; j <list.size() ; j++) {
                    nodeCode=list.get(j).get("NODECODE");
                    listMap.add(list.get(j));
                    continue;
                }
            }else{
                return  listMap;
            }
        }
        return listMap;
    }

}