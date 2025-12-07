package com.lanxige.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.model.system.SysLoginLog;
import com.lanxige.model.vo.SysLoginLogQueryVo;
import com.lanxige.service.impl.SysLoginServiceImpl;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "系统管理-登录日志")
@RequestMapping("/admin/system/record")
@CrossOrigin
public class RecordLogController {
    @Autowired
    private SysLoginServiceImpl sysLoginService;

    @ApiOperation("查询登录记录log")
    @GetMapping("/{page}/{limit}")
    public Result getRecordLog(@PathVariable Long page, @PathVariable Long limit, SysLoginLogQueryVo sysLoginLogQueryVo){
        IPage<SysLoginLog> iPage = new Page<>(page,limit);
        iPage = this.sysLoginService.getLoginLog(iPage,sysLoginLogQueryVo);
        return Result.ok(iPage);
    }

    @ApiOperation("删除登录记录")
    @PostMapping("/del/{id}")
    public Result delRecordLog(@PathVariable Long id){
        boolean b = this.sysLoginService.removeById(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();

        }
    }

    @ApiOperation("批量删除登录记录")
    @PostMapping("/batchdel/{ids}")
    public Result batchDelRecordLog(@PathVariable List<Long> ids){
        boolean b = this.sysLoginService.removeByIds(ids);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();

        }
    }
}
