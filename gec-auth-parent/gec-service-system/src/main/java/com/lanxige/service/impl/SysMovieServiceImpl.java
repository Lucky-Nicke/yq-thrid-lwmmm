package com.lanxige.service.impl;

import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.system.SysMovieMapper;
import com.lanxige.mapper.video.VideoCommentMapper;
import com.lanxige.mapper.video.VideoDanmuMapper;
import com.lanxige.mapper.video.VideoStatMapper;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.video.VideoComment;
import com.lanxige.model.video.VideoDanmaku;
import com.lanxige.model.video.VideoStat;
import com.lanxige.model.vo.SysMovieQueryVo;
import com.lanxige.service.SysMovieService;
import com.lanxige.utils.VodTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
}
