package com.lanxige.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // 1. 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许前端的域名/端口（*表示允许所有，生产环境建议指定具体域名）
        config.addAllowedOrigin("http://192.168.66.19:8080");
        // 允许携带Cookie（如果需要）
        config.setAllowCredentials(true);
        // 允许所有请求方法（GET、POST、PUT等）
        config.addAllowedMethod("*");
        // 允许所有请求头
        config.addAllowedHeader("*");

        // 2. 配置生效的接口路径（/**表示所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 3. 返回过滤器
        return new CorsFilter(source);
    }
}
