package com.lanxige.service.impl;

import com.lanxige.Rsp.DataTrendRsp;
import com.lanxige.mapper.system.SysMovieMapper;
import com.lanxige.mapper.system.SysUserMapper;
import com.lanxige.mapper.video.VideoDanmuMapper;
import com.lanxige.mapper.video.VideoStatMapper;
import com.lanxige.model.vo.DayCountVo;
import com.lanxige.service.SysDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysDateServiceImpl implements SysDateService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMovieMapper sysMovieMapper;

    @Autowired
    private VideoStatMapper videoStatMapper;

    @Autowired
    private VideoDanmuMapper videoDanmuMapper;

    /**
     * 根据用户id查询角色id
     *
     * @return 用户数据
     */
    @Override
    public DataTrendRsp getUserTrend() {
        List<DayCountVo> list = sysUserMapper.selectUserTrend();

        // 转成 Map<日期, 数量>
        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        DayCountVo::getDay,
                        DayCountVo::getCount
                ));

        List<Integer> actualData = new ArrayList<>();

        // 最近7天
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            actualData.add(map.getOrDefault(date, 0));
        }

        // 查询总用户数
        Integer totalUserCount = sysUserMapper.selectTotalUserCount();

        // 封装返回
        DataTrendRsp rsp = new DataTrendRsp();
        rsp.setActualData(actualData);
        rsp.setTotalUserCount(totalUserCount);

        return rsp;
    }

    @Override
    public DataTrendRsp getMovieStock() {

        List<DayCountVo> list = sysMovieMapper.selectMovieTrend();

        // 转Map
        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        DayCountVo::getDay,
                        DayCountVo::getCount
                ));

        List<Integer> result = new ArrayList<>();

        // 最近7天
        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            result.add(map.getOrDefault(day, 0));
        }

        // 总数
        Integer total = sysMovieMapper.selectTotalMovieCount();

        DataTrendRsp rsp = new DataTrendRsp();
        rsp.setActualData(result);
        rsp.setTotalUserCount(total);

        return rsp;
    }

    @Override
    public DataTrendRsp getMoviePlays() {

        List<DayCountVo> list = videoStatMapper.selectPlayTrend();

        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        DayCountVo::getDay,
                        DayCountVo::getCount
                ));

        List<Integer> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            result.add(map.getOrDefault(day, 0));
        }

        Integer total = videoStatMapper.selectTotalPlayCount();

        DataTrendRsp rsp = new DataTrendRsp();
        rsp.setActualData(result);
        rsp.setTotalUserCount(total);

        return rsp;
    }

    @Override
    public DataTrendRsp getMovieDanmaku() {

        List<DayCountVo> list = videoDanmuMapper.selectDanmakuTrend();

        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        DayCountVo::getDay,
                        DayCountVo::getCount
                ));

        List<Integer> result = new ArrayList<>();

        for (int i = 6; i >= 0; i--) {
            LocalDate day = LocalDate.now().minusDays(i);
            result.add(map.getOrDefault(day, 0));
        }

        Integer total = videoDanmuMapper.selectTotalDanmakuCount();

        DataTrendRsp rsp = new DataTrendRsp();
        rsp.setActualData(result);
        rsp.setTotalUserCount(total);

        return rsp;
    }
}
