package com.didong.service;


import com.didong.serviceEntity.TbVideoReport;

/**
 * <p>
 * 用户关注表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface ITbVideoReportService {
    TbVideoReport getVideoReportByVideoId(String video_id);
}
