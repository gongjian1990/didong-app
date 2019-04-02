package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.didong.entity.TbVideo;
import com.didong.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;
import pojo.ResultData;

@RestController
@RequestMapping("/video")
@Slf4j
public class VideoController {

    @Autowired
    VideoService videoService;

    /**
     * 视频上传
     * @param tbVideo
     * @param taskId
     * @return
     */
    @RequestMapping("/saveVideo")
    public String saveVideo(TbVideo tbVideo,String taskId){
        log.info("[视频上传] -- tbVideo:{},taskId:{}", tbVideo,taskId);
        ResultData result = videoService.saveVideo(tbVideo,taskId);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));
    }
}