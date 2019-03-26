package com.didong.controller;

import com.didong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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


    @RequestMapping("/test2")
    public Map authWX(String test1,String test2){



        HashMap<Object, Object> map = new HashMap<>();
        map.put("code","200");

        return map;
    }

}
