package com.wsda.project.service.impl;

import com.wsda.project.dao.SystemUserCollectionMapper;
import com.wsda.project.model.CreateCollectionFiles;
import com.wsda.project.model.SystemUser;
import com.wsda.project.model.SystemUserCollection;
import com.wsda.project.service.SystemUserCollectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SystemUserCollectionServiceImpl implements SystemUserCollectionService {
    @Resource
    private SystemUserCollectionMapper systemUserCollectionMapper;
    @Override
    public List<SystemUserCollection> getAllCollectionByUserCode(String cid,SystemUser systemUser,String recordCode) {
        return systemUserCollectionMapper.getAllCollectionByUserCode(cid,systemUser.getUserCode(),recordCode);
    }

    @Override
    public boolean addCollection(SystemUserCollection systemUserCollection) {
        return systemUserCollectionMapper.addCollection(systemUserCollection);
    }

    /**
     * 删除收藏档案
     * @param id 档案主键唯一编号
     * @param cid 收藏夹编号
     * @return
     */
    @Override
    public boolean delCollectionByUserCode(String id,String cid) {
        return systemUserCollectionMapper.delCollection(id,cid);
    }





    @Override
    public List<CreateCollectionFiles> getCreateCollectionFiles(String userCode) {
        return systemUserCollectionMapper.getCreateCollectionFiles(userCode);
    }

    @Override
    public boolean addCreateCollectionFiles(CreateCollectionFiles createCollectionFiles) {
        return systemUserCollectionMapper.addCreateCollectionFiles(createCollectionFiles);
    }

    @Override
    public boolean delCreateCollectionFiles(String id) {
        return systemUserCollectionMapper.delCreateCollectionFiles(id);
    }
}
