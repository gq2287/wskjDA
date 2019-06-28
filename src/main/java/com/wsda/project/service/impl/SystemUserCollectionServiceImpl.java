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
    public List<SystemUserCollection> getAllCollectionByUserCode(String cid,SystemUser systemUser) {
        return systemUserCollectionMapper.getAllCollectionByUserCode(cid,systemUser.getUserCode());
    }

    @Override
    public boolean addCollection(SystemUserCollection systemUserCollection) {
        return systemUserCollectionMapper.addCollection(systemUserCollection);
    }

    @Override
    public boolean delCollectionByUserCode(String id) {
        return systemUserCollectionMapper.delCollection(id);
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
