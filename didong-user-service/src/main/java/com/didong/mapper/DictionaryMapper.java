package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.serviceEntity.TbDictionary;

import java.util.List;


/**
 * PhoneBook 表数据库控制层接口
 */
public interface DictionaryMapper extends BaseMapper<TbDictionary> {
    List<TbDictionary> selectDictionary(Page<TbDictionary> page, TbDictionary dictionary);

}