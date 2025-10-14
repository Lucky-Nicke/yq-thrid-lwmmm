package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Nicke
 * @since 2025-10-11
 */
public interface ISysUserService extends IService<SysUser> {
    IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo);
    void updateStatusById(Long id, Integer status);
    SysUser getUserInfoUserName(String username);
    Map<String, Object> getUserInfo(String username);
}
