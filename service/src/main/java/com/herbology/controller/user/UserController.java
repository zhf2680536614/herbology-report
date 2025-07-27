package com.herbology.controller.user;

import com.herbology.annotation.Auth;
import com.herbology.result.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("UserUserController")
@RequestMapping("/api/user")
@AllArgsConstructor
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {
    
    @Auth(requireAuth = false, description = "用户登录接口")
    @GetMapping("/login")
    public Result<String> login() {
        return Result.success("login");
    }

    @Auth(requireAuth = false, description = "用户登录接口")
    @GetMapping("/register")
    public Result<String> register() {
        return Result.success("register");
    }
}
