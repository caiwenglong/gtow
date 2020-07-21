package com.yby.uAdmin.controller;


import com.yby.commonUtils.RS;
import com.yby.uAdmin.service.TbWebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/uAdmin/tb-website")
@CrossOrigin
public class TbWebsiteController {

    @Autowired
    TbWebsiteService tbWebsiteService;

    @PostMapping("batchAddWebsite")
    public RS batchAddWebsite(MultipartFile file) throws IOException {

        tbWebsiteService.batchAddWebsite(file, tbWebsiteService);
        return RS.success();
    }

}

