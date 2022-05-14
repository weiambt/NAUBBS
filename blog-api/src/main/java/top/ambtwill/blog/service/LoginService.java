package top.ambtwill.blog.service;

import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
