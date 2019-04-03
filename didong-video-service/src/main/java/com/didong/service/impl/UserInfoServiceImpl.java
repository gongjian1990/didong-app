package com.didong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.user.UserInfoMapper;
import com.didong.service.UserInfoService;
import com.didong.serviceEntity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public List<UserInfo> selectUserByNickNameLike(String nickName) {

        return baseMapper.selectUserByNickNameLike(nickName);
    }
}
