package com.wsda.project.service.impl;

import com.wsda.project.dao.ArchivesSealMapper;
import com.wsda.project.model.ArchivesSeal;
import com.wsda.project.service.ArchivesSealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArchivesSealServiceImpl implements ArchivesSealService {
    @Resource
    private ArchivesSealMapper archivesSealMapper;
    @Override
    public ArchivesSeal getArchivesSeal(String tableCode) {
        return archivesSealMapper.getArchivesSealByTableCode(tableCode);
    }

    @Override
    public boolean delArchivesSeal(String id) {
        return archivesSealMapper.delArchivesSeal(id);
    }

    @Override
    public boolean addArchivesSeal(ArchivesSeal archivesSeal) {
        int max=archivesSealMapper.getSerialMax();//序号;
        if(max>0){
            archivesSeal.setSerial(max);//序号
        }else{
            archivesSeal.setSerial(0);//序号
        }
        return archivesSealMapper.addArchivesSeal(archivesSeal);
    }
}
