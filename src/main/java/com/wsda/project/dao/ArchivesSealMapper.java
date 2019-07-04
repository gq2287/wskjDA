package com.wsda.project.dao;


import com.wsda.project.model.ArchivesSeal;
import org.apache.ibatis.annotations.Param;

public interface ArchivesSealMapper {
    ArchivesSeal getArchivesSealByTableCode(@Param("tableCode") String tableCode);
    boolean delArchivesSeal(@Param("id")String id);
    boolean addArchivesSeal(ArchivesSeal archivesSeal);
    Integer getSerialMax();
}
