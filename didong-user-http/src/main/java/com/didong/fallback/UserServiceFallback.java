package com.didong.fallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import pojo.Response;
import org.springframework.stereotype.Component;
import pojo.ResultData;

import java.util.Map;

@Component
public class UserServiceFallback implements UserService {
    @Override
    public Response hello(String s) {

        return null;
    }

    @Override
    public Response checkWXAccessToken(Map map) {
        return null;
    }

    @Override
    public String checkSmsCode(Map map) {
        return JSON.toJSONString(Response.error(new ResultData(500, "校验短信验证码失败", null)));
    }

    @Override
    public JSONObject getSmsCode(Map map) {
        return null;
    }

    @Override
    public String wbLogin(UserInfo userInfo) {
        return JSON.toJSONString(Response.error(new ResultData(500, "调用微博登陆失败", null)));
    }

    @Override
    public String qqLogin(Map<String, String> map) {
        return JSON.toJSONString(Response.error(new ResultData(500, "调用qq登陆失败", null)));
    }

    @Override
    public String getWXAccessToken(Map map) {
        return JSON.toJSONString(Response.error(new ResultData(500, "调用微信登陆失败", null)));
    }

    @Override
    public ResultData updateUserData(UserInfo userInfo) {
        return new ResultData(500, "更新个人资料失败", null);

    }
}
