package com.lanxige.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanxige.model.base.BaseEntity;
import lombok.Data;

//栏目
@Data
@TableName("sys_category")
public class SysCategory extends BaseEntity {

    private String name;//栏目名
}
