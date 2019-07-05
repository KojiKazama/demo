package com.homura.account.service.impl;

import com.homura.account.dao.UserDao;
import com.homura.account.service.UserService;
import com.homura.common.bean.User;
import com.homura.common.util.CryptoUtils;
import com.homura.common.constant.ReturnResult;
import com.homura.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        if (loginByName(user) != null) {
            return loginByName(user);
        }
        if (loginByEmail(user) != null) {
            return loginByEmail(user);
        }
        if (loginByPhone(user) != null) {
            return loginByPhone(user);
        }
        return null;
    }

    @Override
    public User loginByName(User user) {
        String password = user.getPassword();
        user = userDao.findUserByName(user.getName());
        if (user == null) {
            return null;
        }
        if (password.equals(CryptoUtils.aesDecrypt(user.getPassword()))) {
            return user;
        }
        return null;
    }

    @Override
    public User loginByEmail(User user) {
        String password = user.getPassword();
        user = userDao.findUserByEmail(user.getEmail());
        if (user == null) {
            return null;
        }
        if (password.equals(CryptoUtils.aesDecrypt(user.getPassword()))) {
            return user;
        }
        return null;
    }

    @Override
    public User loginByPhone(User user) {
        String password = user.getPassword();
        user = userDao.findUserByPhone(user.getPhone());
        if (user == null) {
            return null;
        }
        if (password.equals(CryptoUtils.aesDecrypt(user.getPassword()))) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    public ReturnResult registerUser(User user) {
        ReturnResult returnResult = new ReturnResult();
        if (StringUtils.isNull(user.getName())) {
            returnResult.setFlag(false);
            return returnResult;
        }
        if (StringUtils.isNull(user.getEmail())) {
            returnResult.setFlag(false);
            return returnResult;
        }
        if (StringUtils.isNull(user.getPassword())) {
            returnResult.setFlag(false);
            return returnResult;
        }
        user.setPassword(CryptoUtils.aesEncrypt(user.getPassword()));
        userDao.saveUser(user);
        return returnResult;
    }

    @Override
    public void updateUser(User user) {
        user.setUpdateTime(new Date());
        userDao.updateUser(user);
    }
}
