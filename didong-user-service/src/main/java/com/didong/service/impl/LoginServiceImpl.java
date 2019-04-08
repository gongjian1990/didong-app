package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbUserInfoMapper;
import com.didong.redis.RedisUtil;
import com.didong.service.LoginService;
import com.didong.serviceEntity.TbUserInfo;
import com.didong.util.IdGeneratorUtil;
import com.didong.util.MobileMessageSend;
import com.didong.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pojo.Response;
import pojo.ResultData;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service("loginService")
@Slf4j
public class LoginServiceImpl extends ServiceImpl<TbUserInfoMapper, TbUserInfo> implements LoginService {

    /**
     * 获取短信验证码
     *
     * @param map
     * @return
     */
    @Override
    public JSONObject getSmsCode(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        TbUserInfo user = baseMapper.selectOne(new QueryWrapper<TbUserInfo>()
                .eq("user_phone", map.get("userPhone"))
                .eq("login_type", map.get("loginType")));
        if (user != null) {
            return jsonObject;
        }
        try {
            jsonObject = MobileMessageSend.sendMsg(map.get("userPhone"));
        } catch (IOException e) {
            log.error("获取短信验证码异常,异常类型e:{}", e);
        }
//        jsonObject.put("smsCode","1234");
//        jsonObject.put("code","200");
        Long userId= IdGeneratorUtil.generateId();
//        String userId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
        jsonObject.put("userId", userId);
        TbUserInfo userInfo = new TbUserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPhone(map.get("userPhone"));
        userInfo.setUdid(map.get("udid"));
        userInfo.setLoginType("sms");
        userInfo.setCreateTime(new Date());
        userInfo.setLastUpdateTime(new Date());
        RedisUtil.set("smsCode:" + userId, jsonObject.getString("smsCode"), 60 * 100);
//        UserInfo userInfo1=baseMapper.selectById(1);
        baseMapper.insert(userInfo);
        log.info("获取短信验证码,userInfo:{}", userInfo.toString());
        return jsonObject;
    }

    /**
     * 校验短信验证码
     *
     * @param map
     * @return
     */
    @Override
    public String checkSmsCode(Map<String, String> map) {
        String userId = map.get("userId");
        String smsCode = map.get("smsCode");
        log.info("smsCode:{}", "smsCode:" + userId);
        if (!smsCode.equals(RedisUtil.get("smsCode:" + userId))) {
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

            JSONObject result = HttpClientUtils.httpGet("https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid);
            System.out.println("result1---" + result);

            if (result != null) {
                if ("0".equals(result.get("errcode"))) {
                    result = HttpClientUtils.httpGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid);

                    System.out.println("result2---" + result);
                    if (result != null) {
                        // 正确返回
                        if (null == result.get("errcode")) {
                            //微信用户统一标识
                            String unionid = (String) result.get("unionid");
                            TbUserInfo userInfo = baseMapper.selectOne(new QueryWrapper<TbUserInfo>().eq("unionid", unionid));
                            if (userInfo != null) {
                                userInfo.setLastOnlineTime(new Date());
                                userInfo.setLastOnlineIp(ip);
                                baseMapper.updateById(userInfo);
                            } else {
                                userInfo = new TbUserInfo();
                                userInfo.setUdid(UUID.randomUUID().toString());
                                userInfo.setLoginType("wx");
                                userInfo.setThirdUnionId(unionid);
                                userInfo.setLastOnlineTime(new Date());
                                userInfo.setLastOnlineIp(ip);
                                baseMapper.insert(userInfo);
                            }
                            return Response.success(new ResultData(200,"success",result));
                        } else {
                            return Response.error(new ResultData(500,"获取用户个人信息失败",null));
                        }
                    }
                } else if ("40003".equals(result.get("errcode"))) {
                    return Response.error(new ResultData(500,"检验授权凭证失败",null));

                } else {
                    return Response.error(new ResultData(500,"处理异常",null));
                }
            }
        } catch (Exception e) {
            log.info("Exception:{}",e);
            return Response.error(new ResultData(500,"服务器异常",null));

        }
        return null;
    }

    /**
     * QQ登录
     *
     * @param map
     * @return
     */
    @Override
    public String qqLogin(Map<String, String> map) {
        log.info("用户qq登录信息,userInfo:{}", map.toString());
//        UserInfo user=baseMapper.selectOne(new QueryWrapper<UserInfo>()
//                .eq("user_phone",userInfo.getUserPhone())
//                .eq("login_type",userInfo.getLoginType()));
//        if(user!=null){
//            return "false";
//        }
        TbUserInfo userInfo = new TbUserInfo();
        String access_token = map.get("access_token");
        String openid = map.get("openid");
        String ip = map.get("ip");

        JSONObject result = HttpClientUtils.httpGet(
                "https://graph.qq.com/user/get_user_info?access_token="
                        + access_token + "&openid=" + openid + "&format=json" + "&oauth_consumer_key=12345");


        if (result.get("ret").equals("0")) {
            log.info("qq登录,获取用户信息:{}", result.toJSONString());

            Long userId= IdGeneratorUtil.generateId();

//            String userId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
            userInfo.setNickName(result.get("nickname").toString());
            userInfo.setAvatar(result.get("figureurl_qq_1").toString());
            userInfo.setGender(result.get("gender").toString().equals("男")?1:2);
            userInfo.setLoginType("qq");
            userInfo.setUserId(userId);
            userInfo.setLastOnlineIp(ip);
            userInfo.setAccessToken(access_token);
            userInfo.setThirdUnionId(openid);
            userInfo.setCreateTime(new Date());
            userInfo.setLastUpdateTime(new Date());
            userInfo.setLastOnlineTime(new Date());

            baseMapper.insert(userInfo);
        }
        return "success";
    }

    @Override
    public String wbLogin(Map<String, String> map) {
        TbUserInfo userInfo = new TbUserInfo();
        String access_token = map.get("access_token");
        String uid = map.get("uid");
        String ip = map.get("ip");

//        JSONObject result = HttpClientUtils.httpGet("https://api.weibo.com/2/users/show.json?access_token="+access_token);
//
//        if(result.size()>0){
//            log.info("微博登录,获取用户信息:{}",result.toJSONString());
//            result.get("screen_name");
//            String userId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
//            userInfo.setLoginType("wb");
//            userInfo.setUserId(userId);
//            userInfo.setLastOnlineIp(ip);
//            userInfo.setAccessToken(access_token);
//            userInfo.setUnionid(uid);
//            userInfo.setCreateTime(new Date());
//            userInfo.setLastUpdateTime(new Date());
//            userInfo.setLastOnlineTime(new Date());
//
//            baseMapper.insert(userInfo);
//        }
        return "success";
    }

}
