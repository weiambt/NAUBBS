package top.ambtwill.blog.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ambtwill.blog.admin.pojo.Permission;
import top.ambtwill.blog.admin.service.PermissionService;
import top.ambtwill.blog.admin.vo.Result;
import top.ambtwill.blog.admin.vo.params.PageParam;


/*
    2022/3/18 15:38
    @author ZW
    Project Name:blog-parent
     
*/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }

}
