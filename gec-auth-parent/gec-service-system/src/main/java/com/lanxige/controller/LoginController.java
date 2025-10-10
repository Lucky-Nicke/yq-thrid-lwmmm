package com.lanxige.controller;

import com.lanxige.util.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "登录管理控制器")
@RequestMapping("/admin/system/index")
public class LoginController {
    @PostMapping("/login")
    public Result login(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("token","admin-token");
        return Result.ok(map);
    }

    @GetMapping(value = "/info")
    public Result info()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("introduction","I am a super admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","admin dafei hello!");
        return Result.ok(map);

    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok();
    }
}
