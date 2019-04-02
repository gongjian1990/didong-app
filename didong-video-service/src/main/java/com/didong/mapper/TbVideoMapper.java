package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.entity.TbVideo;

/**
 * <p>
 * 视频表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface TbVideoMapper extends BaseMapper<TbVideo> {

    void saveVideo(TbVideo video);

    IPage<TbVideo> selectPageVideos1(Page page);

}
