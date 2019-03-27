package com.didong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.UserInfo;
import com.didong.mapper.UserInfoMapper;
import com.didong.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public String updateUserData(UserInfo userInfo) {
        int i=baseMapper.update(userInfo,new QueryWrapper<UserInfo>().eq("user_id",userInfo.getUserId()));
        if(i>0){
            return "success";
        }
        return "false";
    }
}