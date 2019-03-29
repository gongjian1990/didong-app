package com.didong;

import com.alibaba.fastjson.JSONObject;
import pojo.Response;
import com.didong.entity.UserInfo;

import java.util.Map;

public interface LoginService {

    JSONObject getWXAccessToken(Map map);

    JSONObject getSmsCode(Map<String,String> map);

    String checkSmsCode(Map<String,String> map);

    Response checkWXAccessToken(Map map);

    String qqLogin(UserInfo userInfo);

    String wbLogin(UserInfo userInfo);
}
