package com.didong.fallback;

import com.didong.httpEntity.TbVideo;
import com.didong.service.VideoService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

import java.util.Date;
import java.util.Map;

@Component
public class VideoServiceFallback implements VideoService {

    @Override
    public ResultData saveVideo(TbVideo tbVideo) {
        return new ResultData(500, "视频上传失败", null);

    }

    @Override
    public Response saveVideoback(TbVideo video) {
        return Response.success(new ResultData(500, "保存视频失败", null));
    }

    @Override
    public Response hello(String s1) {
        return null;
    }

    @Override
    public Response selectAllByPage20Videos(String pageNum) {
        return Response.success(new ResultData(500, "加载视频列表失败", null));
    }

    @Override
    public Response selectAllByPageAndCondition(TbVideo video) {
        return null;
    }

    @Override
    public Response backSaveVideo(Map map) {
        return Response.success(new ResultData(500, "上传视频失败", null));
    }

    @Override
    public String getNewestVideo(Long userId, Integer pageIndex, Integer pageSize, Date queryTime) {
        return null;
    }


}
