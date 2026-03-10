package com.lanxige.utils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Description: 时间格式化工具类
 */
public class TimeAgoUtil {
    public static String format(LocalDateTime createTime) {
        LocalDateTime now = LocalDateTime.now();

        long seconds = Duration.between(createTime, now).getSeconds();

        if (seconds < 60) {
            return seconds + "秒前";
        }

        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + "分钟前";
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "小时前";
        }

        long days = hours / 24;
        return days + "天前";
    }
}