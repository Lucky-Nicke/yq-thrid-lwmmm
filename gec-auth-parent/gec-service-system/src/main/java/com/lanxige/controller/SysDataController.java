package com.lanxige.controller;

import com.lanxige.service.SysDateService;
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
    private SysDateService sysDateService;

    @ApiOperation("查询站内用户数")
    @GetMapping("/userTrend")
    public Result selectUserPageByVo() {
        return Result.ok(sysDateService.getUserTrend());
    }

    @ApiOperation("查询站内视频数")
    @GetMapping("/movieStock")
    public Result selectMoviePageByVo() {
        return Result.ok(sysDateService.getMovieStock());
    }

    @ApiOperation("查询站内播放量数")
    @GetMapping("/moviePlays")
    public Result selectmoviePlays() {
        return Result.ok(sysDateService.getMoviePlays());
    }

    @ApiOperation("查询站内弹幕数")
    @GetMapping("/movieDanmaku")
    public Result selectmovieDanmaku() {
        return Result.ok(sysDateService.getMovieDanmaku());
    }
}
