package top.ambtwill.blog.vo.params;

import lombok.Data;
import top.ambtwill.blog.vo.CategoryVo;
import top.ambtwill.blog.vo.TagVo;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}