package top.ambtwill.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.dao.mapper.CommentMapper;
import top.ambtwill.blog.dao.pojo.Comment;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.service.CommentsService;
import top.ambtwill.blog.service.SysUserService;
import top.ambtwill.blog.utils.UserThreadLocal;
import top.ambtwill.blog.vo.CommentVo;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.UserVo;
import top.ambtwill.blog.vo.params.CommentParam;

import java.util.ArrayList;
import java.util.List;

/*
    2022/3/14 16:14
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result commentsByArticleId(Long id) {
        //1.根据id查询所有的评论
        //2.根据作者id查询作者信息
        //3.如果level==1查询有没有子评论
        //4.如果有 根据parent_id去查子评论

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);

        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> ans =  copyList(comments);//2.3.4步 都在这里
        return Result.success(ans);
    }

    @Override
    public Result addComment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();

        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());

        Long parentId = commentParam.getParent();
        if(parentId==null || parentId==0){
            comment.setLevel(1);
            comment.setParentId(0L);
        }else {
            comment.setLevel(2);
            comment.setParentId(parentId);
        }
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId==null ? 0 : toUserId);

        commentMapper.insert(comment);

        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    /**
     *         //2.根据作者id查询作者信息
     *         //3.如果level==1查询有没有子评论
     *         //4.如果有 根据parent_id去查子评论
     * @param comment
     * @return
     */
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        Long authorId = comment.getAuthorId();

        //设置作者
        UserVo userVo =  sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);

        //评论的评论
        List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());//查询所有的子评论
        commentVo.setChildrens(commentVoList);

        if(comment.getLevel()>1){//是子评论，则将子评论的Vo封装
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);

            commentVo.setToUser(toUserVo);
        }
        return commentVo;

    }

    /**
     * 根据一个父评论id查询所有的子评论
     * @param id
     * @return
     */
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return copyList(comments);

    }

}
