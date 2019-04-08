package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.serviceEntity.TbUserInterest;
import pojo.ResultData;

import java.util.List;

public interface UserInterestService extends IService<TbUserInterest> {

    ResultData saveUserInterest(List<TbUserInterest> interestList);
}
