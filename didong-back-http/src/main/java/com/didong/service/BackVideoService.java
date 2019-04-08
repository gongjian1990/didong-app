package com.didong.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.didong.dto.VideoInfoDTO;
import com.didong.fallback.BackVideoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: didong-app
 * @description: 后台视频服务
 * @author: moonLiker
 * @create: 2019-04-04
 */
@FeignClient(value = "video-service", fallback = BackVideoServiceFallback.class)
public interface BackVideoService {

    @RequestMapping(method = RequestMethod.POST,value = "video/getVideoInfo")
    String getVideoInfo(VideoInfoDTO videoInfoDTO);

}
