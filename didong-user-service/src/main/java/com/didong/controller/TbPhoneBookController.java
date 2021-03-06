package com.didong.controller;

import com.didong.service.TbPhoneBookService;
import com.didong.serviceEntity.TbPhoneBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.ResultData;

import java.util.List;


@RestController
@RequestMapping("/phoneBookController")
@Slf4j
public class TbPhoneBookController {

    @Autowired
    public TbPhoneBookService phoneBookService;


    @RequestMapping("/savePhoneBook")
    public ResultData savePhoneBook(@RequestBody List<TbPhoneBook> list) {
        return phoneBookService.savePhoneBook(list);
    }
}
