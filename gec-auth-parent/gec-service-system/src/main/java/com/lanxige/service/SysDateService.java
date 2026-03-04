package com.lanxige.service;

import com.lanxige.Rsp.DataTrendRsp;

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
}
