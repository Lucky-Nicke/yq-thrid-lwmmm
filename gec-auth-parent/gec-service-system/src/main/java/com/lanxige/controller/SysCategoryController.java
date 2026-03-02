package com.lanxige.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanxige.eurm.BusinessType;
import com.lanxige.model.system.SysCategory;
import com.lanxige.model.vo.SysCategoryQueryVo;
import com.lanxige.service.SysCategoryService;
import com.lanxige.util.Result;
import com.lanxige.utils.aop.OpenLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "影视分类")
@RequestMapping("/admin/system/sysCategory")
@CrossOrigin
public class SysCategoryController {
    @Autowired
    private SysCategoryService sysCategoryService;

    @ApiOperation("获取全部分类")
    @GetMapping("/findAll")
    public Result findAll() {
        return Result.ok(sysCategoryService.list());
    }

    // 删除
    @OpenLog(title = "影视分类-单个删除",
            businessType = BusinessType.DELETE,
            requestMethod = "Delete")
    @PreAuthorize("hasAuthority('bnt.sysCategory.remove')")
    @ApiOperation("根据id去移除一个分类")
    @DeleteMapping("/removeCategory/{id}")
    public Result removeCategory(@PathVariable Long id) {
        boolean b = this.sysCategoryService.removeById(id);

        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 分页和条件查询
    @PreAuthorize("hasAuthority('bnt.sysCategory.list')")
    @ApiOperation("分页和条件查询")
    @GetMapping("/{page}/{limit}")
    public Result findCategoryByPageQuery(@PathVariable Long page,
                                          @PathVariable Long limit,
                                          SysCategoryQueryVo sysCategoryQueryVo) {
        //1.创建分页对象
        IPage<SysCategory> p1 = new Page<>(page, limit);

        //2.调用方法
        p1 = this.sysCategoryService.selectPage(p1, sysCategoryQueryVo);

        //3.返回
        return Result.ok(p1);
    }

    // 添加分类
    @OpenLog(title = "影视分类-添加",
            businessType = BusinessType.INSERT,
            requestMethod = "Post")
    @PreAuthorize("hasAuthority('bnt.sysCategory.add')")
    @ApiOperation("添加分类")
    @PostMapping("/addCategory")
    public Result addCategory(@Validated @RequestBody SysCategory sysCategory) {
        boolean res = this.sysCategoryService.save(sysCategory);
        if (res) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 根据id去得到当前分类
    @PreAuthorize("hasAuthority('bnt.sysCategory.update')")
    @ApiOperation("根据id去得到当前分类")
    @GetMapping("/findCategoryById/{id}")
    public Result findCategoryById(@PathVariable Long id) {
        return Result.ok(sysCategoryService.getById(id));
    }

    // 实现修改
    @OpenLog(title = "影视分类-修改",
            businessType = BusinessType.UPDATE,
            requestMethod = "Post")
    @PreAuthorize("hasAuthority('bnt.sysCategory.update')")
    @ApiOperation("修改分类")
    @PostMapping("/updateCategory")
    public Result updateCategory(@RequestBody SysCategory sysCategory) {
        boolean b = this.sysCategoryService.updateById(sysCategory);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 批量删除
    @OpenLog(title = "影视分类-批量删除",
            businessType = BusinessType.DELETE,
            requestMethod = "Delete")
    @PreAuthorize("hasAuthority('bnt.sysCategory.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("/removeCategoryByIds")
    public Result removeCategoryByIds(@RequestBody List<Long> ids) {
        boolean b = this.sysCategoryService.removeByIds(ids);

        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}
