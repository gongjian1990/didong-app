package com.didong.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didong.entity.TbVideo;
import com.didong.mapper.video.TbVideoMapper;
import com.didong.service.ITbVideoService;
import com.didong.util.AliCheckUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pojo.ResultData;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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

    @Resource
    public void setSqlSessionFactorOne(SqlSessionFactory sqlSessionFactory) {

    }

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

    @Override
    public void saveVideo(TbVideo video) {
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
    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video,Page page) {
        List<TbVideo>list = baseMapper.selectAllByPageAndCondition(video,page);

        System.out.println("list:"+list);
        return null;
    }

//    @Override
//    public IPage<TbVideo> selectAllByPageAndCondition(TbVideo video, Page page) {
//        return baseMapper.selectAllByPageAndCondition(video,page);
//    }



}
