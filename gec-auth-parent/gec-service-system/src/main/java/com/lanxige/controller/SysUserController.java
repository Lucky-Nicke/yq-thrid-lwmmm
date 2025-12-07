package com.lanxige.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.SysUserQueryVo;
import com.lanxige.service.SysUserService;
import com.lanxige.util.MD5Helper;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "用户管理控制器")
@RequestMapping("/admin/system/sysUser")
@CrossOrigin
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result selectUserPageByVo(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo sysUserQueryVo) {
        IPage<SysUser> iPage = new Page<>(page, limit);
        iPage = this.sysUserService.selectPage(iPage, sysUserQueryVo);
        return Result.ok(iPage);
    }

    // 添加用户
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody SysUser sysUser) {
        //MD5加密密码
        String passwordWithMD5 = MD5Helper.md5(sysUser.getPassword());
        sysUser.setPassword(passwordWithMD5);

        boolean b = this.sysUserService.save(sysUser);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 根据id 去获取一个用户
    @ApiOperation("根据id查询用户")
    @GetMapping("/findUserById/{id}")
    public Result findUserById(@PathVariable Long id) {
        SysUser sysUser = this.sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("修改用户信息")
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody SysUser sysUser) {
        boolean b = this.sysUserService.updateById(sysUser);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("删除用户")
    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUser(@PathVariable Long id) {
        boolean b = this.sysUserService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();

        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation("批量删除用户")
    @DeleteMapping("/batchDeleteUser")
    public Result batchDeleteUser(@RequestBody List<Long> ids) {
        boolean b = this.sysUserService.removeByIds(ids);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();

        }
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation("更改用户状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        this.sysUserService.updateStatusById(id, status);
        return Result.ok();
    }
}

