package com.didong.fallback;

import com.didong.dto.VideoInfoDTO;
import com.didong.service.BackVideoService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

import java.util.Map;

/**
 * @program: didong-app
 * @description: 后台视频服务异常处理
 * @author: moonLiker
 * @create: 2019-04-04
 */
@Component
public class BackVideoServiceFallback implements BackVideoService {


    @Override
    public String getVideoInfo(VideoInfoDTO videoInfoDTO) {
        return null;
    }

    @Override
    public Response backSaveVideo(Map map) {
        return Response.success(new ResultData(500,"上传视频失败",null));
    }
}
