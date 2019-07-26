package com.wsda.project.service;

import com.wsda.project.model.ArchivesSeal;

public interface ArchivesSealService {
    ArchivesSeal getArchivesSeal(String tableCode);
    boolean delArchivesSeal(String id);
    boolean addArchivesSeal(ArchivesSeal archivesSeal);
}
