package com.didong.controller;

import com.didong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     *  获取 微信 AccessToken
     * @param appid
     * @param secret
     * @param code
     * @param grant_type
     * @param phone
     * @param udid
     * @return
     */
    @RequestMapping("/getWXAccessToken")
    public String getAccessToken(String appid,String secret,String code,String grant_type,String phone,String udid){
        Map map = new HashMap();
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("code",code);
        map.put("grant_type",grant_type);
        map.put("phone",phone);
        map.put("udid",udid);
        return userService.postRestTemplate("thirdPatryController/getWXAccessToken",map,String.class);
    }
}
