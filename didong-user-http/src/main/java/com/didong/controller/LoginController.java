package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.service.UserService;
import com.didong.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/getSmsCode")
    public String getSmsCode(@NotBlank(message = "手机号不能为空") @RequestParam("userPhone") String userPhone,
                             @NotBlank(message = "设备号不能为空") @RequestParam("udid") String udid) {
        log.info("[获取短信验证码] -- userPhone:{}，udid:{}", userPhone,udid);
        Map<String,String> map=new HashMap<String, String>();
        map.put("userPhone",userPhone);
        map.put("udid",udid);
        JSONObject jsonObject = new JSONObject();
        jsonObject = userService.postRestTemplate("loginController/getSmsCode", map, JSONObject.class);

//        if (!jsonObject.getString("code").equals(Response.successCode.toString())){
//            return JSON.toJSONString(new Response(500,"获取短信验证码异常",jsonObject));
//        }
        return JSON.toJSONString(new Response().success(jsonObject));
    }

    @RequestMapping("/checkSmsCode")
    public String checkSmsCode(@NotBlank(message = "用户ID") @RequestParam("userId") String userId,
                               @NotBlank(message = "短信验证码") @RequestParam("smsCode") String smsCode)  {

        Map<String,String> map=new HashMap<String, String>();
        map.put("userId",userId);
        map.put("smsCode",smsCode);
        String result=userService.postRestTemplate("loginController/checkSmsCode", map, String.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("result",result);
        return JSON.toJSONString(new Response().success(jsonObject));

    }


    /**
     * 校验 微信 Access_token ,并返回用户信息
     * @param request
     * @param access_token
     * @param openid
     * @return
     */
    @RequestMapping("/checkWXAccessToken")
    public String checkWXAccessToken(HttpServletRequest request, String access_token, String openid){

        Map map = new HashMap();

        map.put("access_token",access_token);
        map.put("openid",openid);
        map.put("ip",request.getRemoteHost());

        return userService.postRestTemplate("loginController/checkWXAccessToken",map,String.class);
    }

}
