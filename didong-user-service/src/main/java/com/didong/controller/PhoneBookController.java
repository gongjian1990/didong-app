package com.didong.controller;

import com.didong.service.PhoneBookService;
import com.didong.serviceEntity.PhoneBook;
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
public class PhoneBookController {

    @Autowired
    public PhoneBookService phoneBookService;


    @RequestMapping("/savePhoneBook")
    public ResultData savePhoneBook(@RequestBody List<PhoneBook> list) {
        return phoneBookService.savePhoneBook(list);
    }
}
