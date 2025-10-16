package com.lanxige.service.impl;

import com.lanxige.model.system.SysUser;
import com.lanxige.service.ISysUserService;
import com.lanxige.util.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser sysUser = sysUserService.getUserInfoUserName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 检查用户状态
        if (sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("用户已禁用");
        }
        // 返回用户详情（包含用户名、密码、权限等）
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
