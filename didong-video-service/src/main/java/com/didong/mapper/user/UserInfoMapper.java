package com.didong.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didong.serviceEntity.UserInfo;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> selectUserByNickNameLike(String nickName);

}