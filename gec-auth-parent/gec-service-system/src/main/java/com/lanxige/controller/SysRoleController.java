package com.lanxige.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.AssginRoleVo;
import com.lanxige.model.vo.SysRoleQueryVo;
import com.lanxige.service.SysRoleService;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService SysRoleService;

    // 查询全部记录
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("角色分页查询")
    @GetMapping("{page}/{limit}")
    public Result findAll(@PathVariable long page, @PathVariable long limit, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> page1 = new Page<>(page,limit);
        page1 = this.SysRoleService.selectPage(page1,sysRoleQueryVo);
        return Result.ok(page1);
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        boolean flag = this.SysRoleService.save(sysRole);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id查找角色信息")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id){
        SysRole sysRole = this.SysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    // 修改
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole)
    {
        boolean isSuccess = this.SysRoleService.updateById(sysRole);
        if (isSuccess)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除角色")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids){
        boolean flag = this.SysRoleService.removeByIds(Arrays.asList(ids));
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        Map<String, Object> roleMap = SysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }


    @PreAuthorize("hasAuthority('bnt.sysUser.assignRole')")
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        SysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
}
