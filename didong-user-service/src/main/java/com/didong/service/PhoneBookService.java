package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.entity.PhoneBook;
import pojo.ResultData;

import java.util.List;

public interface PhoneBookService  extends IService<PhoneBook> {

    ResultData savePhoneBook(List<PhoneBook> list);
}
