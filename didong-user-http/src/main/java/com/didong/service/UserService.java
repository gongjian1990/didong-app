package com.didong.service;

import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.fallback.UserServiceFallback;
import pojo.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.ResultData;

import java.util.Map;

@FeignClient(value = "user-service", fallback = UserServiceFallback.class)
public interface UserService {

    @RequestMapping(method = RequestMethod.POST,value = "testController/test")
    Response hello(String s);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/checkWXAccessToken")
    Response checkWXAccessToken(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/checkSmsCode")
    String checkSmsCode(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/getSmsCode")
    JSONObject getSmsCode(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/wbLogin")
    String wbLogin(UserInfo userInfo);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/qqLogin")
    String qqLogin(Map<String,String> map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/getWXAccessToken")
    String getWXAccessToken(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "userController/updateUserData")
    ResultData updateUserData(UserInfo userInfo);
}
