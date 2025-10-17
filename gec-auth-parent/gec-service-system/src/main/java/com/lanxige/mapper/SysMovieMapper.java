package com.lanxige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.vo.SysMovieQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysMovieMapper extends BaseMapper<SysMovie> {
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, @Param("vo") SysMovieQueryVo sysMovieQueryVo);
}
