package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.didong.UserInfoService;
import com.didong.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/userController")
@Slf4j
public class UserController {

    @Autowired
    public UserInfoService userinfoService;


    @RequestMapping("/updateUserData")
    public JSONObject updateUserData(@RequestBody UserInfo userInfo) throws UnsupportedEncodingException {
        return userinfoService.updateUserData(userInfo);
    }
}
