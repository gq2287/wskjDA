package com.wsda.project.service.impl;

import com.wsda.project.dao.DepartementMapper;
import com.wsda.project.model.Departement;
import com.wsda.project.model.Tree;
import com.wsda.project.service.DepartementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class DepartementServiceImpl implements DepartementService {
    @Resource
    private DepartementMapper departementMapper;
    @Override
    public List<Tree> getDepartementByParentCode() {
        String parentCode="00000000";//基础部门门类
        List<Departement> departementList=departementMapper.getDepartementByParentCode(parentCode);
        List<Tree> treeList=new ArrayList<>(departementList.size());
        if(departementList!=null&&departementList.size()>0){
            for (int i = 0; i < departementList.size(); i++) {
                Tree rootTree=new Tree();//根节点
                rootTree.setId(departementList.get(i).getDEPARTMENTCODE());
                rootTree.setText(departementList.get(i).getNAME());
                rootTree.setLi_attr(departementList.get(i));
                rootTree.setChildren( getListSystemDepartement(departementList.get(i)));//获取旗下子节点
                treeList.add(rootTree);
            }
            return treeList;
        }else{
            return null;
        }
    }

    @Override
    public String getDepartementNameByDepartementCode(String departementCode) {
        return departementMapper.getDepartementNameByDepartementCode(departementCode);
    }

    /**
     * 获取旗下子节点
     * @param departmentCode 主键
     * @return
     */
    private List<Tree> getListSystemDepartement(Departement departmentCode){
        List<Departement> departementList=departementMapper.getDepartementByParentCode(departmentCode.getDEPARTMENTCODE());//根据基础的部门编号查询旗下子节点
        List<Tree> treeList=new ArrayList<>(departementList.size());//存放节点树
        if(departementList!=null&&departementList.size()>0){
            for (int i = 0; i < departementList.size(); i++) {
                Tree childrenTree=new Tree();
                childrenTree.setId(departementList.get(i).getDEPARTMENTCODE());
                childrenTree.setText(departementList.get(i).getNAME());
                childrenTree.setLi_attr(departementList.get(i));
                treeList.add(childrenTree);
                childrenTree.setChildren(getListSystemDepartement(departementList.get(i)));//存放节点
            }
            return  treeList;
        }
        return  treeList;
    }
}
