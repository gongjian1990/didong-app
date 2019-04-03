package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

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
        return userService.getWXAccessToken(map);
    }

    /**
     * qq登录
     * @param request
     * @param access_token
     * @param openid
     * @return
     */
    @RequestMapping("/qqLogin")
    public String qqLogin(HttpServletRequest request, String access_token, String openid){
        Map<String,String> map=new HashMap<String,String>();

        map.put("access_token",access_token);
        map.put("openId",openid);
        map.put("ip",request.getHeader("x-forwarded-for"));
        String result=userService.qqLogin(map);
        if(!result.equals("success")){
            return JSON.toJSONString(Response.error(new ResultData(500,"调用qq登录失败","")));
        }
        return JSON.toJSONString(Response.success(new ResultData(200,"调用成功",result)));

    }

    /**
     * 微博登录
     * @param userInfo
     * @return
     */
    @RequestMapping("/wbLogin")
    public String wbLogin(UserInfo userInfo, HttpServletRequest request){
        userInfo.setLastOnlineIp(request.getHeader("x-forwarded-for"));
        String result=userService.wbLogin(userInfo);
        if(!result.equals("success")){
            //"登录失败,请重新登录",result
            return JSON.toJSONString(Response.error(new ResultData(500,"登录失败,请重新登录",result)));
        }
        return JSON.toJSONString(Response.success(new ResultData(200,"登录成功",result)));
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
        map.put("loginType","sms");
        JSONObject jsonObject = new JSONObject();
        jsonObject = userService.getSmsCode(map);
        log.info("[获取短信验证码] -- jsonObject:{}", jsonObject);
        if(jsonObject.size()==0){
            return JSON.toJSONString(Response.error(new ResultData(500,"用户已存在","")));
        }
        if (!jsonObject.getString("code").equals("200")){
            return JSON.toJSONString(Response.error(new ResultData(500,"获取短信验证码失败","")));
        }
        return JSON.toJSONString(Response.success(new ResultData(200,"获取短信验证码成功",jsonObject)));
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
        String result=userService.checkSmsCode(map);
        if(result.equals("false")){
            return JSON.toJSONString(Response.error(new ResultData(500,"验证码验证失败",null)));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("result",result);
        return JSON.toJSONString(Response.success(new ResultData(200,"验证成功",jsonObject)));

    }


    /**
     * 校验 微信 Access_token ,并返回用户信息
     * @param request
     * @param access_token
     * @param openid
     * @return
     */
    @RequestMapping("/checkWXAccessToken")
    public Response checkWXAccessToken(HttpServletRequest request, String access_token, String openid){

        Map map = new HashMap();

        map.put("access_token",access_token);
        map.put("openid",openid);
        map.put("ip",request.getRemoteHost());

        return userService.checkWXAccessToken(map);
    }

}
