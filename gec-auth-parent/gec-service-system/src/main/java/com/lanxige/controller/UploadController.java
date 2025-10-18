package com.lanxige.controller;

import com.lanxige.utils.OssTemplate;
import com.lanxige.utils.VodTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/admin/system/upload")
@CrossOrigin
public class UploadController {
    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private VodTemplate vodTemplate;

    @RequestMapping(value = "/uploadImage")
    public String uploadImage(MultipartFile uploadImage) throws IOException {
        return this.ossTemplate.upload(uploadImage.getOriginalFilename(), uploadImage.getInputStream());
    }

    // 上传视频操作
    @RequestMapping(value = "/uploadVideo")
    public String uploadVideo(MultipartFile uploadVideo) throws IOException {
        String videoId = this.vodTemplate.uploadVideo(uploadVideo.getOriginalFilename(), uploadVideo.getInputStream());
        System.out.println("videoId====="+videoId);

        return  videoId;
    }
}
