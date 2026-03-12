package com.lanxige.service.impl;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.Dto.VideoCommentDTO;
import com.lanxige.Req.SendCommentReq;
import com.lanxige.Req.SendDanMuReq;
import com.lanxige.Req.SendLikeReq;
import com.lanxige.Rsp.AllVideoInfoRsp;
import com.lanxige.mapper.system.SysMovieMapper;
import com.lanxige.mapper.system.SysUserMapper;
import com.lanxige.mapper.video.VideoCommentMapper;
import com.lanxige.mapper.video.VideoDanmuMapper;
import com.lanxige.mapper.video.VideoLikeLogMapper;
import com.lanxige.mapper.video.VideoStatMapper;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.video.VideoComment;
import com.lanxige.model.video.VideoDanmaku;
import com.lanxige.model.video.VideoLikeLog;
import com.lanxige.model.video.VideoStat;
import com.lanxige.model.vo.SysMovieQueryVo;
import com.lanxige.service.SysMovieService;
import com.lanxige.utils.VodTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {
    @Autowired
    private VideoStatMapper videoStatMapper;

    @Autowired
    private VodTemplate vodTemplate;

    @Autowired
    private VideoDanmuMapper videoDanmuMapper;

    @Autowired
    private VideoCommentMapper videoCommentMapper;

    @Autowired
    private SysMovieMapper sysMovieMapper;

    @Autowired
    private VideoLikeLogMapper videoLikeLogMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo) {
        return this.baseMapper.selectPage(p1, sysMovieQueryVo);
    }

    /**
     * 保存电影信息
     */
    @Override
    public boolean saveMovieInfo(SysMovie sysMovie) {
        log.info("保存电影信息，参数：{}", sysMovie);

        // 保存至video_stat表
        VideoStat videoStat = new VideoStat();
        videoStat.setId(sysMovie.getId());
        int insert = videoStatMapper.insert(videoStat);
        log.info("保存至video_stat表，结果：{}", insert);

        return this.save(sysMovie);
    }

    /**
     * 根据id获取视频信息
     *
     * @param id 视频id
     * @return 视频信息
     */
    @Override
    public HashMap<String, Object> getMovieId(Long id) {
        //1.根据id 获取 到 SysMovie
        SysMovie sysMovie = this.getById(id);
        //2. 从  SysMovie 中获取到 image  playId  Auth
        String image = sysMovie.getImage();

        String playId = sysMovie.getPlayId();
        System.out.println("playId = " + playId);
        // 根据playId 去阿里云服务器获取播放秘钥
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(playId);
        String playAuth = null;

        // 调用阿里云服务获取播放秘钥
        try {
            playAuth = this.vodTemplate.getVideoPlayAuth(playId).getPlayAuth();
        } catch (Exception e) {
            log.error("获取播放秘钥失败，异常信息：{}", e.getMessage());
            throw new RuntimeException(e);
        }

        // 封装map 集合
        HashMap<String, Object> map = new HashMap<>();
        // 分别封装三个参数  参数的key 要和前端对应
        map.put("image", image);
        map.put("playId", playId);
        map.put("playAuth", playAuth);
        log.info("获取播放秘钥成功，结果：{}", map);

        return map;
    }

    @Override
    public boolean deleteMovie(Long id) {
        boolean b = this.removeById(id);
        log.info("删除视频成功 {}", b);

        if (!b) {
            return false;
        }

        // 删除弹幕
        LambdaQueryWrapper<VideoDanmaku> danmakuWrapper = new LambdaQueryWrapper<>();
        danmakuWrapper.eq(VideoDanmaku::getVideoId, id);
        videoDanmuMapper.delete(danmakuWrapper);

        // 删除统计
        LambdaQueryWrapper<VideoStat> statWrapper = new LambdaQueryWrapper<>();
        statWrapper.eq(VideoStat::getVideoId, id);
        videoStatMapper.delete(statWrapper);

        // 删除评论
        LambdaQueryWrapper<VideoComment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(VideoComment::getVideoId, id);
        videoCommentMapper.delete(commentWrapper);

        return b;
    }

    @Override
    public boolean deleteMovieBatch(List<Long> ids) {
        boolean b = this.removeByIds(ids);
        log.info("删除视频成功{}", b);

        // 删除弹幕
        LambdaQueryWrapper<VideoDanmaku> danmakuWrapper = new LambdaQueryWrapper<>();
        danmakuWrapper.in(VideoDanmaku::getVideoId, ids);
        videoDanmuMapper.delete(danmakuWrapper);

        // 删除统计
        LambdaQueryWrapper<VideoStat> statWrapper = new LambdaQueryWrapper<>();
        statWrapper.in(VideoStat::getVideoId, ids);
        videoStatMapper.delete(statWrapper);

        // 删除评论
        LambdaQueryWrapper<VideoComment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.in(VideoComment::getVideoId, ids);
        videoCommentMapper.delete(commentWrapper);

        return b;
    }

    /**
     * 修改电影信息
     *
     * @param sysMovie 电影信息
     * @return 修改结果
     */
    @Override
    public boolean updateMovieInfo(SysMovie sysMovie) {
        boolean b = this.updateById(sysMovie);
        log.info("修改视频成功{}", b);

        return b;
    }

    @Override
    public List<AllVideoInfoRsp> getAllVideoInfo() {

        QueryWrapper<SysMovie> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0)
                .eq("is_approval", "1")
                .orderByDesc("create_time");

        List<SysMovie> movieList = sysMovieMapper.selectList(wrapper);

        return movieList.stream().map(movie -> {

            AllVideoInfoRsp rsp = new AllVideoInfoRsp();
            rsp.setId(movie.getId());
            rsp.setTitle(movie.getName());
            rsp.setAuthor(movie.getDirector());
            rsp.setCoverUrl(movie.getImage());
            rsp.setCategory(movie.getCid());

            // 目前没有播放量字段，先默认0
            rsp.setViews("0");

            return rsp;

        }).collect(Collectors.toList());
    }

    @Override
    public List<AllVideoInfoRsp> getHotVideoInfo() {
        return sysMovieMapper.selectHotVideos();
    }

    @Override
    public List<AllVideoInfoRsp> getHotWatchVideoInfo() {
        return sysMovieMapper.selectHotWatchVideos();
    }

    /**
     * 发送弹幕
     *
     * @param req 请求参数
     * @return 发送结果
     */
    @Override
    public int sendDanMu(SendDanMuReq req) {
        VideoDanmaku danmaku = new VideoDanmaku();

        // 视频ID
        danmaku.setVideoId(Long.valueOf(req.getVideoId()));

        // 用户ID（允许为空）
        if (req.getUserId() != null) {
            danmaku.setUserId(Long.valueOf(req.getUserId()));
        }

        // 内容
        danmaku.setContent(req.getContent());

        // 播放时间
        if (req.getTime() != null) {
            danmaku.setPlayTime(req.getTime());
        }

        // 默认颜色
        danmaku.setColor("#000000");

        return videoDanmuMapper.insert(danmaku);
    }

    /**
     * 发送点赞
     *
     * @param req 请求参数
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String sendLike(SendLikeReq req) {

        // 1. 查询点赞记录
        LambdaQueryWrapper<VideoLikeLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoLikeLog::getUserId, req.getUserId())
                .eq(VideoLikeLog::getVideoId, req.getVideoId())
                .eq(VideoLikeLog::getType, req.getLikeType())
                .eq(VideoLikeLog::getIsDeleted, 0)
                .last("limit 1");

        VideoLikeLog likeLog = videoLikeLogMapper.selectOne(wrapper);

        // 2. 已经点赞
        if (likeLog != null && likeLog.getStatus() == 1 && req.getLike()) {
            return "已经点过赞了";
        }

        // 3. 第一次点赞
        if (likeLog == null && req.getLike()) {
            VideoLikeLog log = new VideoLikeLog();
            log.setUserId(req.getUserId());
            log.setVideoId(req.getVideoId());
            log.setType(req.getLikeType());
            log.setStatus(1);
            videoLikeLogMapper.insert(log);

            // 更新点赞数
            if ("video".equalsIgnoreCase(req.getLikeType())) {
                videoStatMapper.update(null,
                        new LambdaUpdateWrapper<VideoStat>()
                                .eq(VideoStat::getVideoId, req.getVideoId())
                                .setSql("like_count = like_count + 1"));
            } else if ("comment".equalsIgnoreCase(req.getLikeType())) {
                videoCommentMapper.update(null,
                        new LambdaUpdateWrapper<VideoComment>()
                                .eq(VideoComment::getId, req.getCommentId())
                                .setSql("like_count = like_count + 1"));
            }

            return "点赞成功";
        }

        // 4. 取消点赞
        if (likeLog != null && !req.getLike()) {
            likeLog.setStatus(0);
            videoLikeLogMapper.updateById(likeLog);

            if ("video".equalsIgnoreCase(req.getLikeType())) {
                videoStatMapper.update(null,
                        new LambdaUpdateWrapper<VideoStat>()
                                .eq(VideoStat::getVideoId, req.getVideoId())
                                .setSql("like_count = GREATEST(like_count - 1,0)"));
            } else if ("comment".equalsIgnoreCase(req.getLikeType())) {
                videoCommentMapper.update(null,
                        new LambdaUpdateWrapper<VideoComment>()
                                .eq(VideoComment::getId, req.getVideoId()) // videoId字段存评论id
                                .setSql("like_count = GREATEST(like_count - 1,0)"));
            }

            return "取消点赞成功";
        }

        // 5. 重新点赞（之前取消过的）
        if (likeLog != null && req.getLike()) {
            likeLog.setStatus(1);
            videoLikeLogMapper.updateById(likeLog);

            if ("video".equalsIgnoreCase(req.getLikeType())) {
                videoStatMapper.update(null,
                        new LambdaUpdateWrapper<VideoStat>()
                                .eq(VideoStat::getVideoId, req.getVideoId())
                                .setSql("like_count = like_count + 1"));
            } else if ("comment".equalsIgnoreCase(req.getLikeType())) {
                // 目标评论ID
                Long commentId = req.getCommentId();

                if (req.getLike()) {
                    // 点赞
                    videoCommentMapper.update(null,
                            new LambdaUpdateWrapper<VideoComment>()
                                    .eq(VideoComment::getId, commentId)
                                    .setSql("like_count = like_count + 1"));
                }
            }

            return "点赞成功";
        }

        return "操作失败";
    }

    /**
     * 发送评论
     *
     * @param req 请求参数
     * @return 发送结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sendComment(SendCommentReq req) {

        VideoComment comment = new VideoComment();

        comment.setVideoId(req.getVideoId());
        comment.setUserId(req.getUserId());
        comment.setContent(req.getContent());

        // 一级评论
        if (req.getParentId() == null) {

            videoCommentMapper.insert(comment);

            // rootId = 自己
            comment.setRootId(comment.getId());
            videoCommentMapper.updateById(comment);

        } else {

            // 回复评论 / 楼中楼
            comment.setRootId(req.getCommentRootId());
            comment.setParentId(req.getParentId());

            videoCommentMapper.insert(comment);

            // 父评论回复数 +1
            videoCommentMapper.update(null,
                    new LambdaUpdateWrapper<VideoComment>()
                            .eq(VideoComment::getId, req.getParentId())
                            .setSql("reply_count = reply_count + 1"));
        }

        return 1;
    }

    /**
     * 获取视频评论
     *
     * @param videoId 视频ID
     * @return 评论列表
     */
    @Override
    public List<VideoCommentDTO> getVideoComment(Long videoId) {

        // 1 查询该视频所有评论
        List<VideoComment> comments = videoCommentMapper.selectList(
                new LambdaQueryWrapper<VideoComment>()
                        .eq(VideoComment::getVideoId, videoId)
                        .eq(VideoComment::getStatus, 1)
                        .eq(VideoComment::getIsDeleted, 0)
                        .orderByDesc(VideoComment::getCreateTime)
        );

        if (comments.isEmpty()) {
            return Collections.emptyList();
        }

        // 2 转DTO
        List<VideoCommentDTO> dtoList = comments.stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());

        // 3 按 parentId 分组
        Map<Long, List<VideoCommentDTO>> map = dtoList.stream()
                .filter(c -> c.getParentId() != null)
                .collect(Collectors.groupingBy(VideoCommentDTO::getParentId));

        // 4 组装评论树
        List<VideoCommentDTO> rootList = dtoList.stream()
                .filter(c -> c.getParentId() == null)
                .collect(Collectors.toList());

        for (VideoCommentDTO root : rootList) {
            buildChildren(root, map);
        }

        return rootList;
    }

    /**
     * 构建评论树
     *
     * @param parent 父节点
     * @param map    子节点
     */
    private void buildChildren(VideoCommentDTO parent,
                               Map<Long, List<VideoCommentDTO>> map) {

        List<VideoCommentDTO> children = map.get(parent.getId());

        if (children == null) {
            return;
        }

        parent.setChildren(children);

        for (VideoCommentDTO child : children) {
            buildChildren(child, map);
        }
    }

    /**
     * 转化DTO
     *
     * @param comment 评论
     * @return 点赞结果
     */
    private VideoCommentDTO convertDTO(VideoComment comment) {

        VideoCommentDTO dto = new VideoCommentDTO();

        dto.setId(comment.getId());
        dto.setParentId(comment.getParentId());
        dto.setRootId(comment.getRootId());
        dto.setContent(comment.getContent());
        dto.setLikeCount(comment.getLikeCount());
        dto.setReplyCount(comment.getReplyCount());

        dto.setCreateTime(comment.getCreateTime());

        SysUser sysUser = sysUserMapper.selectById(comment.getUserId());
        dto.setUserId(comment.getUserId().toString());
        dto.setAvatar(sysUser.getHeadUrl());

        return dto;
    }
}

