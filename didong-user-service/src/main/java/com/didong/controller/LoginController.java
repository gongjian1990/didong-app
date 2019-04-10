package com.didong.controller;

import com.alibaba.fastjson.JSONObject;
import com.didong.service.BackLoginService;
import com.didong.service.LoginService;
import com.didong.serviceEntity.TbBackUser;
import org.springframework.web.bind.annotation.PostMapping;
import pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.util.Map;

@RestController
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private BackLoginService backLoginService;

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

    @PostMapping("/backLogin")
    public Response backLogin(@RequestBody Map map){
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        TbBackUser backUser = backLoginService.backLogin(username, password);
        if(backUser!= null){
            return Response.success(new ResultData(200,"登录成功",backUser));
        }else {
            return Response.error(new ResultData(500,"用户名或密码错误",null));
        }
    }

}
