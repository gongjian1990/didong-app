package com.didong.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: didong-app
 * @description: 定时查询视频内容检测结果
 * @author: moonLiker
 * @create: 2019-03-30
 */
@Component
@Slf4j
public class queryAliVideoCheckResult {
    /**
     * 定时任务方法，每1分钟查询视频审核进度
     */
    @Scheduled(fixedDelay = 1000 * 60)
    public void queryVideo(){

    }

}
