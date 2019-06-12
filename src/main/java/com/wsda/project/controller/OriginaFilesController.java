package com.wsda.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.OriginaFilesServiceImpl;
import com.wsda.project.service.impl.TableViewServiceImpl;
import com.wsda.project.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("原文Controller")
public class OriginaFilesController {
    private Logger logger = LoggerFactory.getLogger(OriginaFilesController.class);
    @Resource
    private OriginaFilesServiceImpl originaFilesService;
    @Resource
    private TableViewServiceImpl tableViewService;
    @Resource
    private TableViewMapper tableViewMapper;
    @Value("${spring.servlet.multipart.max-file-size}")
    private String fileSize;//单个文件大小
    @Value("${spring.servlet.multipart.max-request-size}")
    private String requestSize;//总大小

    @ApiOperation(value = "获取档案下原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFileSByRecordCode", method = RequestMethod.POST)
    public ResponseResult getOriginaFileSByRecordCode(@ApiParam(required = false, name = "tableCode", value = "原文表编号") @RequestParam(name = "tableCode", required = false, defaultValue = "187530") String tableCode,
                                                      @ApiParam(required = true, name = "recordCode", value = "档案唯一编号") @RequestParam(name = "recordCode", required = true) String recordCode,
                                                      @ApiParam(required = false, name = "pageNum", value = "当前页") @RequestParam(name = "pageNum", required = true) Integer pageNum,
                                                      @ApiParam(required = false, name = "pageSize", value = "每页条目数") @RequestParam(name = "pageSize", required = true) Integer pageSize,
                                                      @ApiParam(required = true, name = "type", value = "是否被删除（0，1）") @RequestParam(name = "type", required = true) String type) {
        if (recordCode != null && pageNum != null && pageSize != null) {
            Map<String, Object> pageFileDate = originaFilesService.getFilesByRecordCode(tableCode, recordCode, pageNum, pageSize,type);
            if (pageFileDate.get("originaFilePageInfo") != null) {
                return new ResponseResult(ResponseResult.OK, "获取原文信息成功", pageFileDate, true);
            } else {
                return new ResponseResult(ResponseResult.OK, "当前档案暂无原文可供查看", pageFileDate, true);
            }

        } else {
            return new ResponseResult(ResponseResult.OK, "参数查询不完整", null, false);
        }
    }

