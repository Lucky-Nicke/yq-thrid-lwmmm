package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.system.SysMovieMapper;
import com.lanxige.mapper.video.VideoDanmuMapper;
import com.lanxige.mapper.video.VideoStatMapper;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.video.VideoStat;
import com.lanxige.model.vo.SysMovieQueryVo;
import com.lanxige.service.SysMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {
    @Autowired
    private VideoStatMapper videoStatMapper;

    @Autowired
    private VideoDanmuMapper videoDanmuMapper;

    @Override
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo) {
        return this.baseMapper.selectPage(p1,sysMovieQueryVo);
    }

    /**
     * 保存电影信息
     */
    @Override
    public boolean saveMovieInfo(SysMovie sysMovie) {
        log.info("保存电影信息，参数：{}",sysMovie);

        // 保存至video_stat表
        VideoStat videoStat = new VideoStat();
        videoStat.setVideoId(sysMovie.getPlayId());
        int insert = videoStatMapper.insert(videoStat);
        log.info("保存至video_stat表，结果：{}",insert);

        return this.save(sysMovie);
    }
}
