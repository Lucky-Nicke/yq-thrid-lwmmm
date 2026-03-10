package com.lanxige.Rsp;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 最新评论响应类
 */
@Data
public class NewCommentRsp {
    // 用户名
    private String user;

    // 评论时间
    private LocalDateTime createTime;

    // 评论内容
    private String time;

    // 评论电影
    private String movie;

    // 评论内容
    private String content;

    // 用户头像
    private String avatar;
}
