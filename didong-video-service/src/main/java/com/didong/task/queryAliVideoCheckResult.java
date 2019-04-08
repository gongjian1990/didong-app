package com.didong.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.didong.service.ITbVideoChkService;
import com.didong.serviceEntity.TbVideoChk;
import com.didong.util.AliCheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: didong-app
 * @description: 定时查询视频内容检测结果
 * @author: moonLiker
 * @create: 2019-03-30
 */
@Component
@Slf4j
public class queryAliVideoCheckResult {
    @Autowired
    ITbVideoChkService iTbChkVideoService;

    /**
     * 定时任务方法，每1分钟查询视频审核进度
     */
//    @Scheduled(fixedDelay = 1000 * 60)
    public void getWaitMachineChkVideo() {
        try {
            List<String> taskList = new ArrayList<String>();
            List<TbVideoChk> tbChkVideoList = iTbChkVideoService.getWaitMachineChkVideoList();
            if (tbChkVideoList.size() > 0) {
                for (TbVideoChk tbChkVideo : tbChkVideoList) {
//                    taskList.add(tbChkVideo.getTaskId());
                    queryVideo(tbChkVideo.getTaskId(), tbChkVideo);

                }
            }
        } catch (Exception e) {
            log.error("定时任务-视频审核结果查询-发生异常", e);
        }
    }

    /**
     * 查询视频审核结果
     *
     * @param task
     * @param tbChkVideo
     */
    public void queryVideo(String task, TbVideoChk tbChkVideo) {
        try {
            List<String> list = new ArrayList<String>();
            list.add(task);
            JSONObject response = AliCheckUtils.queryVideo(list);
            JSONArray jsonArray = new JSONArray();
            if (200 == response.getInteger("code")) {
                JSONArray taskResults = response.getJSONArray("data");
                for (Object taskResult : taskResults) {
                    if (200 == ((JSONObject) taskResult).getInteger("code")) {
                        String taskId = ((JSONObject) taskResult).getString("taskId");
                        int taskCode = ((JSONObject) taskResult).getIntValue("code");
                        JSONArray sceneResults = ((JSONObject) taskResult).getJSONArray("results");
                        JSONArray audioResults = ((JSONObject) taskResult).getJSONArray("audioScanResults");
                        if (200 == taskCode) {
                            //获取视频检测结果
                            for (Object sceneResult : sceneResults) {
                                JSONObject sceneObject = new JSONObject();
                                String scene = ((JSONObject) sceneResult).getString("scene");
                                String suggestion = ((JSONObject) sceneResult).getString("suggestion");
                                String label = ((JSONObject) sceneResult).getString("label");
                                sceneObject.put("scene", scene);
                                sceneObject.put("suggestion", suggestion);
                                sceneObject.put("label", label);
                                jsonArray.add(sceneObject);
                            }
                            //获取音频检测结果
                            for (Object audioResult : audioResults) {
                                JSONObject audioObject = new JSONObject();
                                String scene = ((JSONObject) audioResult).getString("scene");
                                String suggestion = ((JSONObject) audioResult).getString("suggestion");
                                String label = ((JSONObject) audioResult).getString("label");
                                audioObject.put("scene", scene);
                                audioObject.put("suggestion", suggestion);
                                audioObject.put("label", label);
                                jsonArray.add(audioObject);
                            }
                            tbChkVideo.setMachineChkStatus(1);
                            tbChkVideo.setLastUpdateTime(new Date());
                            for (Object object : jsonArray) {
                                if (!"normal".equals(((JSONObject) object).getString("suggestion"))) {
                                    tbChkVideo.setMachineChkStatus(2);
                                    tbChkVideo.setMachineRefuseReason(((JSONObject) object).getString("label"));
                                    break;
                                }
                            }
                            iTbChkVideoService.updateChkVideo(tbChkVideo);

                        }
                    } else {
                        log.info("task process fail:{}", ((JSONObject) taskResult).getInteger("code"));
                    }
                }
            }
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
