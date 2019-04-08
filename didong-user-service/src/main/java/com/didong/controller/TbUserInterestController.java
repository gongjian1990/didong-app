package com.didong.controller;

import com.didong.service.TbUserInterestService;
import com.didong.serviceEntity.TbUserInterest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.util.List;


@RestController
@RequestMapping("/userInterestController")
@Slf4j
public class TbUserInterestController {

    @Autowired
    public TbUserInterestService userInterestService;


    @RequestMapping("/saveUserInterest")
    public ResultData saveUserInterest(@RequestBody List<TbUserInterest> interestList) {
        return userInterestService.saveUserInterest(interestList);
    }
}
