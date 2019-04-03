package com.didong.service;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.entity.TbVideo;
import pojo.ResultData;
import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 视频表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface ITbVideoService {

//    ResultData checkVideo(String videoUrl) throws UnsupportedEncodingException, ClientException;

    void saveVideoback(TbVideo video) ;

    IPage<TbVideo> selectPageVideos(Page page);

    IPage<TbVideo> selectAllByPage20Videos(String pageNum);

    //IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page page);
    IPage<TbVideo> selectAllByPageAndCondition(TbVideo video,Page page);

    ResultData saveVideo(TbVideo tbVideo) throws UnsupportedEncodingException, ClientException;
}
