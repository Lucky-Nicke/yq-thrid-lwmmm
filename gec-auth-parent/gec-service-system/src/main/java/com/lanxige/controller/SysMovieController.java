package com.lanxige.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.eurm.BusinessType;
import com.lanxige.model.system.SysMovie;
import com.lanxige.model.vo.SysMovieQueryVo;
import com.lanxige.service.SysMovieService;
import com.lanxige.util.Result;
import com.lanxige.utils.aop.OpenLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Api(tags = "影视管理控制器")
@RequestMapping("/admin/system/sysMovie")
@CrossOrigin
public class SysMovieController {
    @Autowired
    private SysMovieService sysMovieService;

    @ApiOperation("获取全部影视列表")
    @GetMapping("/findAll")
    public Result findAll() {
        List<SysMovie> list = this.sysMovieService.list();
        return Result.ok(list);
    }

    // 删除
    @OpenLog(title = "影视管理-删除",
            businessType = BusinessType.DELETE,
            requestMethod = "Delete")
    @ApiOperation("根据id去移除一个影视")
    @DeleteMapping("/removeMovie/{id}")
    public Result removeMovie(@PathVariable Long id) {
        boolean b = sysMovieService.deleteMovie(id);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 分页和条件查询
    @GetMapping("/{page}/{limit}")
    public Result findMovieByPageQuery(@PathVariable Long page,
                                       @PathVariable Long limit,
                                       SysMovieQueryVo sysMovieQueryVo) {
        IPage<SysMovie> p1 = new Page<SysMovie>(page, limit);
        p1 = this.sysMovieService.selectPage(p1, sysMovieQueryVo);
        return Result.ok(p1);
    }

    // 添加影视
    @OpenLog(title = "影视管理-添加",
            businessType = BusinessType.INSERT,
            requestMethod = "Post")
    @PostMapping("/addMovie")
    public Result addRole(@RequestBody SysMovie sysMovie) {
        boolean res = this.sysMovieService.saveMovieInfo(sysMovie);

        if (res) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //根据id 去得到当前影视
    @GetMapping("/findMovieById/{id}")
    public Result findMovieById(@PathVariable Long id) {
        SysMovie sysMovie = this.sysMovieService.getById(id);
        return Result.ok(sysMovie);
    }

    // 实现修改
    @OpenLog(title = "影视管理-修改",
            businessType = BusinessType.UPDATE,
            requestMethod = "Post")
    @PostMapping("/updateMovie")
    public Result updateRole(@RequestBody SysMovie sysMovie) {
        boolean b = this.sysMovieService.updateById(sysMovie);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 批量删除
    @OpenLog(title = "影视管理-批量删除",
            businessType = BusinessType.DELETE,
            requestMethod = "Delete")
    @DeleteMapping("/removeMovieByIds")
    public Result removeMovieByIds(@RequestBody List<Long> ids) {
        boolean b = sysMovieService.deleteMovieBatch(ids);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 播放视频根据id和播放秘钥
    @OpenLog(title = "影视管理-播放视频",
            businessType = BusinessType.OTHER,
            requestMethod = "Post")
    @ApiOperation("根据id获取播放凭证")
    @RequestMapping(value = "/getPlayAuth/{id}")
    public Result playVideoByAuth(@PathVariable Long id) {
        HashMap<String, Object> map = sysMovieService.getMovieId(id);
        return Result.ok(map);
    }
}
