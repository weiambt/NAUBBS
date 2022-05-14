package top.ambtwill.blog.admin.service; /*
     2022/3/18 15:50
     @author ZW
     Project Name:blog-parent
     
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.admin.dao.PermissionMapper;
import top.ambtwill.blog.admin.pojo.Permission;
import top.ambtwill.blog.admin.vo.PageResult;
import top.ambtwill.blog.admin.vo.Result;
import top.ambtwill.blog.admin.vo.params.PageParam;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 要的数据：表的所有字段
     * 分页
     * @param pageParam
     * @return
     */
    public Result listPermission(PageParam pageParam) {
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = permissionMapper.selectPage(page, new LambdaQueryWrapper<>());
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);

    }

    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {

        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}
