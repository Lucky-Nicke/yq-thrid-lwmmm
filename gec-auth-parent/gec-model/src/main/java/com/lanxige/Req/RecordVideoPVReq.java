package com.lanxige.Req;

import lombok.Data;

/**
 * @Description: 视频观看记录请求
 */
@Data
public class RecordVideoPVReq {

    private Long videoId;

    // 可以为空
    private Long userId;
}