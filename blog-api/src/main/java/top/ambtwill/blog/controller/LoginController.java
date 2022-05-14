package top.ambtwill.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ambtwill.blog.service.LoginService;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.LoginParam;

/*
    2022/3/3 20:01
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/login")
public class LoginController {
    //不要这样，这个sysUserService是只完成User的操作的service
    // @Autowired
    // private SysUserService sysUserService;

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登录 验证用户 反问用户表
        return  loginService.login(loginParam);
    }


}
