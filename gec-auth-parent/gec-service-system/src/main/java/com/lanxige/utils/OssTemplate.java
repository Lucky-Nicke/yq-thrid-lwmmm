package com.lanxige.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.lanxige.config.AliOssConfig;
import com.lanxige.service.SysMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OssTemplate {
    @Autowired
    private AliOssConfig ossConfig;

    @Autowired
    private SysMovieService sysMovieService;

    public String upload(String fileName, InputStream is) {
        OSS ossClient = new OSSClient(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        String objName = "img/yq-third-lwmmm/"
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+ "/"
                + System.currentTimeMillis()
                + fileName.substring(fileName.lastIndexOf("."));

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
        ossClient.putObject(ossConfig.getBucketName(), objName, is, meta);

        ossClient.shutdown();
        return ossConfig.getUrl() + "/" + objName;
    }

    public String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        return "image/jpg";
    }
}
