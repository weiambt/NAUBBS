package top.ambtwill.blog.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.ambtwill.blog.common.aop.LogAnnotation;
import top.ambtwill.blog.common.cache.Cache;
import top.ambtwill.blog.service.ArticleService;
import top.ambtwill.blog.vo.ArticleVo;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.ArticleParam;
import top.ambtwill.blog.vo.params.PageParams;

/*
    2022/3/1 16:39

    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @Cache(expire = 5*60*1000,name = "listArticle")
    @LogAnnotation(module = "/articles",operation = "listArticle")
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("/hot")
    @Cache(expire = 5*60*1000,name = "hotArticle")
    // @LogAnnotation(module = "/articles",operation = "hotArticle")
    public Result hotArticle(){
       int limit=3;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章PRIMARY
     * @return
     */
    @PostMapping("/new")
    @Cache(expire = 5*60*1000,name = "newArticle")
    // @LogAnnotation(module = "/articles",operation = "newArticle")
    public Result newArticle(){
        int limit=3;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        int limit=3;
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticle(@PathVariable("id") Long id){
        ArticleVo articleVo = articleService.findArticleById(id);
        return Result.success(articleVo);
    }

    /**
     * 发布文章
     * @return
     */

    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }


}
