package top.ambtwill.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.ambtwill.blog.service.TagService;
import top.ambtwill.blog.dao.mapper.TagMapper;
import top.ambtwill.blog.dao.pojo.Tag;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.TagVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    2022/3/2 20:13
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;


    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatis-plus是无法进行多表查询
        List<Tag> tags= tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         * 标签所拥有的文章数量最多：最热标签
         */
         List<Long> tagIds = tagMapper.findHotsTagsIds(limit);
         //判空
         if(CollectionUtils.isEmpty(tagIds)){
             return Result.success(Collections.emptyList());
         }
         List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
         return  Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tagList = tagMapper.selectList(new QueryWrapper<>());
        return Result.success(tagList);
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(tag);
    }


}
