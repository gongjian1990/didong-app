package com.didong.controller;


import com.didong.service.impl.TbChkVideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;

import java.util.Map;

/**
 * <p>
 * 视频审核表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@RestController
@RequestMapping("/tbChkVideo")
public class TbVideoChkController {

    @Autowired
    private TbChkVideoServiceImpl tbChkVideoService;

    @PostMapping("/backChkVideo")
    public Response backChkVideo(@RequestBody Map map){

        long vidoeId = (long) map.get("vidoeId");
        long backUserId = (long) map.get("backUserId");
        Integer chkVal = (Integer) map.get("chkVal");

        return tbChkVideoService.chkVideo(vidoeId,chkVal,backUserId);
    }

}
