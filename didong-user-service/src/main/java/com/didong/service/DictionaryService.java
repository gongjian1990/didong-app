package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.entity.Dictionary;
import pojo.ResultData;

public interface DictionaryService extends IService<Dictionary> {

    ResultData getDictionaryList(String optGroup);
}
