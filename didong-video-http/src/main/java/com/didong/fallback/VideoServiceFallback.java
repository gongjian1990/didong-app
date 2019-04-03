package com.didong.fallback;

import com.didong.entity.TbVideo;
import com.didong.service.VideoService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

@Component
public class VideoServiceFallback implements VideoService {

    @Override
    public ResultData checkVideo(String videoUrl) {
        return new ResultData(500, "视频审核失败", null);
    }

    @Override
    public Response saveVideo(TbVideo video) {
        return Response.success(new ResultData(500,"保存视频失败",null));
    }

    @Override
    public Response hello(String s1) {
        return null;
    }

    @Override
    public Response selectAllByPage20Videos(String pageNum) {
        return Response.success(new ResultData(500,"加载视频列表失败",null));
    }

    @Override
    public Response selectAllByPageAndCondition(TbVideo video) {
        return null;
    }
}
