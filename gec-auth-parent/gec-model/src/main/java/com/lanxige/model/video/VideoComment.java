package com.lanxige.model.video;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频评论表
 */
@Data
public class VideoComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    private Long id;

    /**
     * 视频ID
     */
    private String videoId;

    /**
     * 评论用户ID
     */
    private String userId;

    /**
     * 父评论ID
     */
    private Long parentId;

    /**
     * 根评论ID
     */
    private Long rootId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 回复数
     */
    private Integer replyCount;

    /**
     * 状态(1正常 0屏蔽 2删除)
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识（0未删除 1已删除）
     */
    private Integer isDeleted;
}