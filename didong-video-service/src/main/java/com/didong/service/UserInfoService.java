package com.didong.service;

import com.didong.entity.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> selectUserByNickNameLike(String nickName);
}
