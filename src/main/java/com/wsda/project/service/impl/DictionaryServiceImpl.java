package com.wsda.project.service.impl;

import com.wsda.project.dao.DictionaryMapper;
import com.wsda.project.model.Dictionary;
import com.wsda.project.service.DictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryMapper dictionaryMapper;

    /**
     * 根据名称查询对应的字典
     * @param code
     * @return
     */
    @Override
    public List<Dictionary> getAllDictionaryData(String code) {
        return dictionaryMapper.getAllDictionaryData(code);
    }
}
