package com.didong.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.dto.VideoInfoAppDTO;
import com.didong.dto.VideoInfoDTO;
import com.didong.service.ITbVideoService;
import com.didong.serviceEntity.TbVideo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pojo.Response;
import pojo.ResultData;

import java.util.*;


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
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hello")
    public String hello(@RequestBody String s) {
        System.out.println("接收：" + s);
        return "world";
    }

    /**
     * 获取最新视频
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @param queryTime
     * @return
     */
    @RequestMapping("/getNewestVideo")
    public IPage<VideoInfoAppDTO> getNewestVideo(@RequestParam Long userId,
                                 @RequestParam Integer pageIndex,
                                 @RequestParam Integer pageSize,
                                 @RequestParam Date queryTime) {
        Page<VideoInfoAppDTO> page = new Page(pageIndex, pageSize);
        iTbVideoService.getNewestVideo(userId, page, queryTime);
        return null;
    }


    @RequestMapping("/getVideoInfo")
    public IPage<VideoInfoDTO> getVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO) {
        Page<VideoInfoDTO> page = new Page(videoInfoDTO.getPageIndex(), videoInfoDTO.getPageSize());
        IPage<VideoInfoDTO> ipage = iTbVideoService.getVideoInfo(videoInfoDTO, page);
        return ipage;
    }

    @RequestMapping("/saveVideo")
    public ResultData saveVideo(@RequestBody TbVideo tbVideo) {
        return iTbVideoService.saveVideo(tbVideo);
    }

    @RequestMapping("/saveVideoback")
    public Response saveVideoback(@RequestBody LinkedHashMap map) {

        ObjectMapper mapper = new ObjectMapper();
        TbVideo video = mapper.convertValue(map.get("tbVideo"), TbVideo.class);

        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Integer personChkStatus = null;
        Integer videoUpDownStatus = null;
        String nickName = null;
        Integer videoType = null;

        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            if ("personChkStatus".equals(key)) {
                //personChkStatus = Integer.parseInt(value.toString());
            } else if ("videoUpDownStatus".equals(key)) {
                videoUpDownStatus = Integer.parseInt(value.toString());
            } else if ("nickName".equals(key)) {
                nickName = value.toString();
            }
        }

        Response response = iTbVideoService.saveVideoback(video, personChkStatus, videoUpDownStatus, nickName);
        return response;
    }

    @RequestMapping("/selectAllByPage20Videos")
    public Response selectAllByPage20Videos(@RequestBody String pageNum) {
        IPage<TbVideo> page = iTbVideoService.selectAllByPage20Videos(pageNum);
        if (page != null) {
            return Response.success(new ResultData(200, "", page.getRecords()));
        }
        return null;
    }


    @RequestMapping("/selectAllByPageAndCondition")
    public Response selectAllByPageAndCondition(@RequestBody Map map) {
        String nickName = (String) map.get("nickName");
        String userId = (String) map.get("userId");
        String pageNum = (String) map.get("pageNum");
        String pageSize = (String) map.get("pageSize");

//        nickName = "哈";
//        if(!StringUtils.isBlank(nickName)){
//            List<UserInfo> userInfos = userInfoService.selectUserByNickNameLike(nickName);
//        }

//        String aa = restTemplate.postForObject("http://localhost:8801/testController/test?s=ss", "", String.class);
//        System.out.println("result:"+aa);


        Page<TbVideo> page = new Page(1, 2);

        TbVideo tbVideo = new TbVideo();
        tbVideo.setUserId(2L);
        IPage<TbVideo> page1 = iTbVideoService.selectAllByPageAndCondition(tbVideo, page);

        System.out.println("page: " + page1);

        //
        //IPage<TbVideo> page = iTbVideoService.selectAllByPageAndCondition(null);
        //if(page!= null){
        //   return Response.success(new ResultData(200,"",page.getRecords()));
        // }
        return null;
    }

    @RequestMapping("/getDownVideoInfo")
    public IPage<VideoInfoDTO> getDownVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO){
        Page<VideoInfoDTO> page = new Page(videoInfoDTO.getPageIndex(), videoInfoDTO.getPageSize());
        IPage<VideoInfoDTO>  ipage=iTbVideoService.getDownVideoInfo(videoInfoDTO, page);
        return ipage;
    }

    @RequestMapping("/getPersonChkVideoPage")
    public IPage<VideoInfoDTO> getPersonChkVideoPage(@RequestBody VideoInfoDTO videoInfoDTO){
        Page<VideoInfoDTO> page = new Page(videoInfoDTO.getPageIndex(), videoInfoDTO.getPageSize());
        IPage<VideoInfoDTO>  ipage=iTbVideoService.getPersonChkVideoPage(videoInfoDTO, page);
        return ipage;
    }

}
