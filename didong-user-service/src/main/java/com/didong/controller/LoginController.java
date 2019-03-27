package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.didong.entity.UserInfo;
import com.didong.service.LoginService;
import com.didong.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/getWXAccessToken")
    public JSONObject getWXAccessToken(@RequestBody Map map){

        return loginService.getWXAccessToken(map);
    }

    @RequestMapping("/qqLogin")
    public String qqLogin(@RequestBody Map<String,String> map){

        return loginService.qqLogin(map);
    }

    @RequestMapping("/wbLogin")
    public String wbLogin(@RequestBody  Map<String,String> map){

        return loginService.wbLogin(map);
    }

    @RequestMapping("/getSmsCode")
    public JSONObject getSmsCode(@RequestBody Map<String,String> map){
        return loginService.getSmsCode(map);
    }

    @RequestMapping("/checkSmsCode")
    public String checkSmsCode(@RequestBody Map<String,String> map){

        return loginService.checkSmsCode(map);
    }

    @RequestMapping("/checkWXAccessToken")
    public Response checkWXAccessToken(@RequestBody Map map){
        return loginService.checkWXAccessToken(map);
    }

}
