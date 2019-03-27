package com.didong.service;

import com.didong.util.Response;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "errorMethod")
    public <T> T postRestTemplate(String url,Object request,Class<T> returnType) {
        T jsonString = restTemplate.postForObject(SERVICE_URL+url, request, returnType);
        return jsonString;
    }


    public Response errorMethod(){
        return Response.error("服务调用异常","");
    }

}
