package com.lanxige.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.Rsp.AllVideoInfoRsp;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.vo.DayCountVo;
import com.lanxige.model.vo.SysMovieQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMovieMapper extends BaseMapper<SysMovie> {
    IPage<SysMovie> selectPage(IPage<SysMovie> p1, @Param("vo") SysMovieQueryVo sysMovieQueryVo);

    /**
     * 查询电影趋势
     */
    List<DayCountVo> selectMovieTrend();

    /**
     * 查询电影总数
     */
    Integer selectTotalMovieCount();

    List<AllVideoInfoRsp> selectHotVideos();

    List<AllVideoInfoRsp> selectHotWatchVideos();
}
