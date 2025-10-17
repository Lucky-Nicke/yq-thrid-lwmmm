package com.lanxige.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanxige.model.system.SysCategory;
import com.lanxige.model.vo.SysCategoryQueryVo;

public interface SysCategoryService extends IService<SysCategory> {
    IPage<SysCategory> selectPage(IPage<SysCategory> p1, SysCategoryQueryVo sysCategoryQueryVo);

}
