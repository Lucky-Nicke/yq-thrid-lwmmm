package com.lanxige.Filter;

import com.lanxige.util.JwtHelper;
import com.lanxige.util.ResponseUtil;
import com.lanxige.util.Result;
import com.lanxige.util.ResultCodeEnum;
import com.mysql.cj.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFliter extends OncePerRequestFilter {
    public TokenAuthenticationFliter(){

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("uri:"+request.getRequestURI());
        if("/admin/system/index/login".equals(request.getRequestURI())){
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
                return new UsernamePasswordAuthenticationToken(username,null,null);
            }
        }
        return null;
    }
}
