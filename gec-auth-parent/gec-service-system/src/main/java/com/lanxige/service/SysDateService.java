package com.lanxige.service;

import com.lanxige.Rsp.DataTrendRsp;
import com.lanxige.Rsp.VideoDetailRsp;

public interface SysDateService {
    /**
     * 根据用户id查询角色id
     *
     * @return 用户数据
     */
    DataTrendRsp getUserTrend();

    /**
     * 根据用户id查询角色id
     *
     * @return 角色数据
     */
    DataTrendRsp getMovieStock();

    /**
     * 根据用户id查询角色id
     *
     * @return 角色数据
     */
    DataTrendRsp getMoviePlays();

    /**
     * 根据用户id查询角色id
     *
     * @return 角色数据
     */
    DataTrendRsp getMovieDanmaku();

    /**
     * 查询视频详情
     * @param id
     * @return
     */
    VideoDetailRsp getSingelMovieDetail(String id);

    /**
     * 删除弹幕
     *
     * @param danmakuId 弹幕id
     * @return 删除结果
     */
    boolean removeDanmaku(String danmakuId);

    /**
     * 删除评论
     *
     * @param commentId 评论id
     * @return 删除结果
     */
    boolean removeComment(String commentId);
}
