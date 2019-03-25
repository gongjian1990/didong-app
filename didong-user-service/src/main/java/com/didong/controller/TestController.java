package com.didong.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testController")
public class TestController {

    @RequestMapping("/test")
    public String test(String s){

        System.out.println("s------"+s);
        return "world";
    }
}
