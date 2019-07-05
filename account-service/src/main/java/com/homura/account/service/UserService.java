package com.homura.account.service;

import com.homura.common.bean.User;
import com.homura.common.constant.ReturnResult;

import java.util.List;

public interface UserService {
    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 用户通过昵称登录
     * @param user
     * @return
     */
    User loginByName(User user);

    /**
     * 用户通过邮箱登录
     * @param user
     * @return
     */
    User loginByEmail(User user);

    /**
     * 用户通过手机号码登录
     * @param user
     * @return
     */
    User loginByPhone(User user);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUser();

    /**
     * 注册用户
     * @param user
     */
    ReturnResult registerUser(User user);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(User user);
}
