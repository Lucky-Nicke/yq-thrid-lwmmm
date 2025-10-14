package com.lanxige.controller;

import com.lanxige.exception.MyCustomerException;
import com.lanxige.model.system.SysUser;
import com.lanxige.model.vo.LoginVo;
import com.lanxige.service.ISysUserService;
import com.lanxige.util.JwtHelper;
import com.lanxige.util.MD5Helper;
import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登录管理控制器")
@RequestMapping("/admin/system/index")
public class LoginController {

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = this.sysUserService.getUserInfoUserName(loginVo.getUsername());

        if (sysUser == null) {
            throw new MyCustomerException(20001, "用户名不存在");
        }

        String password = loginVo.getPassword();
        String passwordWithMD5 = MD5Helper.md5(password);

        if (!sysUser.getPassword().equals(passwordWithMD5)) {
            throw new MyCustomerException(20001, "密码错误");
        }

        if (sysUser.getStatus().intValue() == 0) {
            throw new MyCustomerException(20001, "用户已禁用");
        }

        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
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
