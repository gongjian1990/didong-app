package com.didong.controller;


import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.service.ITbVideoService;
import com.didong.service.UserInfoService;
import com.didong.serviceEntity.TbVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.Map;


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
    private UserInfoService userInfoService;

    @RequestMapping("/hello")
    public String hello(@RequestBody String s){
        System.out.println("接收："+s);
        return "world";
    }

    @RequestMapping("/saveVideo")
    public ResultData saveVideo(@RequestBody TbVideo tbVideo) throws UnsupportedEncodingException, ClientException {
        return iTbVideoService.saveVideo(tbVideo);
    }

    @RequestMapping("/saveVideoback")
    public Response saveVideoback(@RequestBody TbVideo video){
        iTbVideoService.saveVideoback(video);
        return Response.success(null);
    }

    @RequestMapping("/selectAllByPage20Videos")
    public Response selectAllByPage20Videos(@RequestBody String pageNum) {
        IPage<TbVideo> page = iTbVideoService.selectAllByPage20Videos(pageNum);
        if(page!= null){
            return Response.success(new ResultData(200,"",page.getRecords()));
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

        Page<TbVideo> page = new Page(1, 2);

        TbVideo tbVideo = new TbVideo();
        tbVideo.setUserId(2L);
        IPage<TbVideo> page1 = iTbVideoService.selectAllByPageAndCondition(tbVideo,page);

        System.out.println("page: "+page1);

        //
        //IPage<TbVideo> page = iTbVideoService.selectAllByPageAndCondition(null);
        //if(page!= null){
         //   return Response.success(new ResultData(200,"",page.getRecords()));
       // }
        return null;
    }


}
