package top.ambtwill.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.dao.mapper.ArticleMapper;
import top.ambtwill.blog.dao.pojo.Article;

/*
    2022/3/14 15:00
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Service
public class ThreadService {

    //此更新在线程池中进行
    @Async("taskExecutor")//异步
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        //update article set viewCounts = 100 where viewCounts = 99 and id = ?
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts()+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,article.getId());
        queryWrapper.eq(Article::getViewCounts,article.getViewCounts());//乐观锁,在多线程的情况下保证线程安全

        articleMapper.update(articleUpdate,queryWrapper);

        try {
            Thread.sleep(5000);
            System.out.println("更新成功...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("taskExecutor")//异步
    public void updateRedis(){

    }


}
