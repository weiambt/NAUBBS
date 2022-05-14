package top.ambtwill.blog.service;

import org.springframework.stereotype.Service;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.CommentParam;


public interface CommentsService {
    /**
     * 根据文章id查看所有评论
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    /**
     * 新增评论
     * @param commentParam
     * @return
     */
    Result addComment(CommentParam commentParam);
}
