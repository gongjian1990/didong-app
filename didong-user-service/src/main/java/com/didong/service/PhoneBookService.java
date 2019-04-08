package com.didong.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.didong.serviceEntity.TbPhoneBook;
import pojo.ResultData;

import java.util.List;

public interface PhoneBookService  extends IService<TbPhoneBook> {

    ResultData savePhoneBook(List<TbPhoneBook> list);
}
