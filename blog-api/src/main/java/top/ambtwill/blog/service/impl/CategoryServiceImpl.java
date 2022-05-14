package top.ambtwill.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.dao.mapper.CategoryMapper;
import top.ambtwill.blog.dao.pojo.Category;
import top.ambtwill.blog.service.CategoryService;
import top.ambtwill.blog.vo.CategoryVo;
import top.ambtwill.blog.vo.Result;

import java.util.ArrayList;
import java.util.List;

/*
    2022/3/14 10:33
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);//只要这两个字段
        List<Category> categories = categoryMapper.selectList(queryWrapper);//查询所有

        return Result.success(copyList(categories));
    }

    @Override
    public Result findAllDetails() {
        List<Category> categories = categoryMapper.selectList(new QueryWrapper<>());
        return Result.success(categories);
    }

    @Override
    public Result findCategoryDetailById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return Result.success(category);
    }

    private List<CategoryVo> copyList(List<Category> categories) {
        ArrayList<CategoryVo> categoryVos = new ArrayList<>();
        for (Category category : categories) {
            categoryVos.add(copy(category));
        }
        return categoryVos;
    }

    private CategoryVo copy(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}
