package com.wsda.project.controller;

import com.wsda.project.model.CreateCollectionFiles;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.SystemUser;
import com.wsda.project.model.SystemUserCollection;
import com.wsda.project.service.impl.SystemUserCollectionServiceImpl;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 收藏
 */
@RestController
@Api("收藏Controller")
public class CollectionController {
    private Logger logger = LoggerFactory.getLogger(CollectionController.class);
    @Resource
    private SystemUserCollectionServiceImpl systemUserCollectionService;

    @ApiOperation(value = "获取收藏列表", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getAllCollectionByUserCode", method = RequestMethod.POST)
    public ResponseResult getAllCollectionByUserCode(@ApiParam(required = true, name = "cid", value = "收藏夹编号")@RequestParam(name = "cid", required = true)String cid, HttpServletRequest request) {
        HttpSession session=request.getSession();
        SystemUser user= (SystemUser) session.getAttribute("user");
        if (cid != null) {
            List<SystemUserCollection> collections = systemUserCollectionService.getAllCollectionByUserCode(cid,user);
            return new ResponseResult(ResponseResult.OK, "获取用户收藏列表成功", collections,true);
        } else {
            return new ResponseResult(ResponseResult.OK, "获取用户收藏列表失败", false);
        }
    }

    @ApiOperation(value = "添加收藏", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addCollection", method = RequestMethod.POST)
    public ResponseResult addCollection(@ApiParam(required = true, name = "archivesCode", value = "档案编号")@RequestParam(name = "archivesCode", required = true)String archivesCode,
                                        @ApiParam(required = true, name = "cid", value = "档案编号")@RequestParam(name = "cid", required = true)String cid,
                                        @ApiParam(required = true, name = "tableCode", value = "表编号")@RequestParam(name = "tableCode", required = true)String tableCode,HttpServletRequest request) {
        HttpSession session=request.getSession();
        SystemUser user= (SystemUser) session.getAttribute("user");
        if (user != null) {//,user.getUserCode(),tableCode,archivesCode,null
            SystemUserCollection userCollection=new SystemUserCollection();
            userCollection.setId(StringUtil.getUuid());//id
            userCollection.setUserCode(user.getUserCode());//用户编号
            userCollection.setArchivesCode(archivesCode);//档案编号
            userCollection.setTableCode(tableCode);//表编号
            userCollection.setCreateTime(StringUtil.getDateByType("yyyy-MM-dd"));//创建时间
            userCollection.setCollectionFilesId(cid);
            boolean bool = systemUserCollectionService.addCollection(userCollection);
            if(bool){
                return new ResponseResult(ResponseResult.OK, "添加用户收藏成功",bool);
            }else{
                return new ResponseResult(ResponseResult.OK, "添加用户收藏失败",bool);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "参数错误", false);
        }
    }

    @ApiOperation(value = "删除收藏条目", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/delCollectionByUserCode", method = RequestMethod.POST)
    public ResponseResult delCollectionByUserCode(@ApiParam(required = true, name = "archivesCode", value = "收藏编号")@RequestParam(name = "recordCode", required = true)String id) {
        if (id != null) {
            boolean bool = systemUserCollectionService.delCollectionByUserCode(id);
            if(bool){
                return new ResponseResult(ResponseResult.OK, "获取用户收藏列表成功",bool);
            }else{
                return new ResponseResult(ResponseResult.OK, "参数错误",bool);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "删除的列不存在", false);
        }
    }




    @ApiOperation(value = "获取收藏夹列表", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getAllCreateCollectionFiles", method = RequestMethod.POST)
    public ResponseResult getAllCreateCollectionFiles(HttpServletRequest request) {
        HttpSession session=request.getSession();
        SystemUser user= (SystemUser) session.getAttribute("user");
        List<CreateCollectionFiles> createCollectionFiles=systemUserCollectionService.getCreateCollectionFiles(user.getUserCode());
        return new ResponseResult(ResponseResult.OK, "返回收藏夹成功",createCollectionFiles, true);
    }

    @ApiOperation(value = "添加收藏夹", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addCreateCollectionFiles", method = RequestMethod.POST)
    public ResponseResult addCreateCollectionFiles(@ApiParam(required = true, name = "collectionFilesName", value = "收藏夹名称")@RequestParam(name = "collectionFilesName", required = true)String collectionFilesName, HttpServletRequest request) {
        HttpSession session=request.getSession();
        SystemUser user= (SystemUser) session.getAttribute("user");
        CreateCollectionFiles createCollectionFiles=new CreateCollectionFiles();
        createCollectionFiles.setId(StringUtil.getUuid());
        createCollectionFiles.setCreateTime(StringUtil.getDateByType("yyyy-MM-dd"));
        createCollectionFiles.setUserCode(user.getUserCode());
        createCollectionFiles.setCollectionName(collectionFilesName);
        boolean bool=systemUserCollectionService.addCreateCollectionFiles(createCollectionFiles);
        if(bool){
            return new ResponseResult(ResponseResult.OK, "OK", true);
        }
        return new ResponseResult(ResponseResult.OK, "No", false);
    }

    @ApiOperation(value = "删除收藏夹", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/delCreateCollectionFiles", method = RequestMethod.POST)
    public ResponseResult delCreateCollectionFiles(@ApiParam(required = true, name = "id", value = "收藏夹编号")@RequestParam(name = "id", required = true)String id) {
        boolean bool=systemUserCollectionService.delCreateCollectionFiles(id);
        if(bool){
            return new ResponseResult(ResponseResult.OK, "OK", bool);
        }
        return new ResponseResult(ResponseResult.OK, "No", bool);
    }
}
