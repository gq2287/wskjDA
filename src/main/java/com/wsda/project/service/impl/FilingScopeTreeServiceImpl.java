package com.wsda.project.service.impl;

import com.wsda.project.dao.FilingScopeTreeMapper;
import com.wsda.project.model.FilingScopeTree;
import com.wsda.project.service.FilingScopeTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FilingScopeTreeServiceImpl implements FilingScopeTreeService {
    @Resource
    private FilingScopeTreeMapper filingScopeTreeMapper;

    /**
     * 获取归档范围菜单
     *
     * @return
     */
    @Override
    public List<FilingScopeTree> getFilingScopeTreeMenu() {
        List<FilingScopeTree> tempAll = filingScopeTreeMapper.getAllFilingScopes();
        Collections.sort(tempAll, new Comparator<FilingScopeTree>() {
            @Override
            public int compare(FilingScopeTree o1, FilingScopeTree o2) {
                return o1.getSerial() - o2.getSerial();//根据serial进行排序 升序
            }
        });
        List<FilingScopeTree> rootTree = new ArrayList<>();
        for (int i = 0; i < tempAll.size(); i++) {//获取根节点全部数据放到rootTree
            if (tempAll.get(i).getParentCode() != null && "0".equals(tempAll.get(i).getParentCode())) {//根节点没有父级
                rootTree.add(tempAll.get(i));//放入
                tempAll.remove(i);
            }
        }
        for (int i = 0; i < rootTree.size(); i++) {
            List<FilingScopeTree> treeList = getFilingScopeTree(rootTree.get(i), tempAll);
            rootTree.get(i).setChildren(treeList);
        }
        return rootTree;
    }


    /**
     * 添加菜单节点
     *
     * @param filingScopeTree
     * @return
     */
    @Override
    public boolean addFilingScope(FilingScopeTree filingScopeTree) {
        int result = filingScopeTreeMapper.addFilingScope(filingScopeTree);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除 归档范围条目
     *
     * @param nodeCode
     * @return
     */
    @Override
    public boolean delFilingScopeByNodeCode(String nodeCode) {
        FilingScopeTree thisFilingScope = filingScopeTreeMapper.getFilingScopesByNodeCode(nodeCode);
        if (thisFilingScope != null) {
            List<FilingScopeTree> tempAll = filingScopeTreeMapper.getAllFilingScopes();
            if (tempAll != null && tempAll.size() > 0) {
                List<String> childrens = new ArrayList<>();//获取要删除的nodecode
                childrens.add(nodeCode);
                getFilingScopeNodeCode(thisFilingScope.getNodeCode(), tempAll,childrens);
                int result=filingScopeTreeMapper.delFilingScopeByNodeCode(childrens);
                System.err.println("删除成功:"+result+"===childrens"+childrens);
            }
        }
        return false;
    }

    /**
     * 获取最大的编号
     *
     * @param parentCode
     * @return
     */
    @Override
    public int getFilingScopeMaxNodeCode(String parentCode) {
        return filingScopeTreeMapper.getFilingScopeMaxNodeCode(parentCode);
    }

    /**修改归档范围条目
     * @param nodeCode
     * @param title
     * @param dateOfCustody
     * @return
     */
    @Override
    public int upFilingScopeNodeCode(String nodeCode,String title,String dateOfCustody) {
        return filingScopeTreeMapper.upFilingScopeNodeCode(nodeCode,title,dateOfCustody);
    }


    /**
     * 递归归档范围菜单
     *
     * @param thisTree            当前节点
     * @param filingScopeTreeList 全部节点
     * @return
     */
    private List<FilingScopeTree> getFilingScopeTree(FilingScopeTree thisTree, List<FilingScopeTree> filingScopeTreeList) {
        List<FilingScopeTree> childrens = new ArrayList<>();
        if (filingScopeTreeList != null && filingScopeTreeList.size() > 0) {
            for (int i = 0; i < filingScopeTreeList.size(); i++) {
                if (filingScopeTreeList.get(i).getParentCode().equals(thisTree.getNodeCode())) {//判断子节点
                    FilingScopeTree filingScopeTree = filingScopeTreeList.get(i);
                    childrens.add(filingScopeTree);
                    filingScopeTree.setChildren(getFilingScopeTree(filingScopeTree, filingScopeTreeList));
                }
            }
        }
        return childrens;
    }


    /**删除
     * 递归归档范围nodecode
     *
     * @param nodeCode            当前节点
     * @param filingScopeTreeList 全部节点
     *  @param childrens 全部nodeCode
     * @return
     */
    private List<String> getFilingScopeNodeCode(String nodeCode, List<FilingScopeTree> filingScopeTreeList,List<String> childrens) {
        if (filingScopeTreeList != null && filingScopeTreeList.size() > 0) {
            for (int i = 0; i < filingScopeTreeList.size(); i++) {
                if (filingScopeTreeList.get(i).getParentCode().equals(nodeCode)) {//判断子节点
                    FilingScopeTree filingScopeTree = filingScopeTreeList.get(i);
                    childrens.add(filingScopeTree.getNodeCode());
                    getFilingScopeNodeCode(filingScopeTree.getNodeCode(),filingScopeTreeList,childrens);//判断是否存在子节点
                }
            }
        }
        return childrens;
    }


}
