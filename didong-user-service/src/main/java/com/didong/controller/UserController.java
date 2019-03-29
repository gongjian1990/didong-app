package com.didong.controller;

import com.didong.UserInfoService;
import pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    public UserInfoService userService;

//    @GetMapping("/{sex}")
//    public String test(@PathVariable("sex") String sex) {
//        log.info("做个test");
//
//
//        UserInfo userInfo = userService.getbyId(sex);
//        if (userInfo == null) {
//            return "未查到用户数据";
//        }
//        return userInfo.getEmail();
//    }

    @RequestMapping("/test")
    public Response test(@RequestBody String in){
        System.out.println("in--------->"+in);
        return Response.success("success");
    }

}
