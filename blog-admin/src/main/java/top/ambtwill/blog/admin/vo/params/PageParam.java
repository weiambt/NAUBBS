package top.ambtwill.blog.admin.vo.params;

import lombok.Data;

/*
    2022/3/18 15:48
    @author ZW
    Project Name:blog-parent
     
*/
@Data
public class PageParam {
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
