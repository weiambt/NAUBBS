package top.ambtwill.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ambtwill.blog.common.aop.LogAnnotation;
import top.ambtwill.blog.dao.mapper.ArticleBodyMapper;
import top.ambtwill.blog.dao.mapper.ArticleTagMapper;
import top.ambtwill.blog.dao.pojo.ArticleBody;
import top.ambtwill.blog.dao.pojo.ArticleTag;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.service.*;
import top.ambtwill.blog.dao.dos.Archives;
import top.ambtwill.blog.dao.mapper.ArticleMapper;
import top.ambtwill.blog.dao.pojo.Article;
import top.ambtwill.blog.utils.UserThreadLocal;
import top.ambtwill.blog.vo.*;
import top.ambtwill.blog.vo.params.ArticleBodyParam;
import top.ambtwill.blog.vo.params.ArticleParam;
import top.ambtwill.blog.vo.params.PageParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    2022/3/1 16:51
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth()
        );
        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records,true,true));

    }


    // @Override
    // public Result listArticle(PageParams pageParams) {
    //     /**
    //      * 1、分页查询 article数据表
    //      */
    //     //使用mybatis-plus的Page
    //     Page<Article>  page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
    //     LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();
    //
    //     if(pageParams.getCategoryId()!=null){//扩展了功能，P30
    //         queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
    //         //相当于加上 and article_id = #{articleId};
    //     }
    //     if(pageParams.getTagId() != null){
    //         // select * from article where id in (select articleId from ArticleTag where tag_id==#{TagId})
    //         ArrayList<Long> articleIdList = new ArrayList<>();
    //         LambdaQueryWrapper<ArticleTag> queryWrapper2 = new LambdaQueryWrapper<>();
    //         queryWrapper2.eq(ArticleTag::getTagId,pageParams.getTagId());
    //         queryWrapper2.select(ArticleTag::getArticleId);
    //         List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper2);
    //         for (ArticleTag articleTag : articleTags) {
    //             articleIdList.add(articleTag.getArticleId());
    //         }
    //         if(articleIdList.size()>0){
    //             queryWrapper.in(Article::getId,articleIdList);
    //         }
    //     }
    //     //是否置顶进行排序
    //     queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
    //     Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
    //     List<Article> records = articlePage.getRecords();
    //
    //     //records能直接返回吗，明显不能
    //     //调用函数copyList 将List<Article>化为List<ArticleVo>
    //     List<ArticleVo> articleVoList = copyList(records,true,true);
    //
    //     return Result.success(articleVoList);
    // }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();

        //下面三句话的意思：select id,title from article order by view_counts desc limit 5;
        queryWrapper.orderByDesc(Article::getViewCounts);//根据浏览量倒着排
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));

    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<Article>();

        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);

        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    @Override
    public Result listArchives() {
         List<Archives> archivesList = articleMapper.listArchives();
         return Result.success(archivesList);
    }

    @Autowired
    private ThreadService threadService;

    @Override
    public ArticleVo findArticleById(Long id) {
        //查看文章
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true, true, true);

        //更新浏览量,使用线程池
        threadService.updateArticleViewCount(articleMapper,article);

        return articleVo;
    }

    @Override
    @LogAnnotation(module = "文章",operation = "发布文章")//自定义日志
    public Result publish(ArticleParam articleParam) {
        //需要去拦截才能使用ThreadLocal，否则空指针异常
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setWeight(Article.Article_Common);//权重非置顶
        article.setCreateDate(System.currentTimeMillis());
        articleMapper.insert(article);//插入后，article会有一个Id？？？

        //tag
        List<TagVo> tags = articleParam.getTags();
        ArticleTag articleTag ;
        if(tags!=null)
            for (TagVo tag : tags) {
                articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(article.getId());
                articleTagMapper.insert(articleTag);
            }

        //文章体
        ArticleBodyParam articleBodyParam = articleParam.getBody();
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleBodyParam.getContent());
        articleBody.setContentHtml(articleBodyParam.getContentHtml());
        articleBody.setArticleId(article.getId());//因为article已经insert了
        articleBodyMapper.insert(articleBody);//先插入
        Long bodyId = articleBody.getId();//id就生成了
        article.setBodyId(bodyId);


        articleMapper.updateById(article);//更新article

        return Result.success(article);
    }

    /**
     * 函数copyList 将List<Article>化为List<ArticleVo>，
     * @param records
     * @param isTag 是否要传标签
     * @param isAuthor 是否要传作者
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, Boolean isTag,Boolean isAuthor) {
        List<ArticleVo> articleVoList=new ArrayList<>();

        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor));
        }
        return  articleVoList;
    }

    /**
     * 函数copy 将Article化为ArticleVo,属于函数copyList的一部分
     * @param article
     * @param isTag
     * @param isAuthor
     * @return
     */
    public ArticleVo copy(Article article, Boolean isTag,Boolean isAuthor){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);//Spring的

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        return articleVo;
    }

    /**
     * 重载的两个函数
     * @param records
     * @param isTag
     * @param isAuthor
     * @param isBody
     * @param isCategory
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records, Boolean isTag, Boolean isAuthor, Boolean isBody, Boolean isCategory) {
        List<ArticleVo> articleVoList=new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return  articleVoList;
    }
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    public ArticleVo copy(Article article, Boolean isTag,Boolean isAuthor, Boolean isBody, Boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);//Spring的

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }

        if(isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }

        if (isBody){
            Long articleId = article.getId();
            articleVo.setBody(findArticleBody(articleId));
        }

        if(isCategory){
            Long categoryId = article.getCategoryId();
            CategoryVo categoryVo = categoryService.findCategoryById(categoryId);
            articleVo.setCategorys(Arrays.asList(categoryVo));//与笔记不一样
        }
        return articleVo;
    }
    //copy函数调用
    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ArticleBody::getArticleId,articleId);

        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);

        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        if(articleBody.getContent()!=null)//set方法的值为null会报空指针异常
            articleBodyVo.setContent(articleBody.getContent());
        //或者 BeanUtils.copyProperties(articleBody,articleBodyVo);
        return articleBodyVo;
    }
}
