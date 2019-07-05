package com.homura.community.controller;

import com.homura.common.constant.ReturnResult;
import com.homura.community.feign.AccountServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private AccountServiceFeign accountServiceFeign;

    @RequestMapping(value = "/getUser",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ReturnResult testGetUser(){
        ReturnResult returnResult = accountServiceFeign.findAll();
        return returnResult;
    }
}
