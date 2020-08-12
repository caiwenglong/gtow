package com.yby.uCenter.service;

import com.yby.commonUtils.RS;
import com.yby.uCenter.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yby.uCenter.entity.vo.LoginVo;
import com.yby.uCenter.entity.vo.RegisterVo;
import com.yby.uCenter.entity.vo.UserInfoVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-08-07
 */
public interface UserService extends IService<User> {

    // 登录方法
    RS login(LoginVo user);

    // 注册方法
    void register(RegisterVo registerVo);

    // 获取用户信息
    UserInfoVo getUserInfo(HttpServletRequest token);

}
