package com.wsda.project.service.impl;

import com.wsda.project.dao.ClassTreeMapper;
import com.wsda.project.dao.SystemNoFormatMapper;
import com.wsda.project.model.SystemNoFormat;
import com.wsda.project.service.SystemNoFormatService;
import com.wsda.project.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class SystemNoFormatServiceImpl implements SystemNoFormatService {
    @Resource
    private ClassTreeMapper classTreeMapper;
    @Resource
    private SystemNoFormatMapper systemNoFormatMapper;

    @Override
    public List<Map<String,String>> getSystemNoFormatList() {
        List<Map<String,String>> sysMapList=systemNoFormatMapper.getSystemNoFormatList();
        return sysMapList;
    }

    @Override
    public List<SystemNoFormat> getSystemNoFormatListByEntityCode(String entityCode) {
        return systemNoFormatMapper.getSystemNoFormatListByEntityCode(entityCode);
    }

    @Transactional
    @Override
    public int addSystemNoFormat(List<SystemNoFormat> systemNoFormatList) {
        int result=0;
        if(systemNoFormatList!=null&&systemNoFormatList.size()>0){
            String emulationShow=systemNoFormatList.get(systemNoFormatList.size()-1).getEmulationShow();//获取最后一个展示效果
            //先删除原有的档号设置项
            systemNoFormatMapper.delSystemNoFormatByEntityCode(systemNoFormatList.get(0).getEntityCode());
            for (int i = 0; i <systemNoFormatList.size() ; i++) {
                //添加新的档号设置项
                systemNoFormatList.get(i).setStyle("1");
                systemNoFormatList.get(i).setNoFormatCode(StringUtil.getUuid());
                systemNoFormatList.get(i).setEmulationShow(emulationShow);
                result=systemNoFormatMapper.addSystemNoFormat(systemNoFormatList.get(i));
                if(result==0){
                    return result;
                }
            }

        }
        return result;
    }

    /**获取档号设置列表
     * 获取tableCode
     * @param entityCode
     * @return
     */
    @Override
    public List<Map<String,String>>  getSystemNoFormatColumnsByEntityCode(String entityCode) {
        return classTreeMapper.getTableColumnslByEntityCode(entityCode);
    }
}
