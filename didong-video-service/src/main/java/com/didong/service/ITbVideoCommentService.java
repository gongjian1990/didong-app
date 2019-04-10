package com.didong.service;


import com.didong.serviceEntity.TbVideoComment;

import java.util.List;

/**
 * <p>
 * 视频评论表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
public interface ITbVideoCommentService  {
    List<TbVideoComment> getVideoCommentNumByVideoId(Long videoId);
}
