package com.didong.service;

import com.didong.fallback.VideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotNull;

@FeignClient(value = "video-service", fallback = VideoServiceFallback.class)
public interface VideoService {

    @RequestMapping(value = "/tbVideo/hello",method = RequestMethod.POST)
    String hello(@NotNull(message = "入参不能为空") String s);
}
