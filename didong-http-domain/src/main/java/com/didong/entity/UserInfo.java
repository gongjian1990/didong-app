package com.didong.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

    private Integer id;

    private String userId;

    private Integer age;

    private String nickName;

    private String avatar;

    private Date birthday;

    private String liveAddress;

    private String constellation;

    private String loginType;

    private String userPhone;

    private String email;

    private String school;

    private String udid;

    private String accessToken;

    private String level;

    private Integer delFlag;

    private Date create_time;

    private Date lastUpdateTime;

}