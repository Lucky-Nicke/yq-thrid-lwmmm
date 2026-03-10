package com.lanxige.controller;

import com.lanxige.Rsp.VideoDetailRsp;
import com.lanxige.service.SysDateService;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("查询视频详情")
    @GetMapping("/singelMovieDetail")
    public Result getMovieListInfo(@RequestParam String id) {
        VideoDetailRsp rsp = sysDateService.getSingelMovieDetail(id);

        return Result.ok(rsp);
    }

    @ApiOperation("删除弹幕")
    @DeleteMapping("/deleteDanmaku")
    public Result removeDanmaku(@RequestParam String danmakuId) {
        boolean rsp = sysDateService.removeDanmaku(danmakuId);

        if (rsp){
            return Result.ok(rsp);
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/deleteComment")
    public Result removeComment(@RequestParam String commentId) {
        boolean rsp = sysDateService.removeComment(commentId);

        if (rsp){
            return Result.ok(rsp);
        }else {
            return Result.fail("删除失败");
        }
    }

    @ApiOperation("查询站内热门视频")
    @GetMapping("/getHotMovie")
    public Result getHotMovie() {
        return Result.ok(sysDateService.getHotMovie());
    }

    @ApiOperation("查询站最新评论")
    @GetMapping("/getNewComment")
    public Result getNewComment() {
        return Result.ok(sysDateService.getNewComment());
    }
}
