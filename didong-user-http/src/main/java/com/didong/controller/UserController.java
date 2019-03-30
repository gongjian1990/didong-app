package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

 @RequestMapping("/hello")
 public Response hello(){
    return userService.hello("hello");
 }

    /**
     * 新用户个人资料完善
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUserData")
    public String updateUserData(UserInfo userInfo){
        log.info("[新用户个人资料完善] -- userInfo:{}", userInfo);
        ResultData result = userService.updateUserData(userInfo);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));

    }


}
