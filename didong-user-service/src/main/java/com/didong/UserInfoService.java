package com.didong;


import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;

import java.io.UnsupportedEncodingException;

public interface UserInfoService {

    JSONObject updateUserData(UserInfo userInfo) throws UnsupportedEncodingException;
}
