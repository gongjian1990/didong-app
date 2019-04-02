package com.didong.service;

import com.didong.entity.TbVideo;
import com.didong.fallback.VideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.ResultData;

@FeignClient(value = "vedio-service", fallback = VideoServiceFallback.class)
public interface VideoService {
    @RequestMapping(method = RequestMethod.POST,value = "vedio/tb-vedio/saveVideo")
    ResultData saveVideo(TbVideo tbVideo,String taskId);

}
