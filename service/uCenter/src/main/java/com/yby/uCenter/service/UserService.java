package com.yby.uCenter.service;

import com.yby.uCenter.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yby.uCenter.entity.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caiwenlong
 * @since 2020-08-07
 */
public interface UserService extends IService<User> {

    public UserInfo login(User user);

}
