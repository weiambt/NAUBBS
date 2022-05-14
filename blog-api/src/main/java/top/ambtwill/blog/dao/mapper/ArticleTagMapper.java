package top.ambtwill.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.dao.pojo.ArticleTag;

@Mapper
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
}
