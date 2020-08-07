package com.yby.uCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yby.commonUtils.JwtUtils;
import com.yby.commonUtils.MD5;
import com.yby.service.base.exception.CustomException;
import com.yby.uCenter.entity.User;
import com.yby.uCenter.entity.UserInfo;
import com.yby.uCenter.mapper.UserMapper;
import com.yby.uCenter.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public UserInfo login(User user) {
        String mobile = user.getMobile();
        String password = user.getPassword();

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new CustomException("OW20002", "登录失败！手机号或者密码不能为空");
        }

        // 通过账号查询用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mobile", mobile);
        User hitUser = baseMapper.selectOne(userQueryWrapper);

        if(hitUser == null || hitUser.getIsDelete()) {
            throw new CustomException("OW20005", "登录失败！该账号不存在");
        }

        System.out.println(MD5.encrypt(password));

        if(!MD5.encrypt(password).equals(hitUser.getPassword())) {
            throw new CustomException("OW20003", "登录失败！密码错误");
        }

        if(hitUser.getIsDisabled()) {
            throw new CustomException("OW20004", "登录失败！该账号已被禁用");
        }

        // 成功之后
        String jwtToken = JwtUtils.getJwtToken(hitUser.getId(), hitUser.getNickname());

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(hitUser, userInfo);
        System.out.println(hitUser);
        System.out.println(userInfo);
        userInfo.setJwtToken(jwtToken);
        return userInfo;
    }
}
