package com.wsda.project.service;

import com.wsda.project.model.Tree;

import java.util.List;

public interface DepartementService {
    List<Tree> getDepartementByParentCode();
    String getDepartementNameByDepartementCode(String departementCode);
}
