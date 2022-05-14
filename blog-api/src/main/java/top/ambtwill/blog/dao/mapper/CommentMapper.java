package top.ambtwill.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.dao.pojo.Comment;

/*
    2022/3/14 16:16
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
