package top.ambtwill.blog.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.service.LoginService;
import top.ambtwill.blog.service.SysUserService;
import top.ambtwill.blog.utils.JWTUtils;
import top.ambtwill.blog.vo.ErrorCode;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.LoginParam;

import java.util.concurrent.TimeUnit;

/*
    2022/3/3 20:10
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //盐
    private static final String salt = "zwzww!@W#";

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user中查询是否存在
         * 3.不存在，登录失败
         * 4.存在，使用JWT生成TOKEN 返回给前端
         * 5.token放入redis中，redis存储 token:user信息 设置过期时间
         * （登录认证的时候，先验证token，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password))
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        //加密
        // password = DigestUtils.md5Hex((password + salt));
        password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        // System.out.println(password);
        SysUser sysUser =  sysUserService.findUser(account,password);

        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);//1是时间

        return Result.success(token);
    }

    /**
     * 退出登录
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /**
     * 注册功能
     * @param loginParam
     * @return
     */
    @Override
    public Result register(LoginParam loginParam) {
        //1.获取数据，判断合法性
        //2.查询数据库，有则返回已注册
        //3.没有则添加到数据库
        //4.生成token，加入redis
        //5.返回token
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname))
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());

        SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5DigestAsHex((nickname+salt).getBytes()));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);//保存到数据库

        //生成Token
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);//1是时间

        return Result.success(token);
    }


    @Test
    //生成用户账号密码，将生成的密码账户添加到数据库中
    public void createSysUser(){
        //自定义用户名、密码
        String userId="root";
        String password="root";
        //md5加密生成密码
        String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());

        SysUser sysUser = new SysUser();
        sysUser.setAccount(userId);
        sysUser.setPassword(md5Password);
        System.out.println(sysUser);
        String token = JWTUtils.createToken(sysUser.getId());
        System.out.println(token);
        //redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);//1是时间
        System.out.println("userId="+userId);
        System.out.println("password="+password);
        System.out.println("md5Password="+md5Password);

    }

}
