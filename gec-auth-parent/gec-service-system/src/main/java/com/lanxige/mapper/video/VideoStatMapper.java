package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.Rsp.HotMovieRsp;
import com.lanxige.model.video.VideoStat;
import com.lanxige.model.vo.DayCountVo;

import java.util.List;

public interface VideoStatMapper extends BaseMapper<VideoStat> {

    /**
     * 查询播放趋势
     */
    List<DayCountVo> selectPlayTrend();

    /**
     * 查询总播放量
     */
    Integer selectTotalPlayCount();

    /**
     * 查询热门电影
     */
    List<HotMovieRsp> selectHotMovie();
}
