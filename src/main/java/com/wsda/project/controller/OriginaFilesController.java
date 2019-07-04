package com.wsda.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.itextpdf.text.DocumentException;
import com.wsda.project.dao.DepartementMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.ArchivesSeal;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.ArchivesSealServiceImpl;
import com.wsda.project.service.impl.OriginaFilesServiceImpl;
import com.wsda.project.service.impl.TableViewServiceImpl;
import com.wsda.project.util.Change2PDF;
import com.wsda.project.util.Graphics2DRectangleImage;
import com.wsda.project.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Resource
    private DepartementMapper departementMapper;

    @Resource
    private ArchivesSealServiceImpl archivesSealService;

    @ApiOperation(value = "获取档案下原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFileSByRecordCode", method = RequestMethod.POST)
    public ResponseResult getOriginaFileSByRecordCode(@ApiParam(required = false, name = "tableCode", value = "原文表编号") @RequestParam(name = "tableCode", required = false, defaultValue = "187530") String tableCode,
                                                      @ApiParam(required = true, name = "recordCode", value = "档案唯一编号") @RequestParam(name = "recordCode", required = true) String recordCode,
                                                      @ApiParam(required = false, name = "pageNum", value = "当前页") @RequestParam(name = "pageNum", required = true) Integer pageNum,
                                                      @ApiParam(required = false, name = "pageSize", value = "每页条目数") @RequestParam(name = "pageSize", required = true) Integer pageSize,
                                                      @ApiParam(required = true, name = "type", value = "是否被删除（0，1）") @RequestParam(name = "type", required = true) String type) {
        if (recordCode != null && pageNum != null && pageSize != null) {
            Map<String, Object> pageFileDate = originaFilesService.getFilesByRecordCode(tableCode, recordCode, pageNum, pageSize, type);
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

    @ApiOperation(value = "删除原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upOriginaFilesArchives", method = RequestMethod.POST)
    public ResponseResult upOriginaFilesArchives(@ApiParam(required = false, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                                 @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode) {
        boolean result = originaFilesService.delOrigianFileByFileCode(tableCode, fileCode);
        if (result) {
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


    @ApiOperation(value = "获取pdf查看路径", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getPDFUrlByParameter", method = RequestMethod.POST)
    public ResponseResult getPDFUrlByParameter(@ApiParam(required = true, name = "fileCode", value = "原文编号") @RequestParam(name = "fileCode", required = true) String fileCode,
                                               @ApiParam(required = true, name = "tableCode", value = "表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                               @ApiParam(required = true, name = "recordCode", value = "当前档案条目编号") @RequestParam(name = "recordCode", required = true) String recordCode,
                                               HttpServletRequest request) {
//        start 归档章
        ArchivesSeal archivesSeal = archivesSealService.getArchivesSeal(tableCode);//获取归档章
        List<Object> cols = null;//获取归档章对应字段
        if (archivesSeal != null) {
            List<String> templList = Arrays.asList(archivesSeal.getColumnContent().split(","));
            Map<String, String> parms = new HashMap<>();
            String tableName = tableViewMapper.getTableNameByTableCode(archivesSeal.getTableCode());//获取实体表名称
            String sql = archivesSeal.getColumnContent();
            parms.put("tableName", tableName);
            parms.put("sql", sql);
            parms.put("recordCode", recordCode);
            Map<String, String> lll = tableViewMapper.getArchivesByIsArchiveFlag(parms);//获去归档的档案 归档信息
            if (lll != null) {
                cols = new ArrayList<>();
                for (int i = 0; i < templList.size(); i++) {
                    String temp = templList.get(i).toUpperCase();
                    if (lll.containsKey(temp)) {
                        if ("departmentCode".equalsIgnoreCase(temp)) {
                            String departmentName = departementMapper.getDepartementNameByDepartementCode(lll.get(temp));
                            cols.add(i, departmentName);
                        } else if ("createDate".equalsIgnoreCase(temp)) {
                            SimpleDateFormat SFDate = new SimpleDateFormat("yyyyMMdd");
                            String resultTime= SFDate.format((Object) lll.get(temp));
                            cols.add(i, resultTime);
                        } else {
                            cols.add(i, lll.get(temp));
                        }
                    } else {
                        cols.add(i, "");
                    }
                }
            }
        }
//end

        Map<String, String> stringMap = originaFilesService.getUpLoadFilePath();//获取保存路径
        String watermarkTxt = stringMap.get("WATERMARKTXT");
        Map<String, String> url = originaFilesService.getPDFUrlByFileCode(fileCode);//获取查看文件
        if (url != null) {
            File file = new File(url.get("PDFPATH"));//PDFPATH,ORIGINAPATH
            if (file.exists()) {//如果有pdf
                if (watermarkTxt != null && !"".equals(watermarkTxt)) {
                    //start 开始添加水印
                    String watermarkPath = null;
                    try {
                        watermarkPath = Change2PDF.addtextWatermark(file, watermarkTxt);
                        originaFilesService.upWatermarkPath(fileCode, watermarkPath);
                        if (cols != null) {
                            boolean bool = Graphics2DRectangleImage.graphicsGeneration(cols, cols.size() / 2, archivesSeal.getPath());
                            if (bool) {
                                File fileW = new File(watermarkPath);
                                if (fileW.exists()) {
                                    watermarkPath = Change2PDF.addimageWatermark(fileW, archivesSeal.getPath());
                                }
                            }
                        }

                        watermarkPath = watermarkPath.substring(watermarkPath.indexOf(":") + 1, watermarkPath.length());
                        return new ResponseResult(ResponseResult.OK, "pdf文件返回成功", watermarkPath, true);
                    } catch (IOException e) {
                        logger.error("水印添加异常：" + e);
                    } catch (DocumentException e) {
                        logger.error("水印添加异常：" + e);
                    }
                    //end添加结束
                }
                String urlPDf = file.getPath().substring(file.getPath().indexOf("\\") + 1, file.getPath().length());
                return new ResponseResult(ResponseResult.OK, "返回PDFUrl成功", urlPDf, true);
            } else {
                String originaUrl = url.get("ORIGINAPATH");//原文路径
                file = new File(originaUrl);
                if (file.exists()) {
                    if (StringUtil.getFileType(originaUrl)) {
                        //原文支持在线查看，但pdf文件丢失就重新转换并返回路径
                        boolean bool = StringUtil.getFileSuffix(originaUrl, url.get("PDFPATH"));
                        if (bool) {
                            if (watermarkTxt != null && !"".equals(watermarkTxt)) {
//                            start 开始添加水印
                                String watermarkPath = null;
                                try {
                                    watermarkPath = Change2PDF.addtextWatermark(new File(StringUtil.getPdfPath(url.get("PDFPATH"))), watermarkTxt);
                                    if (cols != null) {
                                        bool = Graphics2DRectangleImage.graphicsGeneration(cols, cols.size() / 2, archivesSeal.getPath());
                                        if (bool) {
                                            File fileW = new File(watermarkPath);
                                            if (fileW.exists()) {
                                                watermarkPath = Change2PDF.addimageWatermark(fileW, archivesSeal.getPath());
                                            }
                                        }
                                    }
                                    originaFilesService.upWatermarkPath(fileCode, watermarkPath);
                                    watermarkPath = watermarkPath.substring(watermarkPath.indexOf(":") + 1, watermarkPath.length());
                                } catch (IOException e) {
                                    logger.error("水印添加异常：" + e);
                                } catch (DocumentException e) {
                                    logger.error("水印添加异常：" + e);
                                }
//                            end添加结束
                                return new ResponseResult(ResponseResult.OK, "pdf文件返回成功", watermarkPath, true);
                            }
                            String pdfPath = url.get("PDFPATH").substring(url.get("PDFPATH").indexOf(":") + 1, url.get("PDFPATH").length());
                            return new ResponseResult(ResponseResult.OK, "pdf文件返回成功", pdfPath, true);
                        } else {
                            return new ResponseResult(ResponseResult.OK, "pdf文件丢失,转换失败,请重新上传", false);
                        }
                    } else {
                        return new ResponseResult(ResponseResult.OK, "暂不支持该【" + originaUrl.substring(originaUrl.lastIndexOf(".") + 1, originaUrl.length()) + "】文件在线浏览", false);
                    }

                } else {
                    return new ResponseResult(ResponseResult.OK, "上传原文不存在,请重新上传", false);
                }
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "上传原文不存在,请重新上传", false);
        }
    }

    @ApiOperation(value = "上传新档案原文（多文件上传）", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upLoadFiles", method = RequestMethod.POST)
    public ResponseResult upLoadFiles(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                      @ApiParam(required = true, name = "recordCode", value = "原文主键") @RequestParam(name = "recordCode", required = true) String recordCode,
                                      @ApiParam(required = true, name = "file", value = "多文件上传") @RequestParam("file") MultipartFile[] files) {
        ResponseResult responseResult = null;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                boolean fileMaxBool = StringUtil.checkFileSize((long) files[i].getSize(), 100, "M");
                if (!fileMaxBool) {
                    return new ResponseResult(ResponseResult.OK, "上传文件大于限制的100MB,无法上传", false);
                }
            }
        }
        Map<String, String> OriginaPathMap = originaFilesService.getUpLoadFilePath();//查询原文存放的地址
        String upPath = OriginaPathMap.get("STORETYPE");//存放的是 LOCAL 还是FTP
        if ("LOCAL".equals(upPath)) {//上传本地
            String fileSavePath = OriginaPathMap.get("FILELOC");//获取本地上传的地址D:/archive
            String tableName = tableViewMapper.getTableNameByTableCode(tableCode);//获取档案条目表名
            fileSavePath = fileSavePath + File.separator + tableName;
//            文件上传路径由档案表名称  yyyy MM dd hh mm ss
            String[] dates = OriginaPathMap.get("TIMESTYLEPOS").split("/");//获取文件夹名称
            fileSavePath = StringUtil.getFileData(fileSavePath, dates);
            responseResult = upload(files, fileSavePath.trim());
        }
        if (responseResult.isSuccess()) {
            Map<String, Object> parmsAllMap = (Map<String, Object>) responseResult.getData();//获取count和 originFilePath源文件路径 pdf路径
            int count = (int) parmsAllMap.get("count");
            Map<String, String> parmsMap = (Map<String, String>) parmsAllMap.get("parmsMap");
            //获取文件存放位置
            originaFilesService.addUpLoadFiles(parmsMap, tableCode, recordCode, count);
            responseResult = new ResponseResult(ResponseResult.OK, "上传成功", true);
        }
        return responseResult;
    }

    /**
     * 上传操作,支持多文件上传
     *
     * @param files 文件数组
     * @param files 文件保存地址
     */
    private ResponseResult upload(MultipartFile[] files, String filepath) {
        Map<String, Object> parmsAllMap = new HashMap<>();
        Map<String, String> parmsMap = new HashMap<>();
        int count = 0; //文件数统计
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                BufferedOutputStream out = null;
                try {
                    File newFile = new File(filepath);
                    if (!newFile.exists() || !newFile.isDirectory()) {
                        newFile.mkdirs();//创建文件夹
                    }
                    parmsMap.put("originFileName", files[i].getOriginalFilename());//源文件名称
                    String newPath = newFile.getPath() + File.separator + StringUtil.getUuid() + "-" + files[i].getOriginalFilename();//文件地址
                    parmsMap.put("originFilePath", newPath);//源文件存放路径
                    // 使用UUID确保上传文件不重复
                    File newFileS = new File(newPath);//原文
                    out = new BufferedOutputStream(new FileOutputStream(newFileS));
                    out.write(files[i].getBytes());
                    out.flush();
                    count++;
                    boolean suffix = StringUtil.getFileSuffix(newFileS.getPath(), StringUtil.getPdfPath(newPath));//原文后缀 是否能转换pdf
                    if (suffix) {
                        String suffixPDF = newPath.substring(newPath.lastIndexOf("."), newPath.length());
                        if (".pdf".equalsIgnoreCase(suffixPDF)) {
                            logger.info("原文转换:" + suffix + "成功--原文路径:" + newPath + "---pdf路径:" + newPath);
                            parmsMap.put("pdfPath", newPath);
                            parmsMap.put("type", "0");//0 pdf文件,1没有文件
                        } else {
                            logger.info("原文转换:" + suffix + "成功--原文路径:" + newPath + "---pdf路径:" + StringUtil.getPdfPath(newPath));
                            parmsMap.put("pdfPath", StringUtil.getPdfPath(newPath));
                            parmsMap.put("type", "0");//0 pdf文件,1没有文件
                        }

                    } else {
                        parmsMap.put("type", "1");//0 pdf文件,1没有文件
                    }
                    parmsMap.put("fileSize", newFileS.length() / 1024 + "");//文件大小
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseResult(ResponseResult.OK, e.getMessage(), "", false);
                } finally {
                    if (out != null) {
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
        if (0 == count) {
            return new ResponseResult(ResponseResult.OK, "上传失败", false);
        }
        parmsAllMap.put("parmsMap", parmsMap);//原文和pdf地址
        parmsAllMap.put("count", count);//总条数
        return new ResponseResult(ResponseResult.OK, "文件上传成功", parmsAllMap, true);
    }


    @ApiOperation(value = "修改添加水印文字", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upWatermarkTxt", method = RequestMethod.POST)
    public ResponseResult upWatermarkTxt(@RequestParam(name = "watermarkTxt", required = true) String watermarkTxt) {
        Map<String, String> OriginaPathMap = originaFilesService.getUpLoadFilePath();//查询原文存放的地址
        String storeId = OriginaPathMap.get("STOREID");
        boolean bool = originaFilesService.upWatermarkTxt(storeId, watermarkTxt);
        if (bool) {
            return new ResponseResult(ResponseResult.OK, "修改水印文字成功", bool);
        } else {
            return new ResponseResult(ResponseResult.OK, "修改水印文字失败", bool);
        }
    }


    @ApiOperation(value = "获取水印文字", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getWatermarkTxt", method = RequestMethod.POST)
    public ResponseResult getWatermarkTxt() {
        Map<String, String> WatermarkTxtPath = originaFilesService.getUpLoadFilePath();
        if (WatermarkTxtPath != null) {
            return new ResponseResult(ResponseResult.OK, "获取水印文字成功", WatermarkTxtPath.get("WATERMARKTXT"), true);
        } else {
            return new ResponseResult(ResponseResult.OK, "获取水印文字失败", false);
        }
    }


    @ApiOperation(value = "下载文件", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public ResponseResult download(@RequestParam(name = "fileCode", required = true) String fileCode, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult responseResult = null;
        Map<String, String> originaFile = originaFilesService.getPDFUrlByFileCode(fileCode);//获取查看文件
        if (originaFile != null && originaFile.get("ORIGINAPATH") != null) {
            File file = new File(String.valueOf(originaFile.get("ORIGINAPATH")));
            if (file.exists()) {
                String urlPDf = file.getPath().substring(file.getPath().indexOf("\\") + 1, file.getPath().length());
                responseResult = new ResponseResult(ResponseResult.OK, "原文下载成功", urlPDf, true);
                return responseResult;
            }
        }
        responseResult = new ResponseResult(ResponseResult.OK, "原文下载失败,当前原文不存在", false);
        return responseResult;
    }

}
