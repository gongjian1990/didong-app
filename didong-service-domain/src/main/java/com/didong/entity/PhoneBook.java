package com.didong.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("phone_book")
public class PhoneBook {

    private Integer id;

    private String userId;

    private String optCode;

    private String optValue;

    private Integer delFlag;

    private Date createTime;

    private Date lastUpdateTime;
}
