package com.yby.uCenter.controller;


import com.yby.commonUtils.JwtUtils;
import com.yby.commonUtils.RS;
import com.yby.uCenter.entity.vo.LoginVo;
import com.yby.uCenter.entity.vo.RegisterVo;
import com.yby.uCenter.entity.vo.UserInfoVo;
import com.yby.uCenter.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/uCenter/")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public RS login(
            @ApiParam(name = "username", value = "登录的用户名密码")
                    @RequestBody LoginVo user) {
        return userService.login(user);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public RS registerUser(
            @ApiParam(name = "registerVo", value = "注册的用户对")
            @RequestBody RegisterVo registerVo
            ) {
        userService.register(registerVo);
        return RS.success();
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public RS getInfo(
            @ApiParam(name = "request", value = "token")
            HttpServletRequest request
    ) {
        UserInfoVo userInfo = userService.getUserInfo(request);
        return RS.success().data("result", userInfo);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public RS logout() {
        return userService.logout();
    }
}

