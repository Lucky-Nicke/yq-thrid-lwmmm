package com.lanxige.utils;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lanxige.config.VodConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;


// 视频操作工具类
@Component
public class VodTemplate {

    private static final Logger log = LoggerFactory.getLogger(VodTemplate.class);

    @Autowired
    private VodConfig vodConfig;

    // 初始化阿里云VOD客户端
    public DefaultAcsClient initVodClient() {
        try {
            DefaultProfile profile = DefaultProfile.getProfile(
                    vodConfig.getRegionId(),
                    vodConfig.getAccessKeyId(),
                    vodConfig.getAccessKeySecret());
            return new DefaultAcsClient(profile);
        } catch (Exception e) {
            log.error("初始化VOD客户端失败", e);
            throw new RuntimeException("初始化VOD客户端失败", e);
        }
    }

    // 上传视频，返回视频ID（关键优化：关闭InputStream）
    public String uploadVideo(String fileName, InputStream inputStream) {
        try (InputStream stream = inputStream) {
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(
                    vodConfig.getAccessKeyId(),
                    vodConfig.getAccessKeySecret(),
                    title,
                    fileName,
                    stream);
            request.setStorageLocation("lucky-nicke-movie.oss-cn-shenzhen.aliyuncs.com");
            request.setApiRegionId("cn-shenzhen");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            // 校验上传结果
            if (response.isSuccess()) {
                log.info("视频上传成功，视频ID：{}，文件名：{}", response.getVideoId(), fileName);
                return response.getVideoId();
            } else {
                String errorMsg = String.format(
                        "视频上传失败，文件名：%s，错误码：%s，错误信息：%s",
                        fileName,
                        response.getCode(),
                        response.getMessage()
                );
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        } catch (Exception e) {
            String errorMsg = "视频上传过程异常，文件名：" + fileName;
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }
    }

    // 获取播放凭证
    public GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) throws Exception {
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = initVodClient().getAcsResponse(request);
            log.info("获取播放凭证成功，视频ID：{}", videoId);
            return response;
        } catch (Exception e) {
            log.error("获取播放凭证失败，视频ID：{}", videoId, e);
            throw e; // 向上抛出，让调用方处理
        }
    }
}