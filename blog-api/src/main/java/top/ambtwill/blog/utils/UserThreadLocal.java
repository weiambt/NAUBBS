package top.ambtwill.blog.utils;

import top.ambtwill.blog.dao.pojo.SysUser;

/*
    2022/3/12 17:46
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
public class UserThreadLocal {
    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }

}
