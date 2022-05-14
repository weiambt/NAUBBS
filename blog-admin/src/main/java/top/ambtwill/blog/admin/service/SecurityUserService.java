package top.ambtwill.blog.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import top.ambtwill.blog.admin.dao.AdminMapper;
import top.ambtwill.blog.admin.pojo.Admin;

import java.util.ArrayList;

/*
    2022/3/18 16:44
    @author ZW
    Project Name:blog-parent
     
*/
@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录的时候会把username传递过来
        //查询admin表，存在就将密码告诉SpringSecurity
        //不存在返回null
        Admin admin = adminService.findAdminByUsername(username);
        if(admin==null){
            //登录失败
            return null;
        }
        UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());
        //剩下的认证由框架帮我们完成
        return userDetails;
    }
}
