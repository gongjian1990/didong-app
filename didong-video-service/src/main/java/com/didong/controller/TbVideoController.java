package com.didong.controller;


import com.aliyuncs.exceptions.ClientException;
import com.didong.entity.TbVideo;
import com.didong.service.ITbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;


/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@RestController
@RequestMapping("/video")
public class TbVideoController {

    @Autowired
    ITbVideoService iTbVideoService;

    @RequestMapping("/saveVideo")
    public ResultData saveVideo(@RequestBody TbVideo tbVideo) throws UnsupportedEncodingException, ClientException {
        return iTbVideoService.saveVideo(tbVideo);
    }


    @RequestMapping("/saveVideoback")
    public Response saveVideoback(@RequestBody TbVideo video){
        iTbVideoService.saveVideoback(video);
        return Response.success(null);
    }

}
