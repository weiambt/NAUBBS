package top.ambtwill.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.omg.CORBA.IDLType;

/*
    2022/3/14 9:54
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/

/**
 * 文章体
 */
@Data
public class ArticleBody {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}