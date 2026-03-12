package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.Req.UserInfoRsp;
import com.lanxige.model.video.VideoLikeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoLikeLogMapper extends BaseMapper<VideoLikeLog> {

    List<UserInfoRsp> getUserLikeLog(@Param("userId") Long userId);
}