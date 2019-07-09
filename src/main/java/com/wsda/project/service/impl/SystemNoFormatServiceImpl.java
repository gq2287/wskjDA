package com.wsda.project.service.impl;

import com.wsda.project.dao.SystemNoFormatMapper;
import com.wsda.project.model.SystemNoFormat;
import com.wsda.project.service.SystemNoFormatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SystemNoFormatServiceImpl implements SystemNoFormatService {
    @Resource
    private SystemNoFormatMapper systemNoFormatMapper;
    @Override
    public List<Map<String,String>> getSystemNoFormatList() {
        return systemNoFormatMapper.getSystemNoFormatList();
    }

    @Override
    public List<SystemNoFormat> getSystemNoFormatListByEntityCode(String entityCode) {
        return systemNoFormatMapper.getSystemNoFormatListByEntityCode(entityCode);
    }

    @Override
    public int addSystemNoFormat(SystemNoFormat systemNoFormat) {
        return systemNoFormatMapper.addSystemNoFormat(systemNoFormat);
    }

    @Override
    public int delSystemNoFormatByNoFormatCode(String noFormatCode) {
        return systemNoFormatMapper.delSystemNoFormatByNoFormatCode(noFormatCode);
    }
}
