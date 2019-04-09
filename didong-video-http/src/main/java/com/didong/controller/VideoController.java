package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.didong.httpEntity.TbVideo;
import com.didong.service.VideoService;
import com.didong.util.AliStsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import pojo.Response;
import pojo.ResultData;

@RestController
@RequestMapping("/video")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class VideoController {

    @Autowired
    VideoService videoService;

    @Value("${upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * 获取视频信息
     * @param userId
     * @return
     */
    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(String userId,String method) {

        return null;
    }

    /**
     * 视频上传 app端
     *
     * @param tbVideo
     * @return
     */
    @RequestMapping("/saveVideo")
    public String saveVideo(TbVideo tbVideo) {
        log.info("[视频上传] -- tbVideo:{}", tbVideo);
        ResultData result = videoService.saveVideo(tbVideo);
        if (!result.getCode().equals(200)) {
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));
    }

    @RequestMapping("/hello")
    public String hello(String s) {

        videoService.hello("Sss");

        return null;
    }


    /**
     * 保存视频 PC端
     *
     * @param video
     * @return
     */
    @RequestMapping("/saveVideoback")
    public Response saveVideoback(TbVideo video) {
        try {
            videoService.saveVideoback(video);
            return Response.success(new ResultData(200, "保存成功", null));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500, "保存失败", null));
        }
    }

    @RequestMapping("/selectAllByPage20Videos")
    public Response selectAllByPage20Videos(String num) {
        try {
            Response response = videoService.selectAllByPage20Videos(num);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500, "查询视频失败", null));
        }
    }

    @RequestMapping("/selectAllByPage20Videos1")
    public Response selectAllByPageAndCondition(TbVideo video) {
        try {
            Response response = videoService.selectAllByPageAndCondition(video);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return Response.success(new ResultData(500, "查询视频失败", null));
        }
    }

    @RequestMapping("/getAliStsResponse")
    public Response getAliStsResponse() {
        return Response.success(new ResultData(200, "", AliStsUtil.getStsResponse()));
    }

//    @RequestMapping("/uploadVideo")
//    public Response uploadVide(@RequestParam("video") MultipartFile video,@RequestParam("photo") MultipartFile photo){
//
//        out.println("video:"+video);
//        out.println("photo:"+photo);
//
//
//
//        if (!video.isEmpty()) {
//            // 上传文件路径
//            // 上传文件名
//            String oldname = video.getOriginalFilename();
//            String filetype=video.getContentType();
//            String filesize=(video.getSize()/1024)+"KB";
//            try {
//
//
//                out.println("file:"+video);
//
//                String path = video.getResource().getFile().getAbsolutePath();
//
//                out.println("path:"+path);
//
//                String[] filenames = oldname.split("\\.");
//                String filename ="";
//                OssUploadUtil.upload("aaa",path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        return null;
//    }
//
//        return null;
//    }


    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1(@RequestParam("hello") String hello) {
        System.out.println("hello:" + hello);
        return "world";
    }

}
