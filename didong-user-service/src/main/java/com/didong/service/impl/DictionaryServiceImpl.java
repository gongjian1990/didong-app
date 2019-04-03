package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.Dictionary;
import com.didong.mapper.DictionaryMapper;
import com.didong.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.util.List;

/**
 * PhoneBook 表数据服务层接口实现类
 */
@Service
@Slf4j
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {


    @Override
    public ResultData getDictionaryList(String optGroup) {
        ResultData resultData=new ResultData();
        JSONObject retJson=new JSONObject();
        Page<Dictionary> page=new Page(1,2);
        IPage<Dictionary> dictionary = baseMapper.selectPage(page,new QueryWrapper<Dictionary>()
                .eq("opt_group", optGroup));
        if(dictionary!=null){
            resultData.setCode(200);
            resultData.setMessage("success");
            retJson.put("interset",dictionary.getRecords());
            resultData.setResults(retJson);
            log.info("[字典获取响应结果] -- resultData:{}", resultData);
            return resultData;
        }else {
            resultData.setCode(500);
            resultData.setMessage("请先配置该字典信息");
            log.info("[字典获取响应结果] -- resultData:{}", resultData);
            return  resultData;
        }

    }
}