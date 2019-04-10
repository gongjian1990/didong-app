package com.didong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didong.dto.VideoInfoDTO;
import com.didong.serviceEntity.TbVideo;
import pojo.Response;
import pojo.ResultData;

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

    Response saveVideoback(TbVideo video, Integer personChkStatus, Integer videoUpDownStatus, String nickName);

    IPage<VideoInfoDTO>  getVideoInfo(VideoInfoDTO videoInfoDTO, Page<VideoInfoDTO> page);

    IPage<TbVideo> selectPageVideos(Page page);

    IPage<TbVideo> selectAllByPage20Videos(String pageNum);

    IPage<TbVideo> selectAllByPageAndCondition(TbVideo video,Page<TbVideo> page);

    ResultData saveVideo(TbVideo tbVideo) ;

}
