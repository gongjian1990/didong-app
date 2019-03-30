package com.didong.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.service.UserInfoService;
import com.didong.entity.UserInfo;
import com.didong.enums.AliTextLabelEnum;
import com.didong.mapper.UserInfoMapper;
import com.didong.util.AliCheckUtils;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * User 表数据服务层接口实现类
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {



    @Override
    public ResultData updateUserData(UserInfo userInfo) throws UnsupportedEncodingException, ClientException {
        ResultData resultData = new ResultData();
        JSONObject retJson = new JSONObject();

        //头像审核
        JSONObject httpBody = new JSONObject();
        /**
         * 设置要检测的场景, 计费是按照该处传递的场景进行
         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算
         * 例如：检测2张图片，场景传递porn,terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算
         * porn: porn表示色情场景检测
         */
        httpBody.put("scenes", Arrays.asList("porn", "terrorism", "live"));


        /**
         * 设置待检测图片， 一张图片一个task，
         * 多张图片同时检测时，处理的时间由最后一个处理完的图片决定。
         * 通常情况下批量检测的平均rt比单张检测的要长, 一次批量提交的图片数越多，rt被拉长的概率越高
         * 这里以单张图片检测作为示例, 如果是批量图片检测，请自行构建多个task
         */
        JSONObject task = new JSONObject();
        task.put("dataId", UUID.randomUUID().toString());

        //设置图片链接，图片审核
        task.put("url", userInfo.getAvatar());
        task.put("time", new Date());
        httpBody.put("tasks", Arrays.asList(task));
        JSONArray jsonArray = AliCheckUtils.checkVideo(httpBody);
        if (jsonArray.size() > 0) {
            for (Object object : jsonArray) {
                if (((JSONObject) object).get("suggestion").equals("review") || ((JSONObject) object).get("suggestion").equals("block")) {
                    String label = ((JSONObject) object).get("label").toString();
                    resultData.setCode(500);
                    retJson.put("scene", ((JSONObject) object).get("scene"));
                    retJson.put("suggestion", ((JSONObject) object).get("suggestion"));
                    retJson.put("label", label);
                    retJson.put("reason", "头像审核未通过");
                    resultData.setResults(retJson);
                    return resultData;
                }
            }
        }else {
            resultData.setCode(500);
            resultData.setMessage("图片审核异常");
            return resultData;
        }
        //昵称检测
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task1 = new LinkedHashMap<String, Object>();
        task1.put("dataId", UUID.randomUUID().toString());
        /**
         * 待检测的文本，长度不超过10000个字符
         */
        task1.put("content", userInfo.getNickName());
        tasks.add(task1);
        JSONArray response = AliCheckUtils.checkText(tasks);
        if (response.size() > 0) {
            for (Object object : response) {
                if (((JSONObject) object).get("suggestion").equals("review") || ((JSONObject) object).get("suggestion").equals("block")) {
                    String label = ((JSONObject) object).get("label").toString();
                    resultData.setCode(500);
                    resultData.setMessage("修改资料失败");
                    retJson.put("suggestion", ((JSONObject) object).get("suggestion"));
                    retJson.put("label", label);
                    retJson.put("labelMsg", AliTextLabelEnum.valueOf(label.toUpperCase()).getAliTextLabelName());
                    retJson.put("reason", "昵称审核未通过");
                    resultData.setResults(retJson);
                    return resultData;
                } else {
                    resultData.setCode(200);
                    resultData.setMessage("修改资料成功");
                    baseMapper.update(userInfo, new QueryWrapper<UserInfo>().eq("user_id", userInfo.getUserId()));
                }
            }
            return resultData;
        } else {
            resultData.setCode(500);
            resultData.setMessage("文本审核异常");
            return resultData;
        }
    }
}