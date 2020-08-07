package com.yby.uCenter.controller;


import com.yby.commonUtils.RS;
import com.yby.uCenter.entity.User;
import com.yby.uCenter.entity.UserInfo;
import com.yby.uCenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caiwenlong
 * @since 2020-08-07
 */
@Api(value = "用户中心")
@RestController
@RequestMapping("/uCenter/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @GetMapping("/login")
    public RS login(
            @ApiParam(name = "user", value = "登录的用户名密码")
            User user) {
        UserInfo userInfo = userService.login(user);
        return RS.success().data("userInfo", userInfo);
    }
}

