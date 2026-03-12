package com.lanxige.Rsp;

import lombok.Data;

/**
 * @Desciption: 所有视频信息
 */
@Data
public class AllVideoInfoRsp {
    private Long  id;
    private String title;
    private String author;
    private int views;
    private String coverUrl;
    private String category;
}
