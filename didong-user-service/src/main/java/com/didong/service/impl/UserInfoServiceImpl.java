package com.didong.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.service.UserInfoService;
import com.didong.entity.UserInfo;
import com.didong.enums.AliTextLabelEnum;
import com.didong.mapper.UserInfoMapper;
import com.didong.util.AliCheckUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Override
    public JSONObject updateUserData(UserInfo userInfo) throws UnsupportedEncodingException {
        JSONObject retJson=new JSONObject();
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task1 = new LinkedHashMap<String, Object>();
        task1.put("dataId", UUID.randomUUID().toString());
        /**
         * 待检测的文本，长度不超过10000个字符
         */
        task1.put("content", userInfo.getNickName());
        tasks.add(task1);
        JSONArray response= AliCheckUtils.checkText(tasks);
        //第一期,机器审核未通过与需要人工审核状态的
        if(response.size()>0){
            for (Object object : response) {
                if(((JSONObject)object).get("suggestion").equals("review")||((JSONObject)object).get("suggestion").equals("block")){
                    String label=((JSONObject)object).get("label").toString();
                    retJson.put("suggestion",((JSONObject)object).get("suggestion"));
                    retJson.put("message","审核未通过");
                    retJson.put("code",500);
                    retJson.put("label",((JSONObject)object).get("label"));
                    retJson.put("labelMsg", AliTextLabelEnum.valueOf(label.toUpperCase()).getAliTextLabelName());
                }else {
                    retJson.put("code",200);
                    retJson.put("message", "资料修改成功");
                    baseMapper.update(userInfo,new QueryWrapper<UserInfo>().eq("user_id",userInfo.getUserId()));
                }
            }
            return retJson;
        }else {
            retJson.put("message","审核异常");
            retJson.put("code",500);
            return retJson;
        }
    }
}