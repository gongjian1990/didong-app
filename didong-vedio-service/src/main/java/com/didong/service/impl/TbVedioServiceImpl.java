package com.didong.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.TbVedio;
import com.didong.mapper.TbVedioMapper;
import com.didong.service.ITbVedioService;
import com.didong.util.AliCheckUtils;
import org.springframework.stereotype.Service;
import pojo.ResultData;

import java.io.UnsupportedEncodingException;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-03-30
 */
@Service
public class TbVedioServiceImpl extends ServiceImpl<TbVedioMapper, TbVedio> implements ITbVedioService {

    @Override
    public ResultData checkVideo(String videoUrl) throws UnsupportedEncodingException, ClientException {
        ResultData resultData=new ResultData();
        JSONObject retJson = new JSONObject();
        JSONObject jsonObject= AliCheckUtils.checkVideo(new JSONObject());
        if(200==jsonObject.getInteger("code")){
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (Object object : jsonArray) {
                if(200==((JSONObject)object).getIntValue("code")){
                    resultData.setCode(200);
                    resultData.setMessage("视频审核成功");
                    retJson.put("taskId",((JSONObject)object).getString("taskId"));
                }
            }
        }else {
            resultData.setCode(500);
            resultData.setMessage("视频审核异常");
            return resultData;
        }
        return resultData;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, ClientException {
        AliCheckUtils.queryVideo(new JSONObject());
    }
}
