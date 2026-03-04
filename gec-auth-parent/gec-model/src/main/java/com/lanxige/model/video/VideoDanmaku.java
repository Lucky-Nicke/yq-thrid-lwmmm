package com.lanxige.model.video;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanxige.model.base.BaseEntity;
import lombok.Data;

/**
 * 弹幕表(VideoDanmaku)实体类
 */
@Data
public class VideoDanmaku extends BaseEntity {
    /**
     * 弹幕ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频ID
     */
    @TableField("video_id")
    private Long videoId;

    /**
     * 用户ID(未登录可为空)
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 阿里云VOD视频ID
     */
    @TableField("vod_video_id")
    private String vodVideoId;

    /**
     * 弹幕内容
     */
    @TableField("content")
    private String content;

    /**
     * 弹幕出现时间(秒)
     */
    @TableField("play_time")
    private Integer playTime;

    /**
     * 弹幕颜色
     */
    @TableField("color")
    private String color;

    /**
     * 弹幕位置(0滚动 1顶部 2底部)
     */
    @TableField("position")
    private Byte position;

    /**
     * 字体大小
     */
    @TableField("font_size")
    private Byte fontSize;

    /**
     * 状态(1正常 0屏蔽)
     */
    @TableField("status")
    private Byte status;
}
