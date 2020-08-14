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
import java.io.IOException;
import java.util.ArrayList;
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
    public RS batchAddWebsite(MultipartFile file) throws IOException {

        Map<String, ArrayList<SimpleWebsite>> arrayListMapWebsite = tbWebsiteService.batchAddWebsite(file, tbWebsiteService);
        return RS.success().message("添加成功").data("batchAddResult", arrayListMapWebsite);
    }

    @ApiOperation(value = "批量删除网站")
    @DeleteMapping ("/batchDelWebsite/{idList}")
    public RS batchDelWebsite(
            @ApiParam(name = "idList", value = "网站ID数组", required = true, allowMultiple = true)
            @PathVariable("idList") ArrayList<String> idList
    ) throws IOException {
        ArrayList<SimpleWebsite> simpleWebsites = new ArrayList<>();

        System.out.println(idList);

        if(idList.size() < 1) {
            throw new CustomException("OW20006", "输入的网站ID数组为空！");
        }
        tbWebsiteService.batchDelWebsite(idList);

        return RS.success();
    }

    @ApiOperation(value = "查询用户上传的网站")
    @GetMapping("/selectAllWebsite/{idAdmin}")
    public RS selectAllWebsite(
        @ApiParam(name = "idAdmin", value = "用户id")
        @PathVariable String idAdmin
    ) {
        List<TbWebsite> tbWebsites = tbWebsiteService.selectAllWebsite(idAdmin);

        return RS.success().data("tbWebsites", tbWebsites);
    }

}

