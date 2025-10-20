package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysOpenLogMapper;
import com.lanxige.model.system.SysOpenLog;
import com.lanxige.model.vo.SysOperLogQueryVo;
import com.lanxige.service.SysOpenLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysOpenLogServiceImpl extends ServiceImpl<SysOpenLogMapper, SysOpenLog> implements SysOpenLogService {
    @Autowired
    private SysOpenLogMapper sysOpenLogMapper;

    @Override
    public IPage<SysOpenLog> getLoginLog(IPage<SysOpenLog> iPage, SysOperLogQueryVo sysOperLogQueryVo) {
        return sysOpenLogMapper.selectPage(iPage,sysOperLogQueryVo);
    }
}
