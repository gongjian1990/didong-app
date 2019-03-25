package com.didong.controller;

import com.didong.entity.UserInfo;
import com.didong.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public String test(@RequestBody String in){
        System.out.println("in--------->"+in);
        return "world";
    }
}
