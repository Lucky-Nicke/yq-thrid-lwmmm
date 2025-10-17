package com.lanxige.Filter;

import com.alibaba.fastjson.JSON;
import com.lanxige.util.JwtHelper;
import com.lanxige.util.ResponseUtil;
import com.lanxige.util.Result;
import com.lanxige.util.ResultCodeEnum;
import com.mysql.cj.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TokenAuthenticationFliter extends OncePerRequestFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFliter(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("uri:"+request.getRequestURI());
        if("/admin/system/index/login".equals(request.getRequestURI())
                ||"/admin/system/upload/uploadImage".equals(request.getRequestURI())
                ||"/admin/system/upload/uploadVideo".equals(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if(authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader("token");
        logger.info("token:"+token);
        if(!StringUtils.isNullOrEmpty(token)){
            String username = JwtHelper.getUsername(token);
            logger.info("username:"+username);
            if (!StringUtils.isNullOrEmpty(username)){
                String authoritiesString = (String) redisTemplate.opsForValue().get(username);
                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (Map map : mapList){
                    authorities.add(new SimpleGrantedAuthority((String) map.get("authority")));
                }
                return new UsernamePasswordAuthenticationToken(username,null,authorities);
            }
        }
        return null;
    }
}
