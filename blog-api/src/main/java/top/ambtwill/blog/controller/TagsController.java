package top.ambtwill.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ambtwill.blog.service.TagService;
import top.ambtwill.blog.vo.Result;

/*
    2022/3/2 22:37
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @RequestMapping("/hot")
    public Result hot(){
        int limit = 6 ;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("/detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("/detail/{id}")
    public Result findDetailById(@PathVariable Long id){
        return tagService.findDetailById(id);
    }
}
