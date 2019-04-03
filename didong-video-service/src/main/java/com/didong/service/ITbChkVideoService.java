package com.didong.service;


import com.aliyuncs.exceptions.ClientException;
import com.didong.serviceEntity.TbChkVideo;

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
public interface ITbChkVideoService {

    int saveChkVideo(TbChkVideo tbChkVideo);

    String checkVideo(String url,TbChkVideo tbChkVideo) throws UnsupportedEncodingException, ClientException;

    List<TbChkVideo> getWaitMachineChkVideoList();

    int updateChkVideo(TbChkVideo tbChkVideo);
}
