package com.lanxige.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysMenu;
import com.lanxige.model.vo.AssginMenuVo;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
    List<SysMenu> findNodes();
    void removeMenuById(Long id);
    List<SysMenu> findSysMenuByRoleId(Long roleId);
    void doAssign(AssginMenuVo assginMenuVo);
}
