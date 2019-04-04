package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.serviceEntity.UserInterest;
import pojo.ResultData;

import java.util.List;

public interface UserInterestService extends IService<UserInterest> {

    ResultData saveUserInterest(List<UserInterest> interestList);
}
