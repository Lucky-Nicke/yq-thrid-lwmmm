package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysLoginLogMapper;
import com.lanxige.model.system.SysLoginLog;
import com.lanxige.service.SysLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {

        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);

        this.sysLoginLogMapper.insert(sysLoginLog);

    }

    @Override
    public IPage<SysLoginLog> getLoginLog(IPage<SysLoginLog> iPage) {
        return this.sysLoginLogMapper.selectPage(iPage,null);
    }
}
