package top.ambtwill.blog.service;

import top.ambtwill.blog.vo.ArticleVo;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.ArticleParam;
import top.ambtwill.blog.vo.params.PageParams;


public interface ArticleService {

    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    Result newArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    ArticleVo findArticleById(Long id);

    /**
     * 新增文章
     */
    Result publish(ArticleParam articleParam);

}
