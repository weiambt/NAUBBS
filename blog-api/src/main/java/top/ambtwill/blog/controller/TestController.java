package top.ambtwill.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.utils.UserThreadLocal;
import top.ambtwill.blog.vo.Result;

/*
    2022/3/12 16:07
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/test")
public class TestController {

    //用来测试登录拦截器、（Threadlocal）
    @GetMapping
    public Result ss(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
