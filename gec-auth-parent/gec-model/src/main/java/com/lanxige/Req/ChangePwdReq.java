package com.lanxige.Req;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel(value = "修改密码请求", description = "修改密码请求")
public class ChangePwdReq {
    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "oldPwd不能为空")
    private String oldPwd;

    @NotBlank(message = "newPwd不能为空")
    private String newPwd;
}
