package com.didong.service;

import com.didong.fallback.VideoServiceFallback;
import com.didong.httpEntity.TbVideo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Response;
import pojo.ResultData;

import java.util.Map;

@FeignClient(value = "video-service", fallback = VideoServiceFallback.class)
public interface VideoService {
    @RequestMapping(method = RequestMethod.POST,value = "vedio/saveVideo")
    ResultData saveVideo(TbVideo tbVideo);

    @RequestMapping("video/saveVideoback")
    Response saveVideoback(TbVideo video);

    @RequestMapping("video/hello")
    Response hello(String s1);

    @RequestMapping("video/selectAllByPage20Videos")
    Response selectAllByPage20Videos(String pageNum);

    @RequestMapping("video/selectAllByPageAndCondition")
    Response selectAllByPageAndCondition(TbVideo video);

    @RequestMapping("video/saveVideoback")
    Response backSaveVideo(Map map);

}
