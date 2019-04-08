package com.didong.fallback;

import com.alibaba.fastjson.JSONObject;
import com.didong.dto.VideoInfoDTO;
import com.didong.service.BackVideoService;
import org.springframework.stereotype.Component;

/**
 * @program: didong-app
 * @description: 后台视频服务异常处理
 * @author: moonLiker
 * @create: 2019-04-04
 */
@Component
public class BackVideoServiceFallback implements BackVideoService {


    @Override
    public JSONObject getVideoInfo(VideoInfoDTO videoInfoDTO) {
        return null;
    }
}
