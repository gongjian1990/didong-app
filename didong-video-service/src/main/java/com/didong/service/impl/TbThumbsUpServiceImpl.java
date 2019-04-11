package com.didong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbVideoThumbsUpMapper;
import com.didong.service.ITbVideoThumbsUpService;
import com.didong.serviceEntity.TbVideoThumbsUp;
import org.springframework.stereotype.Service;
import sun.util.resources.ga.LocaleNames_ga;

/**
 * <p>
 * 视频点赞表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbThumbsUpServiceImpl extends ServiceImpl<TbVideoThumbsUpMapper, TbVideoThumbsUp> implements ITbVideoThumbsUpService {

    @Override
    public Long getVideoThumbsUpNumByVideoId(Long videoId) {
        return Long.valueOf(baseMapper.selectCount(new QueryWrapper<TbVideoThumbsUp>().eq("video_id",videoId)));
    }
}
