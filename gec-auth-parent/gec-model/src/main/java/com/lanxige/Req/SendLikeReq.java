package com.lanxige.Req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Description: 发送弹幕请求
 */
@Data
public class SendLikeReq {
    @NotBlank(message = "视频id不能为空")
    private Long videoId;
    private Long userId;
    private Long commentId;
    @NotBlank(message = "点赞类型不能为空")
    private String likeType;
    @NotBlank(message = "是否点赞不能为空")
    private Boolean like;
}
