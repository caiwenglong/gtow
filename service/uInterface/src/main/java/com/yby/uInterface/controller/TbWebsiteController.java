package com.yby.uInterface.controller;


import com.yby.uInterface.entity.TbWebsite;
import com.yby.uInterface.service.TbWebsiteService;
import com.yby.uInterface.service.impl.TbWebsiteServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
@Api(description="网站展示")
@CrossOrigin
@RestController
@RequestMapping("/uInterface/tb-website")
public class TbWebsiteController {
    @Autowired
    private TbWebsiteService tbWebsiteService;

    @ApiOperation(value = "返回请求的数据")
    @GetMapping("/find")
    public ArrayList<TbWebsite> getTbWebsiteByKeyword() throws IOException {


        return tbWebsiteService.getTbWebsite("hello world", 1, 6);
    }

}

