package top.ambtwill.blog.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.admin.pojo.Permission;

import java.util.List;

/*
    2022/3/18 15:59
    @author ZW
    Project Name:blog-parent
     
*/
@Mapper
@Repository
public interface PermissionMapper  extends BaseMapper<Permission> {
    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
    List<Permission> findPermissionsByAdminId(Long adminId);
}
