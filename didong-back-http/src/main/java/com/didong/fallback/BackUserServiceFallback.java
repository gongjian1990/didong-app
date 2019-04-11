package com.didong.fallback;

import com.didong.dto.VideoInfoDTO;
import com.didong.httpEntity.TbUserInfo;
import com.didong.service.BackUserService;
import org.springframework.stereotype.Component;
import pojo.Response;
import pojo.ResultData;

import java.util.Map;

/**
 * @program: didong-app
 * @description: 后台用户服务异常处理
 * @author: moonLiker
 * @create: 2019-04-04
 */
@Component
public class BackUserServiceFallback implements BackUserService {


    @Override
    public TbUserInfo getUserInfo(VideoInfoDTO videoInfoDTO) {
        return null;
    }

    @Override
    public Response backLogin(Map map) {
        return Response.error(new ResultData(500,"登录失败",null));
    }
}
