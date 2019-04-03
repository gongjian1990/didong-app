package com.didong.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.didong.entity.PhoneBook;
import com.didong.entity.UserInfo;
import com.didong.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 字典获取
     * @param optGroup
     * @return
     */
    @RequestMapping("/getIntersetList")
    public String getDictionaryList(@NotBlank(message = "组别不能为空") @RequestParam("optGroup") String optGroup){
        log.info("[字典获取] -- optGroup:{}", optGroup);
        ResultData result = userService.getDictionaryList(optGroup);
        log.info("[字典获取响应结果] -- result:{}", result);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));
    }


    /**
     * 保存用户通讯录
     * @param list
     * @return
     */
    @RequestMapping("/savePhoneBook")
    public String savePhoneBook(@RequestBody List<PhoneBook> list){
        log.info("[通讯录保存] -- list:{}", list.toString());
        ResultData result = userService.savePhoneBook(list);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));
    }

    /**
     *保存用户兴趣
     */
    @RequestMapping("/saveUserInterest")
    public String saveUserInterest(@RequestBody List<String> interestList){
        log.info("[用户兴趣保存] -- interestList:{}", interestList.toString());
        ResultData result = userService.saveUserInterest(interestList);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));
    }

    /**
    /**
     * 新用户个人资料完善
     * @param userInfo
     * @return
     */
    @RequestMapping("/updateUserData")
    public String updateUserData(UserInfo userInfo){
        log.info("[新用户个人资料完善] -- userInfo:{}", userInfo);
        ResultData result = userService.updateUserData(userInfo);
        if(!result.getCode().equals(200)){
            return JSON.toJSONString(Response.error(result));
        }
        return JSON.toJSONString(Response.success(result));

    }

}
