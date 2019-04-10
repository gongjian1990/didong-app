package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.dto.VideoInfoAppDTO;
import com.didong.dto.VideoInfoDTO;
import com.didong.enums.UnifiedApiMethod;
import com.didong.httpEntity.TbVideo;
import com.didong.service.VideoService;
import com.didong.util.AliStsUtil;
import com.didong.util.VodUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.Response;
import pojo.ResultData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/video")
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class VideoController {

    @Autowired
    VideoService videoService;

    @Value("${upload-folder}")
    private String UPLOAD_FOLDER;

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2019-04-01 17:16:12");
        System.out.println(date);

    }

    /**
     * 获取视频信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(@RequestParam(name = "userId", required = false) Long userId,
                               @RequestParam(name = "method", required = false) String method,
                               @RequestParam(name = "queryTime", required = false) String queryTime,
                               @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date time = null;
        try {
            time = format.parse(queryTime);
        } catch (ParseException e) {
            log.error("[获取视频信息]--时间格式转换异常,e{}", e);
            return JSON.toJSONString(Response.Tx400error("时间格式转换异常"));
        }
        UnifiedApiMethod apiMethod = UnifiedApiMethod.valueOf(method);
        if (UnifiedApiMethod.RECOMMEND.equals(apiMethod)) {
            return recommend(userId, pageIndex, pageSize, time);
        } else if (UnifiedApiMethod.FOLLOW.equals(apiMethod)) {
            return follow(userId, pageIndex, pageSize, time);
        } else if (UnifiedApiMethod.NEARBY.equals(apiMethod)) {
            return nearby(userId, pageIndex, pageSize, time);
        } else if (UnifiedApiMethod.NEWEST.equals(apiMethod)) {
            return newest(userId, pageIndex, pageSize, time);
        } else {
            return JSON.toJSONString(Response.success(new ResultData(500, "暂不支持的类型", null)));
        }
    }

    public String recommend(Long userId, Integer pageIndex, Integer pageSize, Date queryTime) {
        return null;
    }

    public String follow(Long userId, Integer pageIndex, Integer pageSize, Date queryTime) {
        return null;
    }

    public String nearby(Long userId, Integer pageIndex, Integer pageSize, Date queryTime) {
        return null;
    }

    public String newest(Long userId, Integer pageIndex, Integer pageSize, Date queryTime) {

        return videoService.getNewestVideo(userId, pageIndex, pageSize, queryTime);
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


    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public Response uploadVide(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            try {
                String originalFilename = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get(UPLOAD_FOLDER));
                }
                //文件写入指定路径
                Files.write(path, bytes);

                int type = chkFileType(originalFilename);

                String url = null;

                if (type == 1) {
                    url = VodUploadUtil.uploadImageLocalFile(UPLOAD_FOLDER + originalFilename, "cover");
                } else {
                    url = VodUploadUtil.uploadVideo(System.currentTimeMillis() + "", UPLOAD_FOLDER + originalFilename);
                }

                if (url == null) {
                    return Response.error(new ResultData(500, "服务器异常", null));
                }

                System.out.println("url:" + url);

                return Response.success(new ResultData(200, "success", url));

            } catch (IOException e) {
                e.printStackTrace();
                return Response.error(new ResultData(500, "服务器异常", null));
            }
        }
        return null;
    }

    /**
     * 检查文件类型
     *
     * @param fileName
     * @return 0：视频，1：图片
     */
    public int chkFileType(String fileName) {

        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        if ("bmp dib rle emf gif jpg jpeg jpe jif pcx dcx pic png tga tif tiffxif wmf jfif".contains(suffix)) {
            return 1;
        }
        return 0;
    }

    /**
     * 后台上传视频保存
     */
    @RequestMapping("/backSaveVideo")
    public Response backSaveVideo(@RequestBody LinkedHashMap<String, Object> data) {

        Set<Map.Entry<String, Object>> entries = data.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        TbVideo video = new TbVideo();
        Integer personChkStatus = null;
        Integer videoUpDownStatus = null;
        Integer videoType = null;
        String nickName = null;

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            String value = (String) next.getValue();
            if ("personChkStatus".equals(key)) {
                personChkStatus = Integer.parseInt(value);
            } else if ("videoUpDownStatus".equals(key)) {
                videoUpDownStatus = Integer.parseInt(value);
            } else if ("owner".equals(key)) {
                video.setOwner(Integer.parseInt(value));
            } else if ("nickName".equals(key)) {
                nickName = value;
            } else if ("oneFps".equals(key)) {
                video.setOneFps(value);
            } else if ("videoUrl".equals(key)) {
                video.setVideoUrl(value);
            } else if ("longitude".equals(key)) {
                video.setLongitude(Double.parseDouble(value));
            } else if ("latitude".equals(key)) {
                video.setLatitude(Double.parseDouble(value));
            } else if ("videoType".equals(key)) {
                video.setVideoType(value);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("tbVideo", video);
        map.put("personChkStatus", personChkStatus);
        map.put("videoUpDownStatus", videoUpDownStatus);
        map.put("nickName", nickName);
        map.put("videoType", videoType);
        Response response = videoService.backSaveVideo(map);
        return response;
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello1(@RequestParam("hello") String hello) {
        System.out.println("hello:" + hello);
        return "world";
    }

}
