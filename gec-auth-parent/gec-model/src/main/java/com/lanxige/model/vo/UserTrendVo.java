package com.lanxige.model.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用户趋势统计
 */
@Data
public class UserTrendVo {
    // 日期
    private LocalDate day;

    // 注册用户数
    private Integer count;
}