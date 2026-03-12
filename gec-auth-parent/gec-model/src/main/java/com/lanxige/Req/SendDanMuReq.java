package com.lanxige.Req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Description: 发送弹幕请求
 */
@Data
public class SendDanMuReq {
    @NotBlank(message = "视频id不能为空")
    private String videoId;
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @NotBlank(message = "弹幕内容不能为空")
    private String content;
    @NotBlank(message = "弹幕时间不能为空")
    private Double time;
}
