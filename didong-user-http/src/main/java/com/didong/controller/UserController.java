package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

 /*   @RequestMapping("/test1")
    public String hello(){

        String s = userService.postRestTemplate("user/test","55",String.class);
        System.out.println("s-----"+s);
        return "";
    }*/

 @RequestMapping("/hello")
 public Response hello(){
    return userService.hello("hello");
 }
    /**
     * 新用户个人资料完善
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUserData")
    public String updateUserData(UserInfo userInfo){
        log.info("[新用户个人资料完善] -- userInfo:{}", userInfo);
        String result=userService.updateUserData(userInfo);
        if(!result.equals("success")){
            return JSON.toJSONString(Response.error(new ResultData(500,"修改资料失败",result)));
        }
        return JSON.toJSONString(Response.success(new ResultData(200,"修改资料成功",result)));

    }


    @RequestMapping("/test2")
    public Map authWX(String test1,String test2){



        HashMap<Object, Object> map = new HashMap<>();
        map.put("code","200");

        return map;
    }

}
