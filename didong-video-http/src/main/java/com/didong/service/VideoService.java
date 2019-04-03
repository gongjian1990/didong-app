package com.didong.service;

import com.didong.entity.TbVideo;
import com.didong.fallback.VideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Response;
import pojo.ResultData;

@FeignClient(value = "video-service", fallback = VideoServiceFallback.class)
public interface VideoService {

    @RequestMapping(method = RequestMethod.POST,value = "vedio/tb-vedio/checkVideo")
    ResultData checkVideo(String videoUrl);

    @RequestMapping("video/saveVideo")
    Response saveVideo(TbVideo video);

    @RequestMapping("video/hello")
    Response hello(String s1);

    @RequestMapping("video/selectAllByPage20Videos")
    Response selectAllByPage20Videos(String pageNum);

    @RequestMapping("video/selectAllByPageAndCondition")
    Response selectAllByPageAndCondition(TbVideo video);

}
