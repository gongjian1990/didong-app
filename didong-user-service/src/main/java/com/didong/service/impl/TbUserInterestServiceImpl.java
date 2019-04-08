package com.didong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbUserInterestMapper;
import com.didong.service.TbUserInterestService;
import com.didong.serviceEntity.TbUserInterest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.util.Date;
import java.util.List;

/**
 * User 表数据服务层接口实现类
 */
@Service
@Slf4j
public class TbUserInterestServiceImpl extends ServiceImpl<TbUserInterestMapper, TbUserInterest> implements TbUserInterestService {



    @Override
    public ResultData saveUserInterest(List<TbUserInterest> interestList) {

        ResultData resultData=new ResultData();
        Date date=new Date();
        for(TbUserInterest UserInterest:interestList){
            UserInterest.setCreateTime(date);
            UserInterest.setLastUpdateTime(date);
        }
        boolean result=saveBatch(interestList);

        if(true==result){
            resultData.setCode(200);
            resultData.setMessage("兴趣保存完成");
        }
        log.info("用户兴趣录入响应结果:{}",resultData);
        return resultData;
    }
}