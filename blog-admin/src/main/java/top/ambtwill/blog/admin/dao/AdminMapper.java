package top.ambtwill.blog.admin.dao;
/*
     2022/3/18 16:47
     @author ZW
     Project Name:blog-parent
     
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.admin.pojo.Admin;

@Mapper
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
}
