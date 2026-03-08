package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lanxige.Dto.VideoCommentDTO;
import com.lanxige.Dto.VideoDanmakuDTO;
import com.lanxige.Rsp.DataTrendRsp;
import com.lanxige.Rsp.VideoDetailRsp;
import com.lanxige.mapper.system.SysMovieMapper;
import com.lanxige.mapper.system.SysUserMapper;
import com.lanxige.mapper.video.VideoCommentMapper;
import com.lanxige.mapper.video.VideoDanmuMapper;
import com.lanxige.mapper.video.VideoStatMapper;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.video.VideoComment;
import com.lanxige.model.video.VideoDanmaku;
import com.lanxige.model.video.VideoStat;
import com.lanxige.model.vo.DayCountVo;
import com.lanxige.service.SysDateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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

    @Autowired
    private VideoCommentMapper videoCommentMapper;

    /**
     * 根据用户id查询角色id
     *
     * @return 用户数据
     */
    @Override
    public DataTrendRsp getUserTrend() {
        List<DayCountVo> list = sysUserMapper.selectUserTrend();

        // 转成 Map<日期, 数量>
        Map<LocalDate, Integer> map = getDateIntegerMap(list);

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

    /**
     * 获得日期map
     *
     * @param list 日期集合
     * @return 日期map
     */
    private static Map<LocalDate, Integer> getDateIntegerMap(List<DayCountVo> list) {
        Map<LocalDate, Integer> map = list.stream()
                .collect(Collectors.toMap(
                        DayCountVo::getDay,
                        DayCountVo::getCount
                ));
        return map;
    }

    /**
     * 获取电影库存
     *
     * @return 电影库存
     */
    @Override
    public DataTrendRsp getMovieStock() {

        List<DayCountVo> list = sysMovieMapper.selectMovieTrend();

        // 转Map
        Map<LocalDate, Integer> map = getDateIntegerMap(list);

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

    /**
     * 获取电影播放量
     *
     * @return 电影播放量
     */
    @Override
    public DataTrendRsp getMoviePlays() {

        List<DayCountVo> list = videoStatMapper.selectPlayTrend();

        Map<LocalDate, Integer> map = getDateIntegerMap(list);

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

    /**
     * 获取弹幕量
     *
     * @return 弹幕量
     */
    @Override
    public DataTrendRsp getMovieDanmaku() {

        List<DayCountVo> list = videoDanmuMapper.selectDanmakuTrend();

        Map<LocalDate, Integer> map = getDateIntegerMap(list);

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

    /**
     * 查询视频详情
     *
     * @param videoId 视频id
     * @return 视频详情
     */
    @Override
    public VideoDetailRsp getSingelMovieDetail(String videoId) {
        VideoDetailRsp rsp = new VideoDetailRsp();

        // 1 查询视频基本信息
        SysMovie movie = sysMovieMapper.selectById(videoId);

        if (movie == null) {
            return rsp;
        }

        rsp.setVideoId(movie.getId());
        rsp.setName(movie.getName());
        rsp.setImage(movie.getImage());
        rsp.setCategory(movie.getCid());
        rsp.setPublishTime(movie.getCreateTime());

        // 2 查询统计数据
        QueryWrapper<VideoStat> statWrapper = new QueryWrapper<>();
        statWrapper.eq("id", videoId);

        VideoStat stat = videoStatMapper.selectOne(statWrapper);

        if (stat != null) {
            rsp.setPlayCount(stat.getPlayCount());
            rsp.setLikeCount(stat.getLikeCount());
        }

        // 3 查询弹幕
        QueryWrapper<VideoDanmaku> danmakuWrapper = new QueryWrapper<>();
        danmakuWrapper.eq("video_id", videoId);

        List<VideoDanmaku> danmakuList = videoDanmuMapper.selectList(danmakuWrapper);

        List<VideoDanmakuDTO> danmakuDTOList = new ArrayList<>();

        for (VideoDanmaku d : danmakuList) {

            VideoDanmakuDTO dto = new VideoDanmakuDTO();

            dto.setContent(d.getContent());
            dto.setPlayTime(d.getPlayTime());
            dto.setColor(d.getColor());
            dto.setCreateTime(d.getCreateTime());
            dto.setUserId(d.getUserId());

            danmakuDTOList.add(dto);
        }

        rsp.setDanmakuList(danmakuDTOList);
        rsp.setDanmakuCount(danmakuDTOList.size());

        // 4 查询评论
        QueryWrapper<VideoComment> commentWrapper = new QueryWrapper<>();
        commentWrapper.eq("video_id", videoId)
                .eq("status", 1)
                .orderByAsc("create_time");

        List<VideoComment> commentList = videoCommentMapper.selectList(commentWrapper);

        List<VideoCommentDTO> allComments = new ArrayList<>();

        // 先转换 DTO
        for (VideoComment c : commentList) {

            VideoCommentDTO dto = new VideoCommentDTO();

            dto.setId(c.getId());
            dto.setContent(c.getContent());
            dto.setLikeCount(c.getLikeCount());
            dto.setCreateTime(c.getCreateTime());
            dto.setParentId(c.getParentId());
            dto.setRootId(c.getRootId());

            // 这里用户名头像应该查用户表，这里先模拟
            dto.setUserId("用户:" + c.getUserId());
            log.info("用户:{}", c.getUserId());
            SysUser sysUser = sysUserMapper.selectById(c.getUserId());
            dto.setAvatar(sysUser.getHeadUrl());

            dto.setChildren(new ArrayList<>());

            allComments.add(dto);
        }

        Map<Long, VideoCommentDTO> commentMap = new HashMap<>();

        for (VideoCommentDTO c : allComments) {
            commentMap.put(c.getId(), c);
        }

        List<VideoCommentDTO> rootComments = new ArrayList<>();

        for (VideoCommentDTO comment : allComments) {

            // 一级评论
            if (comment.getParentId() == null) {

                rootComments.add(comment);
                continue;
            }

            // 找父评论
            VideoCommentDTO parent = commentMap.get(comment.getParentId());

            if (parent != null) {

                comment.setReplyToUser(parent.getUserId());

                parent.getChildren().add(comment);
            }
        }

        rsp.setCommentList(rootComments);
        rsp.setCommentCount(allComments.size());

        return rsp;
    }
}
