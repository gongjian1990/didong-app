package com.didong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didong.dto.VideoInfoDTO;
import com.didong.serviceEntity.TbUserInfo;
import org.apache.ibatis.annotations.Param;


/**
 * User 表数据库控制层接口
 */
public interface TbUserInfoMapper extends BaseMapper<TbUserInfo> {

    TbUserInfo selectUserInfoByPhoneAndLoginType(String phone,String loginType);

    VideoInfoDTO getVideoInfoDTOInfo(@Param(value = "infoDto") VideoInfoDTO videoInfoDTO);

}