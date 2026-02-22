package com.lanxige.model.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanxige.model.base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//栏目
@Data
@TableName("sys_category")
public class SysCategory extends BaseEntity {
    @NotBlank(message = "栏目名不能为空")
    private String name;//栏目名
}
