package top.ambtwill.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.admin.dao.AdminMapper;
import top.ambtwill.blog.admin.dao.PermissionMapper;
import top.ambtwill.blog.admin.pojo.Admin;
import top.ambtwill.blog.admin.pojo.Permission;

import java.util.List;

/*
    2022/3/18 16:46
    @author ZW
    Project Name:blog-parent
     
*/
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionsByAdminId(Long adminId){
        return permissionMapper.findPermissionsByAdminId(adminId);
    }


}
