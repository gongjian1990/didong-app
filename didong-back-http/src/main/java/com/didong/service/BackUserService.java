package com.didong.service;

import com.didong.dto.VideoInfoDTO;
import com.didong.fallback.BackUserServiceFallback;
import com.didong.httpEntity.TbUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Response;

import java.util.Map;

/**
 * @program: didong-app
 * @description: 后台用户服务
 * @author: moonLiker
 * @create: 2019-04-04
 */
@FeignClient(value = "user-service", fallback = BackUserServiceFallback.class)
public interface BackUserService {

    @RequestMapping(method = RequestMethod.POST,value = "userController/getUserInfo")
    TbUserInfo getUserInfo(VideoInfoDTO videoInfoDTO);

    @RequestMapping(value = "/userController/backLogin",method = RequestMethod.POST)
    Response backLogin(Map map);

}
