package com.didong.controller;

import com.didong.service.BackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Response;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private BackUserService backUserService;

    @RequestMapping("/backLogin")
    public Response backLogin(String username,String password){
        Map map = new HashMap();
        map.put("username",username);
        map.put("password",password);
        return backUserService.backLogin(map);
    }
}
