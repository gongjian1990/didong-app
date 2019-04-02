package com.didong.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.TbChkVideo;
import com.didong.entity.TbVideo;
import com.didong.mapper.TbVideoMapper;
import com.didong.service.ITbChkVideoService;
import com.didong.service.ITbVideoService;
import com.didong.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbVideoServiceImpl extends ServiceImpl<TbVideoMapper, TbVideo> implements ITbVideoService {

    @Autowired
    ITbChkVideoService tbChkVideoService;


    @Override
    public ResultData saveVideo(TbVideo tbVideo) throws UnsupportedEncodingException, ClientException {
        ResultData resultData=new ResultData();
        //视频内容存储
        TbChkVideo tbChkVideo=new TbChkVideo();
        long video_id=IdGeneratorUtil.generateId();
        tbVideo.setVideoId(video_id);
        tbVideo.setUploadTime(new Date());
        baseMapper.insert(tbVideo);

        //视频审核存储
        tbChkVideo.setUserId(tbVideo.getUserId());
        tbChkVideo.setVideoId(video_id);
        tbChkVideo.setCreateTime(new Date());
        tbChkVideo.setLastUpdateTime(new Date());
        int i=tbChkVideoService.saveChkVideo(tbChkVideo);

        //视频检测（异步）
        String result=tbChkVideoService.checkVideo(tbVideo.getUrl(),tbChkVideo);
        if("success".equals(result)){
            resultData.setCode(200);
            resultData.setMessage("视频上传成功");
        }else {
            resultData.setCode(500);
            resultData.setMessage("视频上传成功,审核异常");
        }
        return resultData;
    }

    @Override
    public void saveVideoback(TbVideo video) {
        /**
         * com.didong.mapper.TbVideoMapper.saveVideo
         */
        baseMapper.insert(video);
    }

    @Override
    public IPage<TbVideo> selectPageVideos(Page page) {

        //Page<TbVideo> pageRequest = new Page(StringUtils.hasText(pageNum) ? Integer.valueOf(pageNum) - 1 : 0, 10);
        return null;
    }

}
