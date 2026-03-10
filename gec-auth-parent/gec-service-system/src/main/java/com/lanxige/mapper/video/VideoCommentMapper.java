package com.lanxige.mapper.video;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanxige.Rsp.NewCommentRsp;
import com.lanxige.model.video.VideoComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoCommentMapper extends BaseMapper<VideoComment> {
    /**
     * 根据父级id查询评论数量
     *
     * @param parentId 父级id
     * @return 评论数量
     */
    int decreaseReplyCount(@Param("parentId") Long parentId);

    /**
     * 查询最新评论
     *
     * @return 最新评论
     */
    List<NewCommentRsp> selectNewComment();
}
