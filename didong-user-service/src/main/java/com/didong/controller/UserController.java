package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.didong.service.UserInfoService;
import com.didong.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pojo.Response;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


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

    @RequestMapping("/test")
    public Response test(){
        return Response.success(new ResultData(200,"ss",null));
    }

}
