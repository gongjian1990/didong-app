package com.didong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbVideoShareMapper;
import com.didong.service.ITbVideoShareService;
import com.didong.serviceEntity.TbVideoShare;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频分享表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbVideoShareServiceImpl extends ServiceImpl<TbVideoShareMapper, TbVideoShare> implements ITbVideoShareService {

    @Override
    public Long getVideoShareNumByVideoId(Long videoId) {
       return Long.valueOf(baseMapper.selectCount(new QueryWrapper<TbVideoShare>().eq("video_id",videoId)));
    }
}
