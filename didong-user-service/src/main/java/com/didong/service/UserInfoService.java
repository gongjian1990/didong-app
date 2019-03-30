package com.didong.service;


import com.aliyuncs.exceptions.ClientException;
import com.didong.entity.UserInfo;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;

public interface UserInfoService {

    ResultData updateUserData(UserInfo userInfo) throws UnsupportedEncodingException, ClientException;
}
