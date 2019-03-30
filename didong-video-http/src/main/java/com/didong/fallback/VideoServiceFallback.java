package com.didong.fallback;

import com.didong.service.VideoService;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class VideoServiceFallback implements VideoService {
    @Override
    public String hello(@NotNull(message = "入参不能为空") String s) {
        return "熔断 出错 啦";
    }
}
