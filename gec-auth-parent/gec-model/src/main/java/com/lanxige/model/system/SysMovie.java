package com.lanxige.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanxige.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

//影视
@Data
@ApiModel(description = "影视")
@TableName("sys_movie")
public class SysMovie extends BaseEntity {

//    @TableId(type = IdType.AUTO)
//    private Integer id;//主键

    private String name;//影视名

    private Integer cid;//所属栏目

    private String description;//描述

    private String keyword;//搜索关键字

    private String director;//导演

    private String actor;//演员

    private String image;//缩略图

    private String type;//视频类型:1.免费视频  2.付费视频

    private String url;//播放url

    private String playId;//播放id

    @TableField(exist = false)//不存储到数据库,仅仅用于前台数据展示
    private String playAuth;//播放秘钥
}
