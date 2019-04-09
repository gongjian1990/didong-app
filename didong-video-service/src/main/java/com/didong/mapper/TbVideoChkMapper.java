package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didong.serviceEntity.TbVideoChk;

/**
 * <p>
 * 视频审核表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface TbVideoChkMapper extends BaseMapper<TbVideoChk> {

    void updateVideoUpDownStatus(long videoId,Integer chkVal);

}
