package com.wsda.project.service.impl;

import com.wsda.project.dao.ArchivesSealMapper;
import com.wsda.project.model.ArchivesSeal;
import com.wsda.project.service.ArchivesSealService;
import com.wsda.project.util.Graphics2DRectangleImage;
import com.wsda.project.util.StringUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.List;

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
        Integer result = archivesSealMapper.delArchivesSeal(id);
        if (result >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addArchivesSeal(ArchivesSeal archivesSeal) {
        boolean bool=true;
        ArchivesSeal ar = archivesSealMapper.getArchivesSealByTableCode(archivesSeal.getTableCode());//一个表只有一个章
        if (ar != null) {
            File file=new File(ar.getPath());
            if(file!=null&&file.exists()){
                file.delete();
                System.out.println("归档章图片文件：" + file.getPath() + " 删除成功！");
            }else {
                System.out.println("归档章图片文件：" + file.getPath() + "不存在！");
            }
            delArchivesSeal(ar.getId());//删除当前表旧数据
        }
        Integer max = archivesSealMapper.getSerialMax();//序号;
        if (max != null && max > 0) {
            archivesSeal.setSerial(max);//序号
        } else {
            archivesSeal.setSerial(0);//序号
        }
        //生成归档章
        PropertiesConfiguration properties = null;
        String pdfPath = "";
        try {
            List<String> columnContentList= Arrays.asList(archivesSeal.getColumnContent().split(","));
            properties = new PropertiesConfiguration(StringUtil.getRealPathByPack());
            pdfPath = properties.getString("archivesealpath");//归档章图片保存地址
            pdfPath=pdfPath+"\\"+archivesSeal.getTableCode()+".png";
            bool=Graphics2DRectangleImage.graphicsGeneration(null,columnContentList.size()/2,pdfPath);
            if(bool){
                System.out.println("生成归档章图片成功");
                archivesSeal.setPath(pdfPath);
                int result = archivesSealMapper.addArchivesSeal(archivesSeal);
                if (result > 0) {
                    return true;
                }else{
                    File file=new File(ar.getPath());
                    if(file!=null&&file.exists()){
                        file.delete();
                        System.out.println("归档章图片文件：" + file.getPath() + " 删除成功！");
                    }else {
                        System.out.println("归档章图片文件：" + file.getPath() + "不存在！");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            bool=false;
        }
        return bool;
    }
}
