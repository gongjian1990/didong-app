package com.didong.service;

import com.alibaba.fastjson.JSONObject;
import com.didong.util.Response;
import com.didong.entity.UserInfo;

import java.util.Map;

public interface LoginService {

    JSONObject getWXAccessToken(Map map);

    JSONObject getSmsCode(Map<String,String> map);

    String checkSmsCode(Map<String,String> map);

    Response checkWXAccessToken(Map map);

    String qqLogin(Map<String,String> map);

    String wbLogin(Map<String,String> map);
}
