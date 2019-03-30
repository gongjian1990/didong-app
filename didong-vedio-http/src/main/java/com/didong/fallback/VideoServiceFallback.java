package com.didong.fallback;

import com.didong.service.VideoService;
import org.springframework.stereotype.Component;
import pojo.ResultData;

@Component
public class VideoServiceFallback implements VideoService {

    @Override
    public ResultData checkVideo(String videoUrl) {
        return new ResultData(500, "视频审核失败", null);
    }
}
