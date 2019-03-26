package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didong.entity.UserInfo;


/**
 * User 表数据库控制层接口
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectUserInfoByPhoneAndLoginType(String phone,String loginType);

}