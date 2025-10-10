package com.lanxige.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.SysRoleQueryVo;
import com.lanxige.service.SysRoleService;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "角色管理控制器")
@RestController
@RequestMapping("/admin/system/sysRole")
@CrossOrigin
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    // 查询全部记录
    @ApiOperation("角色分页查询")
    @GetMapping("{page}/{limit}")
    public Result findAll(@PathVariable long page, @PathVariable long limit, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> page1 = new Page<>(page,limit);
        page1 = this.sysRoleService.selectPage(page1,sysRoleQueryVo);
        return Result.ok(page1);
    }

    @ApiOperation("添加角色")
    @PostMapping("/addRole")
    public Result addRole(@RequestBody SysRole sysRole){
        boolean flag = this.sysRoleService.save(sysRole);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation("修改角色信息")
    @GetMapping("/findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id){
        SysRole sysRole = this.sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    // 修改
    @ApiOperation("修改角色")
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody SysRole sysRole)
    {
        boolean isSuccess = this.sysRoleService.updateById(sysRole);
        if (isSuccess)
        {
            return Result.ok();
        }
        else
        {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody Long[] ids){
        boolean flag = this.sysRoleService.removeByIds(Arrays.asList(ids));
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }
}
