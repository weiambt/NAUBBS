package top.ambtwill.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ambtwill.blog.service.LoginService;
import top.ambtwill.blog.vo.Result;

/*
    2022/3/12 10:51
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping
    public Result logOut(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
