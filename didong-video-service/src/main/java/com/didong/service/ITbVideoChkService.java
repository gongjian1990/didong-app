package com.didong.service;


import com.aliyuncs.exceptions.ClientException;
import com.didong.serviceEntity.TbVideoChk;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 视频审核表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface ITbVideoChkService {

    int saveChkVideo(TbVideoChk tbChkVideo);

    String checkVideo(String url,TbVideoChk tbChkVideo) throws UnsupportedEncodingException, ClientException;

    List<TbVideoChk> getWaitMachineChkVideoList();

    int updateChkVideo(TbVideoChk tbChkVideo);

    TbVideoChk getChkVideoInfoByVideoId(String videoId);
}
