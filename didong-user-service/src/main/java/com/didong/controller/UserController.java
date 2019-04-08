package com.didong.controller;

import com.aliyuncs.exceptions.ClientException;
import com.didong.dto.VideoInfoDTO;
import com.didong.service.UserInfoService;
import com.didong.serviceEntity.TbUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/userController")
@Slf4j
public class UserController {

    @Autowired
    public UserInfoService userinfoService;

    @RequestMapping("/getUserInfo")
    public VideoInfoDTO getVideoInfoDTOInfo(@RequestBody VideoInfoDTO videoInfoDTO) {
        return userinfoService.getVideoInfoDTOInfo(videoInfoDTO);
    }

    @RequestMapping("/updateUserData")
    public ResultData updateUserData(@RequestBody TbUserInfo userInfo) throws UnsupportedEncodingException, ClientException {
        return userinfoService.updateUserData(userInfo);
    }
}
