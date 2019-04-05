package com.didong.fallback;

import com.didong.dto.VideoInfoDTO;
import com.didong.service.BackService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: didong-app
 * @description: 后台异常处理
 * @author: moonLiker
 * @create: 2019-04-04
 */
@Component
public class BackServiceFallback implements BackService {


    @Override
    public List<VideoInfoDTO> getVideoInfo(VideoInfoDTO videoInfoDTO) {
        return null;

    }
}
