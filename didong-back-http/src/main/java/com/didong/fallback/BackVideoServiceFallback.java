package com.didong.fallback;

import com.alibaba.fastjson.JSONObject;
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

    @Override
    public Response backChkVideo(Map map) {
        return Response.success(new ResultData(500,"视频审核失败",null));
    }

    @Override
    public String getDownVideoInfo(VideoInfoDTO videoInfoDTO) {
        return JSONObject.toJSONString(Response.success(new ResultData(500,"获取视频失败",null)));
    }

    @Override
    public String getPersonChkVideoPage(VideoInfoDTO videoInfoDTO) {
        return JSONObject.toJSONString(Response.success(new ResultData(500,"获取人工审核视频列表失败",null)));

    }
}
