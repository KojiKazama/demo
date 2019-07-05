package com.homura.account.controller;

import com.homura.account.service.UserService;
import com.homura.common.bean.User;
import com.homura.common.constant.AccountConstant;
import com.homura.common.constant.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Value("${user.type}")
    private String userType;

    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ReturnResult login(HttpServletRequest request, User user){
        ReturnResult returnResult = new ReturnResult();
        try {
            user = userService.login(user);
            if (user.getId() > 0) {
                returnResult.setFlag(true);
                returnResult.setResult(user);
                request.getSession().setAttribute(AccountConstant.CURRENT_USER, user);
            } else {
                returnResult.setFlag(false);
            }
        }catch (Exception e) {
            returnResult.setFlag(false);
        }
        return returnResult;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ReturnResult register(HttpServletRequest request, User user){
        ReturnResult returnResult = new ReturnResult();
        try {
            returnResult = userService.registerUser(user);
        }catch (Exception e) {
            returnResult.setFlag(false);
        }
        return returnResult;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ReturnResult update(HttpServletRequest request, User user){
        ReturnResult returnResult = new ReturnResult();
        try {
            userService.updateUser(user);
            returnResult.setFlag(true);
        }catch (Exception e) {
            returnResult.setFlag(false);
        }
        return returnResult;
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ReturnResult findAll(HttpServletRequest request){
        ReturnResult returnResult = new ReturnResult();
        try {
            returnResult.setResult(userService.findAllUser());
            returnResult.setFlag(true);
        }catch (Exception e) {
            returnResult.setFlag(false);
        }
        return returnResult;
    }

    @RequestMapping(value = "/getConfig",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ReturnResult testGetConfig(HttpServletRequest request){
        ReturnResult returnResult = new ReturnResult();
        try {
            returnResult.setResult(userType);
            returnResult.setFlag(true);
        }catch (Exception e) {
            returnResult.setFlag(false);
        }
        return returnResult;
    }
}
