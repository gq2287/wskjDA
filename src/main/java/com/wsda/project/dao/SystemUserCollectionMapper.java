package com.wsda.project.dao;

import com.wsda.project.model.CreateCollectionFiles;
import com.wsda.project.model.SystemUserCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserCollectionMapper {
    /**
     * @param cId 收藏夹id
     * @param userCode 用户id
     * @param recordCode 档案编号
     * @return
     */
    List<SystemUserCollection> getAllCollectionByUserCode(@Param("cId") String cId,@Param("userCode") String userCode,@Param("recordCode") String recordCode);
    boolean addCollection(SystemUserCollection systemUserCollection);
    boolean delCollection(@Param("id") String id,@Param("cid") String cid);


    List<CreateCollectionFiles> getCreateCollectionFiles(@Param("userCode") String userCode);
    boolean addCreateCollectionFiles(CreateCollectionFiles createCollectionFiles);
    boolean delCreateCollectionFiles(@Param("id") String id);
}
