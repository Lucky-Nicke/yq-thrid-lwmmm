package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysMenuMapper;
import com.lanxige.mapper.SysRoleMenuMapper;
import com.lanxige.model.system.SysMenu;
import com.lanxige.model.system.SysRoleMenu;
import com.lanxige.model.vo.AssginMenuVo;
import com.lanxige.model.vo.RouterVo;
import com.lanxige.service.ISysMenuService;
import com.lanxige.utils.MenuHelper;
import com.lanxige.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ISysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> menuList = this.baseMapper.selectList(null);
        List<SysMenu> resultList = MenuHelper.buildTree(menuList);

        return resultList;
    }

    @Override
    public void removeMenuById(Long id) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new RuntimeException("当前菜单存在子菜单，无法删除");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<SysMenu> menuList = baseMapper.selectList(queryWrapper);

        QueryWrapper<SysRoleMenu> wapper = new QueryWrapper<>();
        wapper.eq("role_id", roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(wapper);

        List<Long> roleMuneIds = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenuList) {
            roleMuneIds.add(sysRoleMenu.getMenuId());
        }

        for (SysMenu sysMenu : menuList) {
            if (roleMuneIds.contains(sysMenu.getId())) {
                sysMenu.setSelect(true);
            } else {
                sysMenu.setSelect(false);
            }
        }
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }


    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", assginMenuVo.getRoleId()));
        for (Long menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> findUserMenuList(Long userId) {
        List<SysMenu> menuList = null;

        if(userId.longValue() == 1){
            menuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));

        }else{
            menuList = baseMapper.findMenuListByUserId(userId);
        }

        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(menuList);
        List<RouterVo> routerVoList = RouterHelper.buildRouter(sysMenuTreeList);
        return routerVoList;
    }

    @Override
    public List<String> findUserPermsList(Long id) {
        return null;
    }
}
