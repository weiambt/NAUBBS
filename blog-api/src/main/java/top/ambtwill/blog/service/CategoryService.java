package top.ambtwill.blog.service;

import top.ambtwill.blog.vo.CategoryVo;
import top.ambtwill.blog.vo.Result;

/*
    2022/3/14 10:27
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetails();

    Result findCategoryDetailById(Long categoryId);

}
