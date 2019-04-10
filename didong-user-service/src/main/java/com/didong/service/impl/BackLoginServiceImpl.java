package com.didong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbBackLoginMapper;
import com.didong.service.BackLoginService;
import com.didong.serviceEntity.TbBackUser;
import org.springframework.stereotype.Service;

@Service
public class BackLoginServiceImpl extends ServiceImpl<TbBackLoginMapper, TbBackUser> implements BackLoginService {

    @Override
    public TbBackUser backLogin(String username, String password) {

        TbBackUser backUser = baseMapper.selectOne(new QueryWrapper<TbBackUser>()
                .eq("username", username)
                .eq("password",password));
        return backUser;
    }
}
