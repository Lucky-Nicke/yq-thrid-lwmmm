package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysRoleMapper;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.vo.SysRoleQueryVo;
import com.lanxige.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService{
    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> iPage = this.baseMapper.selectPage(page,roleQueryVo);
        return iPage;
    }
}
