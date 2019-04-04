package com.didong.controller;

import com.aliyuncs.exceptions.ClientException;
import com.didong.service.UserInfoService;
import com.didong.service.UserInterestService;
import com.didong.serviceEntity.UserInfo;
import com.didong.serviceEntity.UserInterest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.List;


@RestController
@RequestMapping("/userInterestController")
@Slf4j
public class UserInterestController {

    @Autowired
    public UserInterestService userInterestService;


    @RequestMapping("/saveUserInterest")
    public ResultData saveUserInterest(@RequestBody List<UserInterest> interestList) {
        return userInterestService.saveUserInterest(interestList);
    }
}
