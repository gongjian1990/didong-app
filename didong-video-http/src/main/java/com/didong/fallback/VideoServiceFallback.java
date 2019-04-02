package com.didong.fallback;

import com.didong.entity.TbVideo;
import com.didong.service.VideoService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

@Component
public class VideoServiceFallback implements VideoService {

    @Override
    public ResultData saveVideo(TbVideo tbVideo, String taskId) {
        return new ResultData(500, "视频上传失败", null);

    }

    @Override
    public Response saveVideo(TbVideo video) {
        return Response.success(new ResultData(500,"保存视频失败",null));
    }

    @Override
    public Response hello(String s1) {
        return null;
    }
}
