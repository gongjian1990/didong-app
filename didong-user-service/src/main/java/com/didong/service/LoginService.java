package com.didong.service;

import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;

import java.util.Map;

public interface LoginService {

    JSONObject getWXAccessToken(Map map);

    JSONObject getSmsCode(Map<String,String> map);

    String checkSmsCode(Map<String,String> map);

    String qqLogin(UserInfo userInfo);

}
