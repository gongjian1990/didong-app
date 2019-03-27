package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.UserInfo;
import com.didong.mapper.UserInfoMapper;
import com.didong.redis.RedisUtil;
import com.didong.service.LoginService;
import com.didong.util.Response;
import com.didong.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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
        RedisUtil.set("smsCode:"+userId,jsonObject.getString("smsCode"),60);
//        UserInfo userInfo1=baseMapper.selectById(1);
        baseMapper.insert(userInfo);
        log.info("获取短信验证码,userInfo:{}",userInfo.toString());
        return jsonObject;
    }

    @Override
    public String checkSmsCode(Map<String, String> map) {
        String userId=map.get("userId");
        String smsCode=map.get("smsCode");
        if(!userId.equals(RedisUtil.get("userId"))){
            return "false";
        }
        if(!smsCode.equals(RedisUtil.get("smsCode"))){
            return "false";
        }
        return "success";
    }

    @Override
    public Response checkWXAccessToken(Map map) {
        try {
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            String ip = (String) map.get("ip");

            JSONObject result = HttpClientUtils.httpGet("https://api.weixin.qq.com/sns/auth?access_token="+access_token+"&openid="+openid);
            System.out.println("result1---"+result);

            if(result!= null){
                if("0".equals(result.get("errcode"))){
                    result = HttpClientUtils.httpGet("https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid);

                    System.out.println("result2---"+result);
                    if(result!= null){
                        // 正确返回
                        if(null == result.get("errcode")){
                            //微信用户统一标识
                            String unionid = (String) result.get("unionid");
                            UserInfo userInfo = baseMapper.selectOne(new QueryWrapper<UserInfo>().eq("unionid", unionid));
                            if(userInfo!= null){
                                userInfo.setLastOnlineTime(new Date());
                                userInfo.setLastOnlineIp(ip);
                                baseMapper.updateById(userInfo);
                            }else {
                                userInfo = new UserInfo();
                                userInfo.setUdid(UUID.randomUUID().toString());
                                userInfo.setLoginType("wx");
                                userInfo.setUnionid(unionid);
                                userInfo.setLastOnlineTime(new Date());
                                userInfo.setLastOnlineIp(ip);
                                baseMapper.insert(userInfo);
                            }
                            return Response.success(userInfo);
                        }else {
                            return Response.error("获取用户个人信息失败","");
                        }
                    }
                }else if("40003".equals(result.get("errcode"))){
                    return Response.error("检验授权凭证失败","");
                }else {
                    return Response.error("处理异常","");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error("服务器异常","");
        }
        return null;
    }
}
