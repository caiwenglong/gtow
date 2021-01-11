package com.yby.uAdmin.controller;


import com.yby.common.entity.SimpleWebsite;
import com.yby.common.entity.TbWebsite;
import com.yby.commonUtils.RS;
import com.yby.service.base.exception.CustomException;
import com.yby.service.base.exception.GlobalExceptionHandle;
import com.yby.uAdmin.service.TbWebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-17
 */
@Api(value = "管理网站")
@RestController
@RequestMapping("/uAdmin/tb-website")
@CrossOrigin
public class TbWebsiteController {

    @Autowired
    TbWebsiteService tbWebsiteService;

    @ApiOperation(value = "添加网站")
    @PostMapping("/addWebsite")
    public RS addWebsite(
            @ApiParam(name = "website", value = "网站对象")
            @RequestBody  SimpleWebsite website) {
        tbWebsiteService.addWebsite(website);
        return RS.success().data("website", website);
    }

    @ApiOperation(value = "通过excel批量添加网站")
    @PostMapping("batchAddWebsite")
    public RS batchAddWebsite(
            @RequestParam(value = "idAdmin") String idAdmin,
            @RequestParam(value = "file") MultipartFile file
    ) {

        Map<String, ArrayList<SimpleWebsite>> arrayListMapWebsite = tbWebsiteService.batchAddWebsite(file, idAdmin);
        return RS.success().message("添加成功").data("batchAddResult", arrayListMapWebsite);
    }

    @ApiOperation(value="修改网站")
    @PostMapping("/modifyWebsite")
    public RS modifyWebsite(
            @ApiParam(name = "website", value = "网站对象")
            @RequestBody SimpleWebsite website
    ){
        tbWebsiteService.modifyWebsite(website);
        return RS.success();
    }

    @ApiOperation(value="删除网站")
    @DeleteMapping("/delWebsiteById/{idWebsite}")
    public RS delWebsiteById(
            @ApiParam(name = "idWebsite", value = "网站ID", required = true)
            @PathVariable("idWebsite") String idWebsite
    ) {
        tbWebsiteService.delWebsiteById(idWebsite);
        return RS.success();
    }

    @ApiOperation(value = "批量删除网站")
    @DeleteMapping ("/batchDelWebsite")
    public RS batchDelWebsite(
            @ApiParam(name = "idList", value = "网站ID数组", required = true, allowMultiple = true)
            @RequestBody ArrayList<String> idList
    ) {
        if(idList.size() < 1) {
            throw new CustomException("OW20006", "输入的网站ID数组为空！");
        }
        tbWebsiteService.batchDelWebsite(idList);

        return RS.success();
    }

    @ApiOperation(value = "通过网站ID查询网站详细信息")
    @GetMapping("/selectWebsiteById/{id}")
    public RS selectWebsiteById(
            @ApiParam(name = "id", value = "网站id")
            @PathVariable String id
    ) {
        TbWebsite tbWebsite = tbWebsiteService.selectWebsiteById(id);
        return RS.success().data("tbWebsite", tbWebsite);
    }

    @ApiOperation(value = "查询用户上传的网站")
    @GetMapping("/selectAllWebsite/{idAdmin}/{pageNum}/{pageSize}")
    public RS selectAllWebsite(
        @ApiParam(name = "idAdmin", value = "用户id")
        @PathVariable String idAdmin,
        @PathVariable Integer pageNum,
        @PathVariable Integer pageSize
    ) {
        HashMap<Object, Object> tbWebsitesData = tbWebsiteService.selectAllWebsite(idAdmin, pageNum, pageSize);
        ArrayList<TbWebsite> tbWebsites = (ArrayList<TbWebsite>) tbWebsitesData.get("websiteList");
        Integer total = (Integer) tbWebsitesData.get("total");
        return RS.success().data("tbWebsites", tbWebsites).data("total", total);
    }

    @ApiOperation(value="网站关键字")
    @GetMapping("/modifyWebsiteKeywords/{websiteId}/{keywords}")
    public RS modifyWebsiteKeywords(
            @ApiParam(name = "keywords", value = "网站关键字")
            @PathVariable String keywords,
            @PathVariable String websiteId){
        if(keywords.equals("null")) {
            keywords = "";
        }
        tbWebsiteService.modifyWebsiteKeywords(websiteId, keywords);
        return RS.success();
    }

}

