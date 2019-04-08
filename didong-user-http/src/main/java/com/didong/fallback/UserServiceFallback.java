package com.didong.fallback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.httpEntity.TbPhoneBook;
import com.didong.httpEntity.TbUserInfo;
import com.didong.httpEntity.TbUserInterest;
import com.didong.service.UserService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

import java.util.List;
import java.util.Map;

@Component
public class UserServiceFallback implements UserService {
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
    public String wbLogin(TbUserInfo userInfo) {
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
    public ResultData updateUserData(TbUserInfo userInfo) {
        return new ResultData(500, "更新个人资料失败", null);

    }
    @Override
    public ResultData savePhoneBook(List<TbPhoneBook> list) {
        return new ResultData(500, "上传用户目录失败", null);
    }

    @Override
    public ResultData saveUserInterest(List<TbUserInterest> list) {
        return new ResultData(500, "保存用户兴趣失败", null);
    }

    @Override
    public ResultData getDictionaryList(String optGroup) {
        return new ResultData(500, "获取兴趣失败", null);
    }

}
