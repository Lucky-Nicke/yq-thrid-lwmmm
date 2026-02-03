package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.Req.ChangePwdReq;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.SysUserQueryVo;

import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo);

    void updateStatusById(Long id, Integer status);

    SysUser getUserInfoUserName(String username);

    Map<String, Object> getUserInfo(String username);

    /**
     * 修改密码
     *
     * @param changePwdReq 修改密码请求
     * @return 修改结果
     */
    int updatePassword(ChangePwdReq changePwdReq);
}
