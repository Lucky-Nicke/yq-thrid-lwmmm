package com.lanxige.model.video;

import com.lanxige.model.base.BaseEntity;
import lombok.Data;

/**
 * 视频统计
 */
@Data
public class VideoStat extends BaseEntity {
    /**
     * 视频id
     */
    private String videoId;
    /**
     * 浏览量
     */
    private int visitPv;
    /**
     * 弹幕量
     */
    private int playCount;

    /**
     * 点赞数
     */
    private int likeCount;
}
