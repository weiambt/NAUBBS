package top.ambtwill.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.ambtwill.blog.service.SysUserService;
import top.ambtwill.blog.dao.mapper.SysUserMapper;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.utils.JWTUtils;
import top.ambtwill.blog.vo.ErrorCode;
import top.ambtwill.blog.vo.LoginUserVo;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.UserVo;

import java.util.Map;

/*
    2022/3/2 21:00
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser=new SysUser();
            sysUser.setNickname("空！！！");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 根据Token查看用户信息
     * @return
     */
    @Override
    public Result getUserInfoByToken(String token) {

        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token)+"";
        // System.out.println("userJson:"+userJson);
        // System.out.println("StringUtils.isBlank:"+StringUtils.isBlank(userJson));
        if (StringUtils.isBlank(userJson)){
            System.out.println("userJson==null");
            return Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        System.out.println("sysUser:"+sysUser);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    /**
     * 根据账号查看用户是否存在（注册功能）
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 保存用户(注册)
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        //注意 默认生成的id 是分布式id 采用了雪花算法
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if(sysUser==null){//防止测试报错
            sysUser.setId(1L);
            sysUser.setNickname("测试的NickName");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;

    }

}
