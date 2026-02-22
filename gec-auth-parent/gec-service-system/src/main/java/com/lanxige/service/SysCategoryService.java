package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysCategory;
import com.lanxige.model.vo.SysCategoryQueryVo;

public interface SysCategoryService extends IService<SysCategory> {
    /**
     * 分页查询
     *
     * @param p1                 分页对象
     * @param sysCategoryQueryVo 查询条件
     * @return 分页结果
     */
    IPage<SysCategory> selectPage(IPage<SysCategory> p1, SysCategoryQueryVo sysCategoryQueryVo);
}
