package com.wsda.project.dao;


import com.wsda.project.model.ArchivesSeal;
import org.apache.ibatis.annotations.Param;

public interface ArchivesSealMapper {
    ArchivesSeal getArchivesSealByTableCode(@Param("tableCode") String tableCode);
    Integer delArchivesSeal(@Param("id")String id);
    Integer addArchivesSeal(ArchivesSeal archivesSeal);
    Integer getSerialMax();
}
