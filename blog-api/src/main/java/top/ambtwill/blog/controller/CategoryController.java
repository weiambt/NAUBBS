package top.ambtwill.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ambtwill.blog.service.CategoryService;
import top.ambtwill.blog.vo.Result;

/*
    2022/3/15 16:43
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@RestController
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping
    public Result getCategorys(){
        return  categoryService.findAll();
    }

    @GetMapping("/detail")
    public Result getCategorysDetail(){
        return categoryService.findAllDetails();
    }

    @GetMapping("/detail/{id}")
    public Result getCategorysDetailById(@PathVariable Long id){
        return categoryService.findCategoryDetailById(id);
    }
}
