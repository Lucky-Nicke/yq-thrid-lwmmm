package com.lanxige.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanxige.mapper.SysRoleMapper;
import com.lanxige.mapper.SysUserRoleMapper;
import com.lanxige.model.system.SysRole;
import com.lanxige.model.system.SysUserRole;
import com.lanxige.model.vo.AssginRoleVo;
import com.lanxige.model.vo.SysRoleQueryVo;
import com.lanxige.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    public IPage<SysRole> selectPage(IPage<SysRole> page, SysRoleQueryVo roleQueryVo) {
        IPage<SysRole> iPage = this.baseMapper.selectPage(page,roleQueryVo);
        return iPage;
    }

    @Override
    public Map<String, Object> getRolesByUserId(Long userId) {
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        QueryWrapper<SysUserRole> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(qw);
        List<Long> userRoleIds = new ArrayList<>();
        for (SysUserRole sysUserRole : sysUserRoles) {
            userRoleIds.add(sysUserRole.getRoleId());
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",sysRoles);
        returnMap.put("userRoleIds",userRoleIds);
        return returnMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assginRoleVo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            if(roleId != null){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(assginRoleVo.getUserId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
}
