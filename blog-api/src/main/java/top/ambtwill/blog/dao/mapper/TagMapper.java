package top.ambtwill.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ambtwill.blog.dao.pojo.Tag;

import java.util.List;

@Mapper
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询最热的标签
     * @param limit
     * @return
     */
    List<Long> findHotsTagsIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
