package com.didong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserService {

    public static String SERVICE_URL = "http://user-service/";
    @Autowired
    RestTemplate restTemplate;

    /**
     *
     * @param url 服务的url地址
     * @param request 传入服务的对象
     * @param returnType  服务的返回值 一般是String
     * @return
     */
    public <T> T postRestTemplate(String url,Object request,Class<T> returnType) {
        T jsonString = restTemplate.postForObject(SERVICE_URL+url, request, returnType);
        return jsonString;
    }

}
