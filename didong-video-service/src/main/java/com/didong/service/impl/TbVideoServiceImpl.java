package com.didong.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.dto.VideoInfoDTO;
import com.didong.enums.VideoCheckStatusEnum;
import com.didong.mapper.TbVideoMapper;
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
        String result=tbChkVideoService.checkVideo(tbVideo.getVideoUrl(),tbChkVideo);
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
         * TbVideoMapper.saveVideo
         */
        baseMapper.insert(video);
    }

    /**
     * 获取视频信息
     * @param videoInfoDTO
     * @param page
     * @return
     */
    @Override
    public List<VideoInfoDTO> getVideoInfo(VideoInfoDTO videoInfoDTO, Page<VideoInfoDTO> page) {
        List<VideoInfoDTO> list=baseMapper.selectByVideoInfoDTO(page,videoInfoDTO);
        for(VideoInfoDTO infoDTO:list){
            TbChkVideo tbChkVideo=tbChkVideoService.getChkVideoInfoByVideoId(infoDTO.getVideoId());
            infoDTO.setUpDownStatus(tbChkVideo.getVedioUpDownStatus());
            if(0==tbChkVideo.getMachineChkStatus()){
                infoDTO.setCheckStatus(0);
            }else {
                infoDTO.setCheckStatus(1);
            }
            if(1==tbChkVideo.getMachineChkStatus()&&1==tbChkVideo.getPersonChkStatus()){
                infoDTO.setCheckStatus(2);
            }else if(1==tbChkVideo.getMachineChkStatus()&&2==tbChkVideo.getPersonChkStatus()){
                infoDTO.setCheckStatus(3);
            }

        }
        return list;
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

        List<TbVideo> list = baseMapper.selectAllByPageAndCondition(page,video);
        return null;
    }

//    @Override
//    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page page) {
//        return baseMapper.selectAllByPageAndCondition(video,page);
//    }



}
