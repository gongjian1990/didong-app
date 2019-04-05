package com.didong.service;

import com.didong.dto.VideoInfoDTO;
import com.didong.fallback.BackServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @program: didong-app
 * @description: 后台服务
 * @author: moonLiker
 * @create: 2019-04-04
 */
@FeignClient(value = "video-service", fallback = BackServiceFallback.class)
public interface BackService {

    @RequestMapping(method = RequestMethod.POST,value = "video/getVideoInfo")
    List<VideoInfoDTO> getVideoInfo(VideoInfoDTO videoInfoDTO);

}
