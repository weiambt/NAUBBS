package top.ambtwill.blog.common.aop;

import java.lang.annotation.*;

/*
    2022/3/16 14:54
    @author 张渭
    Project Name:blog-parent
     
    theme:自定义注解，实现日志，标记了该注解的类就会生成日志
*/
@Target({ElementType.METHOD,ElementType.TYPE})//METHOD作用于方法，TYPE作用于类
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
