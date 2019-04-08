package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.didong.dto.VideoInfoDTO;
import com.didong.httpEntity.TbUserInfo;
import com.didong.service.BackUserService;
import com.didong.service.BackVideoService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;
import pojo.ResultData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: didong-app
 * @description: 后台管理入口
 * @author: moonLiker
 * @create: 2019-04-04
 */
@RestController
@Slf4j
@RequestMapping(value = "/back", produces = "application/json;charset=UTF-8")
public class BackController {

    @Autowired
    BackVideoService backVideoService;

    @Autowired
    BackUserService backUserService;

    /**
     * 全部视频列表
     *
     * @param videoInfoDTO
     * @return
     */
    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO) {
        if(videoInfoDTO.getPageIndex()==null){
            videoInfoDTO.setPageIndex(1);
        }
        if(videoInfoDTO.getPageSize()==null){
            videoInfoDTO.setPageSize(10);
        }

        if (StringUtils.hasText(videoInfoDTO.getNickName())
                || null != videoInfoDTO.getUserId()
                || StringUtils.hasText(videoInfoDTO.getUserPhone())) {
            //传用户属性
            TbUserInfo userInfo = backUserService.getUserInfo(videoInfoDTO);
            videoInfoDTO.setUserId(userInfo.getUserId());
            //获取用户视频信息
            String result = backVideoService.getVideoInfo(videoInfoDTO);
            JSONObject jsonObject=JSONObject.parseObject(result);
            IPage<VideoInfoDTO> VideoInfoDTOiPage=JSONObject.toJavaObject(jsonObject,IPage.class);
            List<VideoInfoDTO> list = JSON.parseArray(jsonObject.getString("records"), VideoInfoDTO.class);
            for (VideoInfoDTO infoDTO : list) {
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("totalPages", VideoInfoDTOiPage.getPages());
            resultJson.put("totalElements", VideoInfoDTOiPage.getTotal());
            resultJson.put("list", JSONArray.toJSON(list));
            resultJson.put("pageIndex", videoInfoDTO.getPageIndex());
            resultJson.put("pageSize", videoInfoDTO.getPageSize());
            return JSON.toJSONString(Response.success(new ResultData(200, "查询成功", resultJson)));

        } else {
            //不传用户属性
            String result = backVideoService.getVideoInfo(videoInfoDTO);
            JSONObject jsonObject=JSONObject.parseObject(result);
            IPage<VideoInfoDTO> VideoInfoDTOiPage=JSONObject.toJavaObject(jsonObject,IPage.class);
            List<VideoInfoDTO> list = JSON.parseArray(jsonObject.getString("records"), VideoInfoDTO.class);
            for (VideoInfoDTO infoDTO : list) {
                TbUserInfo userInfo = backUserService.getUserInfo(infoDTO);
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("totalPages", VideoInfoDTOiPage.getPages());
            resultJson.put("totalElements", VideoInfoDTOiPage.getTotal());
            resultJson.put("list", JSONArray.toJSON(list));
            resultJson.put("pageIndex", videoInfoDTO.getPageIndex());
            resultJson.put("pageSize", videoInfoDTO.getPageSize());
            return JSON.toJSONString(Response.success(new ResultData(200, "查询成功", resultJson)));

        }
    }

}
