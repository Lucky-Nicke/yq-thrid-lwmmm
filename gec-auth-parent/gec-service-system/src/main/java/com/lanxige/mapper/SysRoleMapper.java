package com.lanxige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.SysRoleQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    public IPage<SysRole> selectPage(IPage<SysRole> page1,@Param("vo") SysRoleQueryVo roleQueryVo);
}
