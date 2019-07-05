package com.homura.common.bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private int sex;
    private String headPhoto;
    private String remark;
    private Date birthday;
    private Date createTime;
    private Date updateTime;
}
