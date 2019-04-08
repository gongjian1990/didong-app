package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.serviceEntity.TbDictionary;
import pojo.ResultData;

public interface DictionaryService extends IService<TbDictionary> {

    ResultData getDictionaryList(String optGroup);
}
