package top.ambtwill.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.ambtwill.blog.handler.LoginInterceptor;

/*
    2022/2/28 22:14
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 配置跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置：前端是8080端口，后端是8888
        //要允许8080访问接口服务
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080");
    }

    /**
     * 配置拦截器，拦截所有需要登录才能访问的接口
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")//评论要登录
                .addPathPatterns("/articles/publish");//要拦截后才能获取用户信息
                // .addPathPatterns("/**")
                // .excludePathPatterns("/login").excludePathPatterns("/register")
                /*
                这里因为博客项目不太需要拦截，所以只采用test作为测试
                如果要配的话，使用上面注释的两句话，拦截所有接口，除了登录和注册
                 */
    }
}
