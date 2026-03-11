package com.lanxige.Rsp;

import lombok.Data;

@Data
public class AllVideoInfoRsp {
    private Long  id;
    private String title;
    private String author;
    private String views;
    private String coverUrl;
    private String category;
}
