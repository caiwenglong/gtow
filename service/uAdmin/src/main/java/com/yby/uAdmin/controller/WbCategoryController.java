package com.yby.uAdmin.controller;


import com.yby.common.entity.SimpleWbCategory;
import com.yby.common.entity.SimpleWebsite;
import com.yby.common.entity.WbCategory;
import com.yby.commonUtils.RS;
import com.yby.uAdmin.service.WbCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-09-15
 */
@Api(value = "管理网站")
@RestController
@RequestMapping("/uAdmin/wb-category")
@CrossOrigin
public class WbCategoryController {

    @Autowired
    private WbCategoryService wbCategoryService;

    @ApiOperation(value = "通过ID获取网站分类")
    @GetMapping("/getWbCategory/{id}")
    public RS getWbCategory(
            @ApiParam(name = "id", value = "网站分类ID")
            @PathVariable String id
    ) {

        WbCategory wbCategory = wbCategoryService.getWbCategoryById(id);
        return RS.success().data("wbCategory", wbCategory);
    }

    @ApiOperation(value = "获得所有的网站分类")
    @GetMapping("/getAllWbCategory")
    public RS getAllWbCategory() {
        ArrayList<WbCategory> allWbCategory = (ArrayList<WbCategory>) wbCategoryService.getAllWbCategory();
        return RS.success().data("wbCategory", allWbCategory);
    }

    @ApiOperation(value = "通过excel批量添加网站")
    @PostMapping("batchAddWbCategory")
    public RS batchAddWbCategory(MultipartFile file) {

        Map<String, ArrayList<String>> arrayListMapWbCategory = wbCategoryService.batchAddWbCategory(file);
        return RS.success().message("添加成功").data("batchAddResult", arrayListMapWbCategory);
    }

    @ApiOperation(value = "通过ID删除分类")
    @DeleteMapping("/delWbCategoryById/{id}")
    public RS delWbCategoryById(
            @ApiParam(name = "id", value = "网站分类ID")
            @PathVariable String id
    ) {

        int delCount = wbCategoryService.delWbCategoryById(id);
        return RS.success().message("删除成功").data("delCount",delCount);
    }

    @ApiOperation(value = "通过名称删除分类")
    @DeleteMapping("/delWbCategoryByName/{name}")
    public RS delWbCategoryByName(
            @ApiParam(name = "name", value = "网站分类ID")
            @PathVariable String name
    ) {

        int delCount = wbCategoryService.delWbCategoryByName(name);
        return RS.success().message("删除成功").data("delCount",delCount);
    }

    @ApiOperation(value = "通过excel批量添加网站")
    @PostMapping("batchDelWbCategory")
    public RS batchDelWbCategory(MultipartFile file) {

        int delCount = wbCategoryService.batchDelWbCategory(file);
        return RS.success().message("删除成功").data("delCount", delCount);
    }

}

