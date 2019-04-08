package com.didong.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbDictionaryMapper;
import com.didong.service.TbDictionaryService;
import com.didong.serviceEntity.TbDictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pojo.ResultData;

/**
 * PhoneBook 表数据服务层接口实现类
 */
@Service
@Slf4j
public class TbDictionaryServiceImpl extends ServiceImpl<TbDictionaryMapper, TbDictionary> implements TbDictionaryService {


    @Override
    public ResultData getDictionaryList(String optGroup) {
        ResultData resultData=new ResultData();
        JSONObject retJson=new JSONObject();
        Page<TbDictionary> page=new Page(1,2);
//        List<Dictionary> list=baseMapper.selectDictionary(page,new Dictionary());

        IPage<TbDictionary> dictionary = baseMapper.selectPage(page,new QueryWrapper<TbDictionary>()
                .eq("opt_group", optGroup));
        if(dictionary!=null){
            resultData.setCode(200);
            resultData.setMessage("success");
                retJson.put("intersets",dictionary.getRecords());
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