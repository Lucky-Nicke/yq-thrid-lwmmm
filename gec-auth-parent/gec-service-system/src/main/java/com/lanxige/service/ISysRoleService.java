package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.AssginRoleVo;
import com.lanxige.model.vo.SysRoleQueryVo;

import java.util.Map;

public interface ISysRoleService extends IService<SysRole>{
    IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo roleQueryVo);
    Map<String, Object> getRolesByUserId(Long userId);
    void doAssign(AssginRoleVo assginRoleVo);
}
