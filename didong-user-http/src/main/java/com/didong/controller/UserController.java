package com.didong.controller;

import com.didong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/test1")
    public String hello(){

        String s = userService.postRestTemplate("user/test","55",String.class);
        System.out.println("s-----"+s);
        return "";
    }


    @RequestMapping("/authWX")
    public String authWX(Map map){

        String test = (String) map.get("test");

        System.out.println("test--"+test);


        return null;
    }

}
