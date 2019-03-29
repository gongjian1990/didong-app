package com.didong.controller;

import com.didong.UserInfoService;
import com.didong.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/userController")
@Slf4j
public class UserController {

    @Autowired
    public UserInfoService userinfoService;


    @RequestMapping("/updateUserData")
    public String updateUserData(@RequestBody UserInfo userInfo){
        return userinfoService.updateUserData(userInfo);
    }

}
