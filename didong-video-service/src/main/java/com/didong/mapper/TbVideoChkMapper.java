package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.serviceEntity.TbVideoChk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频审核表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface TbVideoChkMapper extends BaseMapper<TbVideoChk> {

    void updateVideoUpDownStatus(long videoId,Integer chkVal,long backUserId);

    List<TbVideoChk> selectByPage(Page<TbVideoChk> page,  @Param(value = "tbVideoChk")TbVideoChk tbVideoChk);

}
