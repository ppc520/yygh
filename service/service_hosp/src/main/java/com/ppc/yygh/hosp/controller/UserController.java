package com.ppc.yygh.hosp.controller;

import com.atguigu.yygh.model.acl.User;
import com.ppc.yygh.common.result.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/user")
public class UserController {

    @PostMapping("/login")
    public R loging(@RequestBody User user){
        return R.ok().data("token","admin-token");
    }

    @GetMapping("/info")
    public R getInfo(String token){
        return R.ok().data("roles","[admin]")
                .data("introduction","I am a super admin")
                .data("avatar","https://p8.itc.cn/images01/20211221/f6479428c7f24642987725828d961caa.gif")
                .data("name","super admin");
    }
}
