package com.didong.service;

import com.didong.serviceEntity.UserInfo;

import java.util.List;

public interface UserInfoService {

    List<UserInfo> selectUserByNickNameLike(String nickName);
}
