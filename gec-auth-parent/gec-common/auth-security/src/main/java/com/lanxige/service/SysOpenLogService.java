package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysOpenLog;
import com.lanxige.model.vo.SysOperLogQueryVo;

public interface SysOpenLogService extends IService<SysOpenLog> {
    IPage<SysOpenLog> getLoginLog(IPage<SysOpenLog> iPage, SysOperLogQueryVo sysOperLogQueryVo);
}
