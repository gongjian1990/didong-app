package com.didong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.TbVideoComment;
import com.didong.mapper.video.TbVideoCommentMapper;
import com.didong.service.ITbVideoCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频评论表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbVideoCommentServiceImpl extends ServiceImpl<TbVideoCommentMapper, TbVideoComment> implements ITbVideoCommentService {

}
