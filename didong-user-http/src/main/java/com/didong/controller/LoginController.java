package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.service.UserService;
import com.didong.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
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
        return userService.postRestTemplate("loginController/getWXAccessToken",map,String.class);
    }

    /**
     * 获取短信验证码
     * @param userPhone
     * @param udid
     * @return
     */
    @RequestMapping("/getSmsCode")
    public String getSmsCode(@NotBlank(message = "手机号不能为空") @RequestParam("userPhone") String userPhone,
                             @NotBlank(message = "设备号不能为空") @RequestParam("udid") String udid) {
        log.info("[获取短信验证码] -- userPhone:{}，udid:{}", userPhone,udid);
        Map<String,String> map=new HashMap<String, String>();
        map.put("userPhone",userPhone);
        map.put("udid",udid);
        map.put("loginType","sms_login");
        JSONObject jsonObject = new JSONObject();
        jsonObject = userService.postRestTemplate("loginController/getSmsCode", map, JSONObject.class);
        log.info("[获取短信验证码] -- jsonObject:{}", jsonObject);
        if(jsonObject.size()==0){
            return JSON.toJSONString(new Response().error("用户已存在",""));
        }
        if (!jsonObject.getString("code").equals(Response.successCode.toString())){
            return JSON.toJSONString(new Response().error("获取短信验证码失败",jsonObject));
        }
        return JSON.toJSONString(new Response().success(jsonObject));
    }

    /**
     * 验证短信验证码
     * @param userId
     * @param smsCode
     * @return
     */
    @RequestMapping("/checkSmsCode")
    public String checkSmsCode(@NotBlank(message = "用户ID") @RequestParam("userId") String userId,
                               @NotBlank(message = "短信验证码") @RequestParam("smsCode") String smsCode)  {

        log.info("[验证短信验证码] -- userId:{}，smsCode:{}", userId,smsCode);
        Map<String,String> map=new HashMap<String, String>();
        map.put("userId",userId);
        map.put("smsCode",smsCode);
        String result=userService.postRestTemplate("loginController/checkSmsCode", map, String.class);
        if(result.equals("false")){
            return JSON.toJSONString(new Response().error("验证码验证失败",result));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",Response.successCode);
        jsonObject.put("result",result);
        return JSON.toJSONString(new Response().success(jsonObject));

    }

}
