package com.didong.controller;

import com.alibaba.fastjson.JSON;
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
     * 视频内容安全检测
     * @param videoUrl
     * @return
     */
    @RequestMapping("/checkVideo")
    public String checkVideo(String videoUrl){
        log.info("[视频内容安全检测] -- videoUrl:{}", videoUrl);
        ResultData result = videoService.checkVideo(videoUrl);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));

    }
}
