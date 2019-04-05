package com.didong.controller;

import com.didong.dto.VideoInfoDTO;
import com.didong.service.BackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: didong-app
 * @description: 后台管理入口
 * @author: moonLiker
 * @create: 2019-04-04
 */
@RestController
@Slf4j
@RequestMapping(value="/back",produces="application/json;charset=UTF-8")
public class BackController {

    @Autowired
    BackService backService;

    @RequestMapping("/getVideoInfo")
    public String getVideoInfo(@RequestBody VideoInfoDTO videoInfoDTO) {

        List<VideoInfoDTO> result = backService.getVideoInfo(videoInfoDTO);

        return null;
    }
}
