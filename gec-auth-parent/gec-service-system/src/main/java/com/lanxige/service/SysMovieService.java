package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.Rsp.AllVideoInfoRsp;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.vo.SysMovieQueryVo;

import java.util.HashMap;
import java.util.List;

public interface SysMovieService extends IService<SysMovie> {
    IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo);

    /**
     * 保存电影信息
     */
    boolean saveMovieInfo(SysMovie sysMovie);

    /**
     * 根据id查询电影信息
     *
     * @param id 电影id
     * @return 电影信息
     */
    HashMap<String, Object> getMovieId(Long id);

    /**
     * 根据id删除电影信息
     *
     * @param id 电影id
     * @return 删除结果
     */
    boolean deleteMovie(Long id);

    /**
     * 批量删除电影信息
     *
     * @param id 电影id列表
     * @return 删除结果
     */
    boolean deleteMovieBatch(List<Long> id);

    /**
     * 修改电影信息
     *
     * @param sysMovie 电影信息
     * @return 修改结果
     */
    boolean updateMovieInfo(SysMovie sysMovie);

    List<AllVideoInfoRsp> getAllVideoInfo();

    List<AllVideoInfoRsp> getHotVideoInfo();

    List<AllVideoInfoRsp> getHotWatchVideoInfo();
}

