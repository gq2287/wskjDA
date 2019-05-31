package com.wsda.project.model;

import lombok.Data;

/**
 * 字典实体
 * */
@Data
public class Dictionary {
    private String DICTIONARYCODE; //	字典编号
    private String DICTIONARYCATALOGCODE; 	//字典目录编号
    private String SERIAL;// 	顺序号
    private String CODE;	//代号
    private String NAME; //	项名称
    private String TYPE;	//类型
    private String REMARK;	//项注释
    private String DEFAULTVALUE;//	默认值
}
