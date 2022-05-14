package top.ambtwill.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.ambtwill.blog.service.CommentsService;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.CommentParam;

/*
    2022/3/14 16:10
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 文章的所有评论列表
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result comments(@PathVariable Long id){
        return commentsService.commentsByArticleId(id);
    }

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @PostMapping("/create/change")
    public Result createComment(@RequestBody CommentParam commentParam){
        return commentsService.addComment(commentParam);
    }
}
