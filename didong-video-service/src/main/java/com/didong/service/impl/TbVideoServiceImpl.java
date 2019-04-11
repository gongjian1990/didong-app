package com.didong.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.dto.VideoInfoAppDTO;
import com.didong.dto.VideoInfoDTO;
import com.didong.mapper.TbVideoMapper;
import com.didong.service.*;
import com.didong.serviceEntity.TbVideo;
import com.didong.serviceEntity.TbVideoChk;
import com.didong.serviceEntity.TbVideoComment;
import com.didong.serviceEntity.TbVideoReport;
import com.didong.util.IdGeneratorUtil;
import com.didong.util.VodUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.Response;
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
    ITbVideoChkService tbVideoChkService;

    @Autowired
    ITbVideoReportService iTbVideoReportService;

    @Autowired
    ITbVideoCommentService iTbVideoCommentService;

    @Autowired
    ITbVideoThumbsUpService iTbVideoThumbsUpService;

    @Autowired
    ITbVideoShareService iTbVideoShareService;

    @Override
    public ResultData saveVideo(TbVideo tbVideo) {
        ResultData resultData = new ResultData();
        //视频内容存储
        TbVideoChk tbChkVideo = new TbVideoChk();
        long video_id = IdGeneratorUtil.generateId();
        tbVideo.setVideoId(video_id);
        tbVideo.setUploadTime(new Date());
        baseMapper.insert(tbVideo);

        //视频审核存储
        tbChkVideo.setUserId(tbVideo.getUserId());
        tbChkVideo.setVideoId(video_id);
        tbChkVideo.setCreateTime(new Date());
        tbChkVideo.setLastUpdateTime(new Date());
        int i = tbVideoChkService.saveChkVideo(tbChkVideo);

        //获取视频url
        String videoUrl = VodUploadUtil.getVideoUrlByVideoId(tbVideo.getThirdVideoId());

        //视频检测（异步）
        String result = null;
        try {
            result = tbVideoChkService.checkVideo(videoUrl, tbChkVideo);
        } catch (UnsupportedEncodingException e) {
            resultData.setCode(500);
            resultData.setMessage("编码格式不支持");
        } catch (ClientException e) {
            resultData.setCode(500);
            resultData.setMessage("通讯异常");
        }
        if ("success".equals(result)) {
            resultData.setCode(200);
            resultData.setMessage("视频上传成功");
        } else {
            resultData.setCode(500);
            resultData.setMessage("视频上传成功,审核异常");
        }
        return resultData;
    }

    @Override
    public IPage<VideoInfoAppDTO> getNewestVideo(Long userId, Page<VideoInfoAppDTO> page, Date queryTime) {
        IPage<VideoInfoAppDTO> iPage = baseMapper.getNewestVideo(page, queryTime);
        for (VideoInfoAppDTO videoInfoAppDTO : iPage.getRecords()) {
            //获取视频评论内容以及评论数量
            List<TbVideoComment> list = iTbVideoCommentService.getVideoCommentNumByVideoId(videoInfoAppDTO.getVideoId());
            if(null!=list&list.size()>0){
                videoInfoAppDTO.setCommentList(list);
                videoInfoAppDTO.setCommentNums(Long.valueOf(list.size()));
            }else {
                videoInfoAppDTO.setCommentNums(0L);
            }
            //获取视频点赞数量
            Long thumbsUpNums=iTbVideoThumbsUpService.getVideoThumbsUpNumByVideoId(videoInfoAppDTO.getVideoId());
            videoInfoAppDTO.setThumbUpNums(thumbsUpNums);

            //获取视频分享数量
            Long shareNums=iTbVideoShareService.getVideoShareNumByVideoId(videoInfoAppDTO.getVideoId());
            videoInfoAppDTO.setShareNums(shareNums);
        }
        return iPage;
    }


    @Override
    public Response saveVideoback(TbVideo video, Integer personChkStatus, Integer videoUpDownStatus, String nickName) {
        ResultData resultData = new ResultData();
        //视频检测（异步）
        String result = "success";
        try {

            // 保存视频
            long videoId = IdGeneratorUtil.generateId();
            video.setVideoId(videoId);
            video.setUploadTime(new Date());
            baseMapper.insert(video);

            //视频审核存储
            TbVideoChk tbChkVideo = new TbVideoChk();
            tbChkVideo.setUserId(video.getUserId());
            tbChkVideo.setVideoId(videoId);
            tbChkVideo.setCreateTime(new Date());
            tbChkVideo.setLastUpdateTime(new Date());
            tbChkVideo.setPersonChkStatus(personChkStatus);
            tbChkVideo.setVideoUpDownStatus(videoUpDownStatus);
            tbVideoChkService.saveChkVideo(tbChkVideo);

            result = tbVideoChkService.checkVideo(video.getVideoUrl(), tbChkVideo);

            if ("success".equals(result)) {
                resultData.setCode(200);
                resultData.setMessage("视频上传成功");
            } else {
                resultData.setCode(500);
                resultData.setMessage("视频上传成功,审核异常");
            }
            return Response.success(resultData);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(resultData);
        }
    }

    /**
     * 获取视频信息
     *
     * @param videoInfoDTO
     * @param page
     * @return
     */
    @Override
    public IPage<VideoInfoDTO> getVideoInfo(VideoInfoDTO videoInfoDTO, Page<VideoInfoDTO> page) {
        //视频详细信息获取
        IPage<VideoInfoDTO> list = baseMapper.selectByVideoInfoDTO(page, videoInfoDTO);
        for (VideoInfoDTO infoDTO : list.getRecords()) {
            //视频审核信息获取
            if (0 == infoDTO.getMachineChkStatus()) {
                infoDTO.setCheckStatus(0);
            } else {
                infoDTO.setCheckStatus(1);
            }
            if (1 == infoDTO.getMachineChkStatus() && 1 == infoDTO.getPersonChkStatus()) {
                infoDTO.setCheckStatus(2);
            } else if (1 == infoDTO.getMachineChkStatus() && 2 == infoDTO.getPersonChkStatus()) {
                infoDTO.setCheckStatus(3);
            }
            //视频举报信息获取
            TbVideoReport tbVideoReport = iTbVideoReportService.getVideoReportByVideoId(infoDTO.getVideoId());
            if (tbVideoReport != null) {
                infoDTO.setHandelStatus(tbVideoReport.getReportStatus());
            } else {
                infoDTO.setHandelStatus(0);
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
    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page<TbVideo> page) {

        List<TbVideo> list = baseMapper.selectAllByPageAndCondition(page, video);
        return null;
    }

//    @Override
//    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page page) {
//        return baseMapper.selectAllByPageAndCondition(video,page);
//    }


}
