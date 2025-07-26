package com.herbology.controller.user;

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
    @GetMapping("/login")
    public Result<String> login() {
        return Result.success("login");
    }

    @GetMapping("/register")
    public Result<String> register() {
        return Result.success("register");
    }
}
