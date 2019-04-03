package com.didong.controller;

import com.didong.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

/**
 * @program: didong-app
 * @description: 字典
 * @author: moonLiker
 * @create: 2019-04-03
 */
@RestController
@RequestMapping("/dictionaryController")
@Slf4j
public class DictionaryController {
    @Autowired
    public DictionaryService dictionaryService;


    @RequestMapping("/getDictionaryList")
    public ResultData getDictionaryList(@RequestBody String optGroup)  {
        return dictionaryService.getDictionaryList(optGroup);
    }
}
