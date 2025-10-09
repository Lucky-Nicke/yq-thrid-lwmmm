package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.SysRoleQueryVo;

public interface SysRoleService extends IService<SysRole>{
    IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo roleQueryVo);
}