    @ApiOperation(value = "修改原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upOriginaFilesByFileCode", method = RequestMethod.POST)
    public ResponseResult upOriginaFilesByFileCode(@ApiParam(required = false, name = "tableCode", value = "原文表编号") @RequestParam(name = "tableCode", required = false, defaultValue = "187530") String tableCode,
                                                   @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode,
                                                   @ApiParam(required = true, name = "params", value = "修改列详情") @RequestParam(name = "params", required = true) String params) {
        Map<String, String> paramsMap = new HashMap<>();
        Type typeObj = new TypeToken<Map<String, String>>() {
        }.getType();
        if (params != null) {
            paramsMap = JSONObject.parseObject(params, typeObj);//JSONObject转换map
        }
        boolean result = tableViewService.upArchivesByRecordCode(tableCode, fileCode, paramsMap, 1);
        if (result) {
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        } else {
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }

    @ApiOperation(value = "恢复删除原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upOriginaFilesArchives", method = RequestMethod.POST)
    public ResponseResult upOriginaFilesArchives(@ApiParam(required = false, name = "tableCode", value = "原文表编号") @RequestParam(name = "tableCode", required = false, defaultValue = "187530") String tableCode,
                                                 @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode,
                                                 @ApiParam(required = true, name = "trashStatus", value = "回收站(0恢复,1放入回收站)") @RequestParam(name = "trashStatus", required = true) String trashStatus) {
        boolean result = true;
        try {
            Type typeObj = new TypeToken<List<String>>() {
            }.getType();
            List<String> recordCodeList = JSONObject.parseObject(fileCode, typeObj);//JSONObject转换map
            result = tableViewService.upArchives(tableCode, recordCodeList, trashStatus, 1);
        } catch (Exception e) {
            result = false;
        }
        if (result) {//不能真正删除档案，只做状态码的改变放入回收站即可
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        } else {
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }


    @ApiOperation(value = "获取当前文件条目详情", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFilesArchives", method = RequestMethod.POST)
    public ResponseResult getOriginaFilesArchives(@ApiParam(required = true, name = "tableCode", value = "原文表编号") @RequestParam(name = "tableCode", required = false, defaultValue = "187530") String tableCode,
                                                  @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode) {
        Map<String, String> result = tableViewService.getArchives(tableCode, fileCode, 1);
        if (result != null && result.size() > 0) {
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        } else {
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }


    @ApiOperation(value = "上传新档案原文（多文件上传）", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upLoadFiles", method = RequestMethod.POST)
    public ResponseResult upLoadFiles(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                      @ApiParam(required = true, name = "recordCode", value = "原文主键") @RequestParam(name = "recordCode", required = true) String recordCode,
                                      @ApiParam(required = true, name = "file", value = "多文件上传") @RequestParam("file") MultipartFile[] files,HttpServletRequest request) {

        ResponseResult responseResult=null;
        Map<String,String>  OriginaPathMap=originaFilesService.getUpLoadFilePath();//查询原文存放的地址
        String upPath=OriginaPathMap.get("STORETYPE");//存放的是 LOCAL 还是FTP
        if("LOCAL".equals(upPath)) {//上传本地
            String fileSavePath = OriginaPathMap.get("FILELOC");//获取本地上传的地址D:/archive
            String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//获取档案条目表名
            fileSavePath=fileSavePath+File.separator+tableName;
//            文件上传路径由档案表名称  yyyy MM dd hh mm ss
            String[] dates = OriginaPathMap.get("TIMESTYLEPOS").split("/");//获取文件夹名称
            fileSavePath= StringUtil.getFileData(fileSavePath,dates);
            responseResult=upload(files,fileSavePath);
        }
        if(responseResult.isSuccess()){
            List<Map<String, String>> parmsMap= (List<Map<String, String>>) responseResult.getData();//获取count和 originFilePath源文件路径 pdf路径
            if(parmsMap!=null&&parmsMap.size()>0){
                for (int i = 0; i <parmsMap.size() ; i++) {
                    if(parmsMap.get(i).get("pdfPath")!=null){
                        OriginaPathMap.put("pdfPath",parmsMap.get(i).get("pdfPath"));//pdf位置
                    }
                    if(parmsMap.get(i).get("originFilePath")!=null){
                        OriginaPathMap.put("originFilePath",parmsMap.get(i).get("originFilePath"));//原文位置
                    }
                    //获取文件存放位置
                    originaFilesService.upLoadFiles(OriginaPathMap, tableCode, recordCode, Integer.parseInt(String.valueOf(parmsMap.get(parmsMap.size()-1))));
                }
                responseResult=new ResponseResult(ResponseResult.OK, "上传成功", "", false);
            }else {
                responseResult=new ResponseResult(ResponseResult.OK, "上传失败", "", false);
            }
            return responseResult;
        }else {
            return responseResult;
        }
    }


    /**
     * 上传操作,支持多文件上传
     *
     * @param files   文件数组
     * @param files   文件保存地址
     */
    private ResponseResult upload(MultipartFile[] files, String filepath) {
        List<Object> parmsList=new ArrayList<>();
        Map<String,String> parmsMap=new HashMap<>();
        int count = 0; //文件数统计
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                BufferedOutputStream out=null;
                try {
                    File newFile=new File(filepath);
                    if(!newFile.exists()||!newFile.isDirectory()){
                        newFile.mkdirs();//创建文件夹
                    }
                    String newPath=newFile.getPath()+File.separator + StringUtil.getUuid() + "-" + files[i].getOriginalFilename();//文件地址
                    parmsMap.put("originFilePath",newPath);//源文件存放路径
                    // 使用UUID确保上传文件不重复
                    out = new BufferedOutputStream(new FileOutputStream(new File(newPath)));
                    out.write(files[i].getBytes());
                    out.flush();
                    count++;
                    boolean suffix=StringUtil.getFileSuffix(newPath,StringUtil.getPdfPath(newPath));//原文后缀 是否能转换pdf
                    if(suffix){
                        logger.info("原文转换:"+suffix+"成功--原文路径:"+newPath+"---pdf路径:"+StringUtil.getPdfPath(newPath));
                        parmsMap.put("pdfPath",StringUtil.getPdfPath(newPath));
                    }
                    parmsList.add(parmsMap);//放入集合返回
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseResult(ResponseResult.OK, e.getMessage(), "", false);
                } finally {
                    if(out!=null){
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ResponseResult(ResponseResult.OK, e.getMessage(), "", false);
                        }
                    }
                }
            }
        }
        if (0 == count){
            return new ResponseResult(ResponseResult.OK,"上传失败", false);
        }
        parmsList.add(count);
        return new ResponseResult(ResponseResult.OK, "文件上传成功", parmsList, true);
    }

}
