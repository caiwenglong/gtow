package com.yby.uCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yby.commonUtils.JwtUtils;
import com.yby.commonUtils.MD5;
import com.yby.commonUtils.RS;
import com.yby.service.base.exception.CustomException;
import com.yby.uCenter.entity.User;
import com.yby.uCenter.entity.vo.LoginVo;
import com.yby.uCenter.entity.vo.RegisterVo;
import com.yby.uCenter.entity.vo.UserInfoVo;
import com.yby.uCenter.mapper.UserMapper;
import com.yby.uCenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-08-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    // 登录功能
    @Override
    public RS login(LoginVo user) {
        String mobile = user.getUsername();
        String password = user.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return loginError("OW20002", "登录失败！手机号或者密码不能为空");
        }

        // 通过账号查询用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mobile", mobile);
        User hitUser = baseMapper.selectOne(userQueryWrapper);

        if(hitUser == null || hitUser.getIsDelete()) {
            return loginError("OW20005", "登录失败！该账号不存在");
        }

        if(!MD5.encrypt(password).equals(hitUser.getPassword())) {
            return loginError("OW20003", "登录失败！密码错误");
        }

        if(hitUser.getIsDisabled()) {
            return loginError("OW20004", "登录失败！该账号已被禁用");
        }

        // 成功之后
        return loginSuccess(hitUser);
    }

    public RS loginError(String code, String message) {
        return RS.error().code(code).message(message);
    }

    public RS loginSuccess(User hitUser) {
        String jwtToken = JwtUtils.getJwtToken(hitUser.getId(), hitUser.getNickname());

        return RS.success().data("token", jwtToken);
    }

    // 注册功能
    @Override
    public void register(RegisterVo registerVo) {

        // 获取用户信息
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        String phoneCode = registerVo.getPhoneCode();

        // 非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(phoneCode)) {
            throw new CustomException("OW20002", "注册失败！手机号/密码/验证码不能为空！");
        }

    }

    @Override
    public UserInfoVo getUserInfo(HttpServletRequest request) {

        String userId = JwtUtils.getUserIdByJwtToken(request);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        User user = baseMapper.selectOne(queryWrapper);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        return userInfoVo;
    }

    @Override
    public RS logout() {
        return RS.success().data("token", null);
    }
}
