package com.didong.controller;


import com.didong.entity.TbVideo;
import com.didong.service.ITbVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;

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
    private ITbVideoService iTbVideoService;

    @RequestMapping("/hello")
    public String hello(@RequestBody String s){
        System.out.println("接收："+s);
        return "world";
    }

    @RequestMapping("/saveVideo")
    public Response saveVideo(@RequestBody TbVideo video){
        iTbVideoService.saveVideo(video);
        return Response.success(null);
    }

}
