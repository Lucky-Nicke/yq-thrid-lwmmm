package com.lanxige.controller;

import com.lanxige.model.vo.LoginVo;
import com.lanxige.service.ISysUserService;
import com.lanxige.util.JwtHelper;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(tags = "登录管理控制器")
@RequestMapping("/admin/system/index")
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/login")
    public void login(@RequestBody LoginVo loginVo) {

    }

    @GetMapping(value = "/info")
    public Result info(HttpServletRequest request) {
        String token = request.getHeader("token");
        String username = JwtHelper.getUsername(token);
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }
}
