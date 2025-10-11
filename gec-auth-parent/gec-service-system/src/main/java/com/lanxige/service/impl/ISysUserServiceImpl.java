package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysUserMapper;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.SysUserQueryVo;
import com.lanxige.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Nicke
 * @since 2025-10-11
 */
@Service
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Override
    public IPage<SysUser> selectPage(IPage<SysUser> iPage, SysUserQueryVo sysUserQueryVo) {
        return this.baseMapper.selectPage(iPage,sysUserQueryVo);
    }

    @Override
    public void updateStatusById(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        sysUser.setStatus(status);
        this.baseMapper.updateById(sysUser);
    }
}
