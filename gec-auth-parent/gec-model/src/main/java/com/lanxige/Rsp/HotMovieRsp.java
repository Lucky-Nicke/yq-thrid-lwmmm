package com.lanxige.Rsp;

import lombok.Data;

/**
 * 热门视频查询响应类
 */
@Data
public class HotMovieRsp {
    // 视频名字
    private String name;

    // 视频分类
    private String category;

    // 播放量
    private Long views;
}
