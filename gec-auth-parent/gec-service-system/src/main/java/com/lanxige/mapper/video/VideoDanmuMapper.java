package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.model.video.VideoDanmaku;
import com.lanxige.model.vo.DayCountVo;

import java.util.List;

/**
 * 视频弹幕
 */
public interface VideoDanmuMapper extends BaseMapper<VideoDanmaku> {

    /**
     * 查询弹幕趋势
     */
    List<DayCountVo> selectDanmakuTrend();

    /**
     * 查询弹幕总数
     */
    Integer selectTotalDanmakuCount();
}
