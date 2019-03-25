package com.didong.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserInterest {

    private Integer id;

    private String userId;

    private String optCode;

    private String optVale;

    private Integer delFlag;

    private Date createTime;

    private Date lastUpdateTime;
}
