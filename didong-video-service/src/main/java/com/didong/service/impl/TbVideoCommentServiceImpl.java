package com.didong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.TbVideoCommentMapper;
import com.didong.service.ITbVideoCommentService;
import com.didong.serviceEntity.TbVideoComment;
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
