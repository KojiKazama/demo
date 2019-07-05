package com.homura.account.dao;

import com.homura.common.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserDao {
    /**
     * 根据昵称获取用户
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     * 根据邮箱获取用户
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 根据手机号码获取用户
     * @param phone
     * @return
     */
    User findUserByPhone(String phone);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUser();

    /**
     * 保存用户
     * @param user
     */
    void saveUser(User user);

    /**
     * 修改用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改密码
     * @param user
     */
    void updatePassword(User user);

    /**
     * 修改邮箱
     * @param user
     */
    void updateEmail(User user);

    /**
     * 修改电话号码
     * @param user
     */
    void updatePhone(User user);
}
