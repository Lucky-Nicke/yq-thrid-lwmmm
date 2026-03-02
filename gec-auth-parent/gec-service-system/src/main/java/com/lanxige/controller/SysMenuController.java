package com.lanxige.controller;

import com.lanxige.eurm.BusinessType;
import com.lanxige.model.system.SysMenu;
import com.lanxige.model.vo.AssginMenuVo;
import com.lanxige.service.SysMenuService;
import com.lanxige.util.Result;
import com.lanxige.utils.aop.OpenLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "系统菜单控制器")
@RestController
@RequestMapping("/admin/system/sysMenu")
@CrossOrigin
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> menuList = this.sysMenuService.findNodes();
        return Result.ok(menuList);
    }

    @OpenLog(title = "系统菜单-添加",
            businessType = BusinessType.INSERT,
            requestMethod = "Post")
    @PreAuthorize("hasAuthority('bnt.sysMenu.add')")
    @ApiOperation("添加菜单")
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu) {
        this.sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("根据id去查询菜单")
    @GetMapping("/findNodeById/{id}")
    public Result findNodeById(@PathVariable Long id) {
        SysMenu sysMenu = this.sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @OpenLog(title = "系统菜单-修改",
            businessType = BusinessType.UPDATE,
            requestMethod = "Post")
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation(value = "修改菜单")
    @PostMapping("/updateMenu")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @OpenLog(title = "系统菜单-删除",
            businessType = BusinessType.DELETE,
            requestMethod = "Delete")
    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeMenu/{id}")
    public Result removeMenu(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        List<SysMenu> list = this.sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @OpenLog(title = "系统菜单-给角色分配权限",
            businessType = BusinessType.UPDATE,
            requestMethod = "Post")
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }
}
