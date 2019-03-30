package com.didong.controller;

import com.didong.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/testController")
public class TestController {

    @Autowired
    private VideoService videoService;

    @RequestMapping("/hello")
    public String hello(@NotNull(message = "入参不能为空") String s){

        String hello = videoService.hello("44");
        System.out.println("result: "+hello);
        return hello;
    }

}
