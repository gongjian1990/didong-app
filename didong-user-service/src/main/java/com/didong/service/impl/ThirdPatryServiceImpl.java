package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.UserInfo;
import com.didong.mapper.UserInfoMapper;
import com.didong.service.ThirdPatryService;
import com.didong.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("thirdPatryService")
public class ThirdPatryServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements ThirdPatryService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public JSONObject getWXAccessToken(Map map) {

        String appid = (String) map.get("appid");
        String secret = (String) map.get("secret");
        String code = (String) map.get("code");
        String grant_type = (String) map.get("grant_type");
        String udid = (String) map.get("udid");
        String phone = (String) map.get("phone");

        System.out.println("appid:"+appid+",secret:"+secret+",code:"+code+",grant_type:"+grant_type);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type="+grant_type+"";

        JSONObject result = HttpClientUtils.httpGet(url);

        phone = "15152212330";

        UserInfo userInfo = userInfoMapper.selectUserInfoByPhoneAndLoginType(phone, "wx");

        System.out.println("userInfo:"+userInfo);

        System.out.println("result--------------:"+result);

        return result;
    }
}
