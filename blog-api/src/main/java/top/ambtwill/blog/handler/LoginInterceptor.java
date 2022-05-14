package top.ambtwill.blog.handler;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.service.LoginService;
import top.ambtwill.blog.utils.JWTUtils;
import top.ambtwill.blog.utils.UserThreadLocal;
import top.ambtwill.blog.vo.ErrorCode;
import top.ambtwill.blog.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
    2022/3/12 15:21
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
//登录拦截器
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在controller方法（Handler）执行之前执行
        /**
         * 1.判断接口请求是否为HandlerMethod（Controller方法）
         * 2.判断token是否为null，空则登录
         * 3.检查token,checkToken
         * 4.认证成功就放行——>将用户的信息从redis中拿到并且给controller
         */
        if (!(handler instanceof HandlerMethod)){//如果不是Controller方法,比如静态资源，直接放行
            return true;
        }
        String token = request.getHeader("Authorization");

        //日志
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (token == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        Map<String, Object> map = JWTUtils.checkToken(token);
        if (map == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        //是登录状态，放行
        //我希望在controller中 直接获取用户的信息 怎么获取?
        String sysUserJson = redisTemplate.opsForValue().get("TOKEN_" + token)+"";
        SysUser sysUser = JSON.parseObject(sysUserJson, SysUser.class);
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除ThreadLocal中用完的信息，会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
