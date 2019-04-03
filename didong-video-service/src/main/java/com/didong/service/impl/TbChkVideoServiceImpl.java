package com.didong.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.mapper.video.TbChkVideoMapper;
import com.didong.service.ITbChkVideoService;
import com.didong.serviceEntity.TbChkVideo;
import com.didong.util.AliCheckUtils;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * <p>
 * 视频审核表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbChkVideoServiceImpl extends ServiceImpl<TbChkVideoMapper, TbChkVideo> implements ITbChkVideoService {

    @Override
    public int saveChkVideo(TbChkVideo tbChkVideo) {
        return baseMapper.insert(tbChkVideo);
    }

    /**
     * 视频内容检测（异步）
     *
     * @param url
     * @param tbChkVideo
     * @return
     * @throws UnsupportedEncodingException
     * @throws ClientException
     */
    @Override
    public String checkVideo(String url, TbChkVideo tbChkVideo) throws UnsupportedEncodingException, ClientException {
        ResultData resultData = new ResultData();
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        Map<String, Object> task = new LinkedHashMap<String, Object>();
        task.put("dataId", UUID.randomUUID().toString());
        task.put("url",url);

        tasks.add(task);
        /**
         * 设置要检测的场景, 计费是按照该处传递的场景进行
         * 视频默认1秒截取一帧，您可以自行控制截帧频率，收费按照视频的截帧数量以及每一帧的检测场景进行计费
         * 举例：1分钟的视频截帧60张，检测色情和暴恐涉政2个场景，收费按照60张暴恐+60张暴恐涉政进行计费
         * porn: porn表示色情场景检测,terrorism表示暴恐涉政场景检测
         */
        JSONObject data = new JSONObject();
        data.put("scenes", Arrays.asList("porn", "terrorism", "live"));
        data.put("tasks", tasks);
        /**
         * 若检测视频画面的同时需要检测语音是否有风险内容，传递下面的参数
         * 注意语音的计费是按照时长进行，即该视频的时长*语音反垃圾的单价
         */
        data.put("audioScenes", Arrays.asList("antispam"));
        JSONObject jsonObject = AliCheckUtils.checkVideo(data);
        if (200 == jsonObject.getInteger("code ")) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (Object object : jsonArray) {
                if (200 == ((JSONObject) object).getIntValue("code")) {
                    tbChkVideo.setTaskId(((JSONObject) object).getString("taskId"));
                }
            }
        } else {
            return "false";
        }
        baseMapper.update(tbChkVideo, new QueryWrapper<TbChkVideo>().eq("vedio_id", tbChkVideo.getVideoId()));
        return "success";
    }

    @Override
    public List<TbChkVideo> getWaitMachineChkVideoList() {
        return baseMapper.selectList(new QueryWrapper<TbChkVideo>().eq("machine_chk_status", 0));
    }

    @Override
    public int updateChkVideo(TbChkVideo tbChkVideo) {
        return baseMapper.update(tbChkVideo,new QueryWrapper<TbChkVideo>().eq("video_id", tbChkVideo.getVideoId()));
    }
}
