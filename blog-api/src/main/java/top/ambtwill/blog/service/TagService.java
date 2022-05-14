package top.ambtwill.blog.service;

import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result findAllDetail();

    /**
     * 根据标签id查询标签详情
     * @param id
     * @return
     */
    Result findDetailById(Long id);
}
