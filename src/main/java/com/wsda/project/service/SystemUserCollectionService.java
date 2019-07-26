package com.wsda.project.service;

import com.wsda.project.model.CreateCollectionFiles;
import com.wsda.project.model.SystemUser;
import com.wsda.project.model.SystemUserCollection;

import java.util.List;

public interface SystemUserCollectionService {
    List<SystemUserCollection> getAllCollectionByUserCode(String cid, SystemUser systemUser,String recordCode);

    boolean addCollection(SystemUserCollection systemUserCollection);

    boolean delCollectionByUserCode(String id,String cid);


    List<CreateCollectionFiles> getCreateCollectionFiles(String userCode);

    boolean addCreateCollectionFiles(CreateCollectionFiles createCollectionFiles);

    boolean delCreateCollectionFiles(String id);
}
