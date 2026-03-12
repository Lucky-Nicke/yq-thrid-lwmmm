package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.Req.UserInfoRsp;
import com.lanxige.model.video.VideoWatchLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoWatchLogMapper extends BaseMapper<VideoWatchLog> {

    List<UserInfoRsp> getUserWatchLog(@Param("userId") Long userId);
}