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

    @RequestMapping("/hello")
    public String hello(String s){

        videoService.hello("Sss");

        return null;
    }

    @RequestMapping("/saveVideo")
    public Response saveVideo(TbVideo video){
        try {
            videoService.saveVideo(video);
            return Response.success(new ResultData(200,"保存成功",null));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500,"保存失败",null));
        }
    }

    @RequestMapping("/selectAllByPage20Videos")
    public Response selectAllByPage20Videos(String num){
        try {
            Response response = videoService.selectAllByPage20Videos(num);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500,"查询视频失败",null));
        }
    }

    @RequestMapping("/selectAllByPage20Videos1")
    public Response selectAllByPageAndCondition(TbVideo video){
        try {
            Response response = videoService.selectAllByPageAndCondition(video);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500,"查询视频失败",null));
        }
    }
}
