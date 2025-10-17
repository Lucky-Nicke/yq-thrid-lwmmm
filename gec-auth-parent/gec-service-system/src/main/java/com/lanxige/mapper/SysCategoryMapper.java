package com.lanxige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.model.system.SysCategory;
import com.lanxige.model.vo.SysCategoryQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysCategoryMapper extends BaseMapper<SysCategory> {
    IPage<SysCategory> selectPage(IPage<SysCategory> p1, @Param("vo") SysCategoryQueryVo sysCategoryQueryVo);
}
