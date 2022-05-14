package top.ambtwill.blog.service;

import top.ambtwill.blog.dao.pojo.SysUser;
import top.ambtwill.blog.vo.Result;
import top.ambtwill.blog.vo.UserVo;


public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result getUserInfoByToken(String token);


    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
