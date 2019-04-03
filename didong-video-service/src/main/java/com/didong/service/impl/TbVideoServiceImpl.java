package com.didong.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.video.TbVideoMapper;
import com.didong.service.ITbChkVideoService;
import com.didong.service.ITbVideoService;
import com.didong.serviceEntity.TbChkVideo;
import com.didong.serviceEntity.TbVideo;
import com.didong.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
         * com.didong.mapper.video.TbVideoMapper.saveVideo
         */
        baseMapper.insert(video);
    }

    @Override
    public IPage<TbVideo> selectPageVideos(Page page) {

        //Page<TbVideo> pageRequest = new Page(StringUtils.hasText(pageNum) ? Integer.valueOf(pageNum) - 1 : 0, 10);
        return null;
    }

    @Override
    public IPage<TbVideo> selectAllByPage20Videos(String pageNum) {

        Page<TbVideo> page = new Page(StringUtils.hasText(pageNum) ? Integer.valueOf(pageNum) - 1 : 0, 5);

        IPage iPage = baseMapper.selectPage(page, new QueryWrapper<TbVideo>());

        return iPage;
    }

    @Override
    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video,Page<TbVideo> page) {
        Page<TbVideo> pages = new Page(1, 2);

        IPage<TbVideo> dictionary = baseMapper.selectPage(pages,new QueryWrapper<TbVideo>()
                .eq("user_id", "2"));
        List<TbVideo> list = baseMapper.selectAllByPageAndCondition(pages,video);
        return null;
    }

//    @Override
//    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page page) {
//        return baseMapper.selectAllByPageAndCondition(video,page);
//    }



}
