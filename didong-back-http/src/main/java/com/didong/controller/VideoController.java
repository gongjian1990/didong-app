package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.didong.dto.VideoInfoDTO;
import com.didong.httpEntity.TbUserInfo;
import com.didong.httpEntity.TbVideo;
import com.didong.service.BackUserService;
import com.didong.service.BackVideoService;
import com.didong.util.VodUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.Response;
import pojo.ResultData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @program: didong-app
 * @description: 后台管理入口
 * @author: moonLiker
 * @create: 2019-04-04
 */
@RestController
@Slf4j
@RequestMapping(value = "/video", produces = "application/json;charset=UTF-8")
public class VideoController {

    @Autowired
    BackVideoService backVideoService;

    @Autowired
    BackUserService backUserService;

    @Value("${upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * 全部视频列表
     *
     * @param videoInfoDTO
     * @return
     */
    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO) {
        if(videoInfoDTO.getPageIndex()==null){
            videoInfoDTO.setPageIndex(1);
        }
        if(videoInfoDTO.getPageSize()==null){
            videoInfoDTO.setPageSize(10);
        }

        if (StringUtils.hasText(videoInfoDTO.getNickName())
                || null != videoInfoDTO.getUserId()
                || StringUtils.hasText(videoInfoDTO.getUserPhone())) {
            //传用户属性
            TbUserInfo userInfo = backUserService.getUserInfo(videoInfoDTO);
            videoInfoDTO.setUserId(userInfo.getUserId());
            //获取用户视频信息
            String result = backVideoService.getVideoInfo(videoInfoDTO);
            JSONObject jsonObject=JSONObject.parseObject(result);
            IPage<VideoInfoDTO> VideoInfoDTOiPage=JSONObject.toJavaObject(jsonObject,IPage.class);
            List<VideoInfoDTO> list = JSON.parseArray(jsonObject.getString("records"), VideoInfoDTO.class);
            for (VideoInfoDTO infoDTO : list) {
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("totalPages", VideoInfoDTOiPage.getPages());
            resultJson.put("totalElements", VideoInfoDTOiPage.getTotal());
            resultJson.put("list", JSONArray.toJSON(list));
            resultJson.put("pageIndex", videoInfoDTO.getPageIndex());
            resultJson.put("pageSize", videoInfoDTO.getPageSize());
            return JSON.toJSONString(Response.success(new ResultData(200, "查询成功", resultJson)));

        } else {
            //不传用户属性
            String result = backVideoService.getVideoInfo(videoInfoDTO);
            JSONObject jsonObject=JSONObject.parseObject(result);
            IPage<VideoInfoDTO> VideoInfoDTOiPage=JSONObject.toJavaObject(jsonObject,IPage.class);
            List<VideoInfoDTO> list = JSON.parseArray(jsonObject.getString("records"), VideoInfoDTO.class);
            for (VideoInfoDTO infoDTO : list) {
                TbUserInfo userInfo = backUserService.getUserInfo(infoDTO);
                infoDTO.setNickName(userInfo.getNickName());
                infoDTO.setAvatar(userInfo.getAvatar());
                infoDTO.setUserPhone(userInfo.getUserPhone());
            }
            JSONObject resultJson = new JSONObject();
            resultJson.put("totalPages", VideoInfoDTOiPage.getPages());
            resultJson.put("totalElements", VideoInfoDTOiPage.getTotal());
            resultJson.put("list", JSONArray.toJSON(list));
            resultJson.put("pageIndex", videoInfoDTO.getPageIndex());
            resultJson.put("pageSize", videoInfoDTO.getPageSize());
            return JSON.toJSONString(Response.success(new ResultData(200, "查询成功", resultJson)));

        }
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

                if(type==1){
                    url= VodUploadUtil.uploadImageLocalFile(UPLOAD_FOLDER + originalFilename,"cover");
                }else {
                    url = VodUploadUtil.uploadVideo(System.currentTimeMillis()+"", UPLOAD_FOLDER + originalFilename);
                }

                if(url==null){
                    return Response.error(new ResultData(500, "服务器异常", null));
                }

                System.out.println("url:"+url);

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
     * @param fileName
     * @return 0：视频，1：图片
     */
    public int chkFileType(String fileName){

        String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());

        if("bmp dib rle emf gif jpg jpeg jpe jif pcx dcx pic png tga tif tiffxif wmf jfif".contains(suffix)){
            return 1;
        }
        return 0;
    }

    /**
     * 后台上传视频保存
     */
    @RequestMapping("/backSaveVideo")
    public Response backSaveVideo(@RequestBody LinkedHashMap<String,Object> data){

        Set<Map.Entry<String, Object>> entries = data.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        TbVideo video = new TbVideo();
        Integer personChkStatus = null;
        Integer videoUpDownStatus = null;
        Integer videoType = null;
        String nickName = null;

        while (iterator.hasNext()){
            Map.Entry<String, Object> next  = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if("personChkStatus".equals(key)){
                personChkStatus = Integer.parseInt(value.toString());
            }else if("videoUpDownStatus".equals(key)){
                videoUpDownStatus = Integer.parseInt(value.toString());
            }else if("owner".equals(key)){
                video.setOwner(Integer.parseInt(value.toString()));
            }else if("nickName".equals(key)){
                nickName = value.toString();
            }else if("oneFps".equals(key)){
                video.setOneFps(value.toString());
            }else if("videoUrl".equals(key)){
                video.setVideoUrl(value.toString());
            }else if("longitude".equals(key)){
                video.setLongitude(Double.parseDouble(value.toString()));
            }else if("latitude".equals(key)){
                video.setLatitude(Double.parseDouble(value.toString()));
            }else if("videoType".equals(key)){
                video.setVideoType(value.toString());
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("tbVideo",video);
        map.put("personChkStatus",personChkStatus);
        map.put("videoUpDownStatus",videoUpDownStatus);
        map.put("nickName",nickName);
        map.put("videoType",videoType);
        Response response = backVideoService.backSaveVideo(map);
        return response;
    }

}
