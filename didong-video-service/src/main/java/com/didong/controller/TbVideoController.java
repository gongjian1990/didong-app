package com.didong.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 视频表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@RestController
@RequestMapping("/tbVideo")
public class TbVideoController {

    @RequestMapping("/hello")
    public String hello(@RequestBody String s){
        System.out.println("接收："+s);
        return "world";
    }

}
