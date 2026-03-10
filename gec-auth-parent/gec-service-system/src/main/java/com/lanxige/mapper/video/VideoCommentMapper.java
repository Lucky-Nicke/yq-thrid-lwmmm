package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.model.video.VideoComment;
import org.apache.ibatis.annotations.Param;

public interface VideoCommentMapper extends BaseMapper<VideoComment> {
    /**
     * 根据父级id查询评论数量
     *
     * @param parentId 父级id
     * @return 评论数量
     */
    int decreaseReplyCount(@Param("parentId") Long parentId);
}
