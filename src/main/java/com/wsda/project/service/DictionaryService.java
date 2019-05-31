package com.wsda.project.service;

import com.wsda.project.model.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    List<Dictionary> getAllDictionaryData(String dictName);
}
