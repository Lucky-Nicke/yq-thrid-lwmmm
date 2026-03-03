package com.lanxige.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.Rsp.UserTrendRsp;
import com.lanxige.model.system.SysUser;

public interface SysUserDateService extends IService<SysUser> {
    /**
     * 根据用户id查询角色id
     *
     * @return 用户数据
     */
    UserTrendRsp getRoleIdsByUserId();
}
