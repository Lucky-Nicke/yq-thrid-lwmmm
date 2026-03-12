package com.lanxige.Req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @Description: 发送评论请求
 */
@Data
public class SendCommentReq {

    @NotNull(message = "视频id不能为空")
    private Long videoId;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    private Long toUserId;

    // 根评论ID（一级评论为空）
    private Long commentRootId;

    // 父评论ID
    private Long parentId;

    @NotBlank(message = "评论内容不能为空")
    private String content;
}
