package com.didong.service;

import com.alibaba.fastjson.JSONObject;
import com.didong.fallback.UserServiceFallback;
import com.didong.httpEntity.TbPhoneBook;
import com.didong.httpEntity.TbUserInfo;
import com.didong.httpEntity.TbUserInterest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Response;
import pojo.ResultData;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user-service", fallback = UserServiceFallback.class)
public interface UserService {

    @RequestMapping(method = RequestMethod.POST,value = "loginController/checkWXAccessToken")
    Response checkWXAccessToken(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/checkSmsCode")
    String checkSmsCode(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/getSmsCode")
    JSONObject getSmsCode(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/wbLogin")
    String wbLogin(TbUserInfo userInfo);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/qqLogin")
    String qqLogin(Map<String,String> map);

    @RequestMapping(method = RequestMethod.POST,value = "loginController/getWXAccessToken")
    String getWXAccessToken(Map map);

    @RequestMapping(method = RequestMethod.POST,value = "userController/updateUserData")
    ResultData updateUserData(TbUserInfo userInfo);

    @RequestMapping(method = RequestMethod.POST,value = "phoneBookController/savePhoneBook")
    ResultData savePhoneBook(List<TbPhoneBook> list);

    @RequestMapping(method = RequestMethod.POST,value = "userInterestController/saveUserInterest")
    ResultData saveUserInterest(List<TbUserInterest> list);

    @RequestMapping(method = RequestMethod.POST,value = "dictionaryController/getDictionaryList")
    ResultData getDictionaryList(String optGroup);

}
