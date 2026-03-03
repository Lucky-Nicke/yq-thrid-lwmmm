package com.lanxige.Rsp;

import lombok.Data;

import java.util.List;

/**
 * 用户趋势响应类
 */
@Data
public class UserTrendRsp {

    // 最近7天数据
    private List<Integer> actualData;

    // 总用户数
    private Integer totalUserCount;
}