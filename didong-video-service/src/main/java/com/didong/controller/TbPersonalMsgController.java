package com.didong.controller;


import com.aliyuncs.exceptions.ClientException;
import com.didong.service.ITbVedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 个人消息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@RestController
@RequestMapping("/tbPersonalMsg")
public class TbPersonalMsgController {

    @Autowired
    ITbVedioService iTbVedioService;

    @RequestMapping("/checkVideo")
    public ResultData checkVideo(@RequestBody String videoUrl) throws UnsupportedEncodingException, ClientException {
        return iTbVedioService.checkVideo(videoUrl);
    }
}
