package top.ambtwill.blog.dao.dos;

import lombok.Data;

/*
    2022/3/3 19:14
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Data
public class Archives {
    private Integer year;

    private Integer month;

    private Long count;
}
