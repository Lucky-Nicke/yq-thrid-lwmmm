package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysMovieMapper;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.vo.SysMovieQueryVo;
import com.lanxige.service.SysMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMovieServiceImpl extends ServiceImpl<SysMovieMapper, SysMovie> implements SysMovieService {
    @Autowired
    private SysMovieMapper sysMovieMapper;

    @Override
    public IPage<SysMovie> selectPage(IPage<SysMovie> p1, SysMovieQueryVo sysMovieQueryVo) {
        return this.baseMapper.selectPage(p1,sysMovieQueryVo);
    }
}
