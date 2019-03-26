package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.UserInfo;
import com.didong.mapper.UserInfoMapper;
import com.didong.redis.RedisUtil;
import com.didong.service.LoginService;
import com.didong.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.UUID;

@Service("loginService")
@Slf4j
public class LoginServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements LoginService {

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

    /**
     * 获取短信验证码
     * @param map
     * @return
     */
    @Override
    public JSONObject getSmsCode(Map<String,String> map) {
        JSONObject jsonObject=new JSONObject();
        /*try {
            jsonObject=MobileMessageSend.sendMsg(userPhone);
        } catch (IOException e) {
            log.error("获取短信验证码异常,异常类型e:{}",e);
        }*/
        jsonObject.put("smsCode","1234");
        jsonObject.put("code","200");
        String userId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
        jsonObject.put("userId",userId);
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPhone(map.get("userPhone"));
        userInfo.setUdid(map.get("udid"));
        RedisUtil.set("smsCode:"+userId,jsonObject.getString("smsCode"),60*100);
//        UserInfo userInfo1=baseMapper.selectById(1);
        baseMapper.insert(userInfo);
        log.info("获取短信验证码,userInfo:{}",userInfo.toString());
        return jsonObject;
    }

    /**
     * 校验短信验证码
     * @param map
     * @return
     */
    @Override
    public String checkSmsCode(Map<String, String> map) {
        String userId=map.get("userId");
        String smsCode=map.get("smsCode");
        log.info("smsCode:{}","smsCode:"+userId);
        if(!smsCode.equals(RedisUtil.get("smsCode:"+userId))){
            return "false";
        }
        return "success";
    }
}
