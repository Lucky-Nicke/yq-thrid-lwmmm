package com.lanxige.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("aliyun.oss")
public class AliOssConfig {
    //区域
    private String endpoint;
    //访问id
    private String accessKeyId;
    //访问秘钥
    private String accessKeySecret;
    //桶名称
    private String bucketName;
    //访问URL
    private String url;
}
