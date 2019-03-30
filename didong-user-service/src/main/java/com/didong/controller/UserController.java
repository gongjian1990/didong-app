package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.didong.service.UserInfoService;
import com.didong.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/userController")
@Slf4j
public class UserController {

    @Autowired
    public UserInfoService userinfoService;


    @RequestMapping("/updateUserData")
    public ResultData updateUserData(@RequestBody UserInfo userInfo) throws UnsupportedEncodingException, ClientException {
        return userinfoService.updateUserData(userInfo);
    }
}
