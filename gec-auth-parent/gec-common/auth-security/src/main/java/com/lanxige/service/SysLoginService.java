package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysLoginLog;

public interface SysLoginService extends IService<SysLoginLog> {
    void recordLoginLog(String username, Integer status, String ipaddr, String message);
    IPage<SysLoginLog> getLoginLog(IPage<SysLoginLog> iPage);
}
