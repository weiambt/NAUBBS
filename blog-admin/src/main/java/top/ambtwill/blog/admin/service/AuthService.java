package top.ambtwill.blog.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.admin.pojo.Admin;
import top.ambtwill.blog.admin.pojo.Permission;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/*
    2022/3/18 17:02
    @author ZW
    Project Name:blog-parent
     
*/

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
        String requestURI = request.getRequestURI();
        log.info("request url:{}", requestURI);
        //true代表放行      false代表拦截
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin == null){
            return false;
        }
        if (admin.getId() == 1){
            //认为是超级管理员
            return true;
        }
        List<Permission> permissions = adminService.findPermissionsByAdminId(admin.getId());
        requestURI = StringUtils.split(requestURI,'?')[0];//不要参数只要请求
        for (Permission permission : permissions) {
            if (requestURI.equals(permission.getPath())){//这里删除权限除了root谁都没有，因为URL上有变量
                log.info("权限通过");
                return true;
            }
        }
        return false;
    }
}
