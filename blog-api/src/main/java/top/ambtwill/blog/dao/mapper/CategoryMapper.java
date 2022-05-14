package top.ambtwill.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.dao.pojo.Category;

/*
    2022/3/14 10:34
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
}
