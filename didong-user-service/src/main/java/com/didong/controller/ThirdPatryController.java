package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.didong.service.ThirdPatryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/thirdPatryController")
public class ThirdPatryController {

    @Autowired
    private ThirdPatryService thirdPatryService;

    @PostMapping("/getWXAccessToken")
    public JSONObject getWXAccessToken(@RequestBody Map map){

        return thirdPatryService.getWXAccessToken(map);
    }

}
