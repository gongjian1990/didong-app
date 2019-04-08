package com.didong.service;


import com.aliyuncs.exceptions.ClientException;
import com.didong.dto.VideoInfoDTO;
import com.didong.serviceEntity.TbUserInfo;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;

public interface UserInfoService {

    ResultData updateUserData(TbUserInfo userInfo) throws UnsupportedEncodingException, ClientException;

    VideoInfoDTO getVideoInfoDTOInfo(VideoInfoDTO videoInfoDTO) ;
}
