package com.lanxige.Filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanxige.model.vo.LoginVo;
import com.lanxige.service.SysLoginService;
import com.lanxige.util.*;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFliter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate redisTemplate;

    private SysLoginService sysLoginService;

    public TokenLoginFliter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate, SysLoginService sysLoginService) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login", "POST"));
        this.redisTemplate = redisTemplate;
        this.sysLoginService = sysLoginService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        LoginVo loginVo = new ObjectMapper().readValue(req.getInputStream(), LoginVo.class);

        // 缓存 username，供失败处理使用
        req.setAttribute("loginUsername", loginVo.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
        //调用认证管理器：通过getAuthenticationManager().authenticate(authentication)
        //触发认证，认证管理器会调用 UserDetailsService 的 loadUserByUsername 方法
        return this.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());
        sysLoginService.recordLoginLog(customUser.getUsername(), 0, IpUtil.getIpAddress(req), "登录成功");
        redisTemplate.opsForValue().set(customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        ResponseUtil.out(res, Result.ok(map));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException e) throws IOException {

        // 从 request 属性里取 username
        String username = (String) request.getAttribute("loginUsername");
        if (username == null) {
            username = "未知用户";
        }

        // 保存登录失败日志
        sysLoginService.recordLoginLog(username, 1, IpUtil.getIpAddress(request), e.getMessage());

        ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
    }
}
