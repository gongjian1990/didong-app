package com.didong.fallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import pojo.Response;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public Response hello(String s) {

        System.out.println(s);

        return Response.error("error",null);
    }

    @Override
    public Response checkWXAccessToken(Map map) {
        return Response.error("error",null);
    }

    @Override
    public String checkSmsCode(Map map) {
        return JSON.toJSONString(Response.error("调用短信验证失败",""));
    }

    @Override
    public JSONObject getSmsCode(Map map) {
        return JSONObject.parseObject(JSON.toJSONString(Response.error("调用短信验证失败","")));
    }

    @Override
    public String wbLogin(UserInfo userInfo) {
        return JSON.toJSONString(Response.error("调用微博登录失败",""));
    }

    @Override
    public String qqLogin(UserInfo userInfo) {
        return JSON.toJSONString(Response.error("调用qq登录失败",""));
    }

    @Override
    public String getWXAccessToken(Map map) {
        return JSON.toJSONString(Response.error("获取微信AccessToken失败",""));
    }
}
