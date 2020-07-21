package com.yby.uInterface.controller;


import com.yby.commonUtils.RS;
import com.yby.uInterface.entity.TbWebsite;
import com.yby.uInterface.service.TbWebsiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-03
 */
@Api(tags="通过关键字获取数据")
@CrossOrigin
@RestController
@RequestMapping("/uInterface/tb-website")
public class TbWebsiteController {
    @Autowired
    private TbWebsiteService tbWebsiteService;
    private Object website;

    @ApiOperation(value = "通过关键字请求返回的数据")
    @GetMapping("/find/{keyword}/{pageNo}/{pageSize}")
    public RS getTbWebsiteByKeyword(
            @ApiParam(name = "keyword", value = "要搜索的关键字", required = true)
            @PathVariable String keyword, /*关键字*/
            @PathVariable Integer pageNo,  // 开始查询第几页
            @PathVariable Integer pageSize // 一页显示几条数据
    ) throws IOException {

        ArrayList<TbWebsite> websiteArrayList = tbWebsiteService.getWebsiteSourceMapList(keyword, pageNo, pageSize);
        return RS.success().data("websiteArrayList", websiteArrayList);
    }

}

