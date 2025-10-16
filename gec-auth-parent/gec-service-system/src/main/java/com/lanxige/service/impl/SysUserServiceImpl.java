package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysUserMapper;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.RouterVo;
import com.lanxige.model.vo.SysUserQueryVo;
import com.lanxige.service.SysMenuService;
import com.lanxige.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final SysMenuService iSysMenuService;

    public SysUserServiceImpl(SysMenuService SysMenuService) {
        this.iSysMenuService = SysMenuService;
    }

    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo) {
        return this.baseMapper.selectPage(iPage,sysUserQueryVo);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        sysUser.setStatus(status);
        this.baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getUserInfoUserName(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> map = new HashMap<>();
        SysUser sysUser = this.getUserInfoUserName(username);

        List<RouterVo> routerVoList = iSysMenuService.findUserMenuList(sysUser.getId());
        List<String> permsList = iSysMenuService.findUserPermsList(sysUser.getId());

        //当前权限控制使用不到，我们暂时忽略
        map.put("name", sysUser.getName());
        map.put("avatar", sysUser.getHeadUrl());
        map.put("roles",  "[admin]");


        map.put("buttons", permsList);
        map.put("routers", routerVoList);
        return map;
    }
}
