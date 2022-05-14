package top.ambtwill.blog.vo.params;

import lombok.Data;

/*
    2022/3/3 20:08
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Data
public class LoginParam {
    private String account;

    private String password;

    private String nickname;
}
