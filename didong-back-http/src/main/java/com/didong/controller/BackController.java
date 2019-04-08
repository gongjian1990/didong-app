package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.didong.dto.VideoInfoDTO;
import com.didong.httpEntity.TbUserInfo;
import com.didong.service.BackUserService;
import com.didong.service.BackVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;
import pojo.ResultData;

/**
 * @program: didong-app
 * @description: 后台管理入口
 * @author: moonLiker
 * @create: 2019-04-04
 */
@RestController
@Slf4j
@RequestMapping(value="/back",produces="application/json;charset=UTF-8")
public class BackController {

    @Autowired
    BackVideoService backVideoService;

    @Autowired
    BackUserService backUserService;

    /**
     * 全部视频列表
     * @param videoInfoDTO
     * @return
     */
    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO) {

        if(StringUtils.hasText(videoInfoDTO.getNickName())
                ||null!=videoInfoDTO.getUserId()
                ||StringUtils.hasText(videoInfoDTO.getUserPhone())){
            //传用户属性
            TbUserInfo userInfo=backUserService.getUserInfo(videoInfoDTO);
            videoInfoDTO.setUserId(userInfo.getUserId());
            JSONObject json = backVideoService.getVideoInfo(videoInfoDTO);
            IPage<VideoInfoDTO> result= (IPage<VideoInfoDTO>) json.get("result");
            for(VideoInfoDTO infoDTO:result.getRecords()){
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalPages", result.getPages());
            jsonObject.put("totalElements", result.getTotal());
            jsonObject.put("list", JSONArray.toJSON(result.getRecords()));
            jsonObject.put("pageIndex", 1);
            jsonObject.put("pageSize", 10);
            return JSON.toJSONString(Response.success(new ResultData(200,"查询成功",jsonObject)));

        }else{
            //不传用户属性
            JSONObject json = backVideoService.getVideoInfo(videoInfoDTO);
            IPage<VideoInfoDTO> result= (IPage<VideoInfoDTO>) json.get("result");
            for(VideoInfoDTO infoDTO:((IPage<VideoInfoDTO>) json.get("result")).getRecords()){
                TbUserInfo userInfo=backUserService.getUserInfo(infoDTO);
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalPages", result.getPages());
            jsonObject.put("totalElements", result.getTotal());
            jsonObject.put("list", JSONArray.toJSON(result.getRecords()));
            jsonObject.put("pageIndex", 1);
            jsonObject.put("pageSize", 10);
            return JSON.toJSONString(Response.success(new ResultData(200,"查询成功",jsonObject)));

        }
    }
}
