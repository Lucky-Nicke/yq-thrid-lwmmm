package com.lanxige.controller;

import com.lanxige.service.impl.SysUserDateServiceImpl;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "数据管理控制器")
@RequestMapping("/admin/system/sysDate")
@CrossOrigin
public class SysDataController {
    @Autowired
    private SysUserDateServiceImpl sysUserDateService;

    @ApiOperation("查询站内用户数")
    @GetMapping("/userTrend")
    public Result selectUserPageByVo() {
        return Result.ok(sysUserDateService.getRoleIdsByUserId());
    }
}
