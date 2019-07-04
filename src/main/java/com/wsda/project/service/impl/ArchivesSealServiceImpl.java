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
        int result=archivesSealMapper.delArchivesSeal(id);
        if(result>=0){
            return true;
        }
        return false;
    }

    @Override
    public boolean addArchivesSeal(ArchivesSeal archivesSeal) {
        ArchivesSeal ar=archivesSealMapper.getArchivesSealByTableCode(archivesSeal.getTableCode());//一个表只有一个章
        if(ar!=null){
            delArchivesSeal(ar.getId());
        }
        Integer max=archivesSealMapper.getSerialMax();//序号;
        if(max!=null&&max>0){
            archivesSeal.setSerial(max);//序号
        }else{
            archivesSeal.setSerial(0);//序号
        }
        int result=archivesSealMapper.addArchivesSeal(archivesSeal);
        if(result>0){
            return true;
        }
        return false;
    }
}
