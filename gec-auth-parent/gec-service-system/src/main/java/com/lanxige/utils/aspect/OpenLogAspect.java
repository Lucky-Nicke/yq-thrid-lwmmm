package com.lanxige.utils.aspect;

import com.lanxige.mapper.SysOpenLogMapper;
import com.lanxige.model.system.SysOpenLog;
import com.lanxige.utils.aop.OpenLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * &#064;Description:  日志记录切面
 */
@Aspect
@Component
public class OpenLogAspect {

    @Autowired
    private SysOpenLogMapper sysOpenLogMapper;

    @Around("@annotation(openLog)")
    public Object recordLog(ProceedingJoinPoint joinPoint, OpenLog openLog) throws Throwable {

        Object result = joinPoint.proceed();

        try {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                            .getRequest();

            SysOpenLog log = new SysOpenLog();

            // 模块标题
            log.setTitle(openLog.title());

            // 业务类型
            log.setBusinessType(openLog.businessType().getCode());

            // 请求方式（手写优先，否则自动获取）
            String method = openLog.requestMethod();
            if (method == null || method.isEmpty()) {
                method = request.getMethod();
            }
            log.setRequestMethod(method);

            // 请求URL
            log.setOperUrl(request.getRequestURI());

            // 操作人员（Spring Security）
            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            log.setOperName(username);

            // IP地址
            log.setOperIp(getIpAddr(request));

            log.setCreateTime(new Date());
            log.setIsDeleted(0);

            sysOpenLogMapper.insert(log);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取真实IP
     */
    private String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}