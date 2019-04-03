package com.didong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.PhoneBook;
import com.didong.mapper.PhoneBookMapper;
import com.didong.service.PhoneBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.ResultData;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.List;

/**
 * PhoneBook 表数据服务层接口实现类
 */
@Service
@Slf4j
public class PhoneBookServiceImpl extends ServiceImpl<PhoneBookMapper, PhoneBook> implements PhoneBookService {

    @Autowired
    PhoneBookService phoneBookService;

    @Override
    public ResultData savePhoneBook(List<PhoneBook> list) {
        ResultData resultData=new ResultData();
        Date date=new Date();
        for(PhoneBook phoneBook:list){
            phoneBook.setCreateTime(date);
            phoneBook.setLastUpdateTime(date);
        }
        boolean result=phoneBookService.saveBatch(list);
        if(true==result){
            resultData.setCode(200);
            resultData.setMessage("通讯录导入成功");
        }
        log.info("通讯录保存响应结果:{}",resultData);
        return resultData;
    }
}