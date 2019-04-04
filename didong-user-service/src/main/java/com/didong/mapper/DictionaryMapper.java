package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.serviceEntity.Dictionary;

import java.util.List;


/**
 * PhoneBook 表数据库控制层接口
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {
    List<Dictionary> selectDictionary(Page<Dictionary> page, Dictionary dictionary);

}