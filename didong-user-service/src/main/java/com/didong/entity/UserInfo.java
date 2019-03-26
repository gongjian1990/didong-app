package com.didong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_info")
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

    private String userLevel;

    private Integer delFlag;

    private Date createTime;

    private Date lastUpdateTime;

    private Date lastOnlineTime;

    private String lastOnlineIp;

    private Integer userStatus;

    private Integer gender;

}