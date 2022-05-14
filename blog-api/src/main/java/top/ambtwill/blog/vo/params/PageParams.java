package top.ambtwill.blog.vo.params;

import lombok.Data;

/*
    2022/3/1 16:40
    @author 张渭
    Project Name:blog-parent
     
    theme:
*/
@Data
public class PageParams {

    private int page=1;

    private int pageSize=10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    // public String getMonth(){
    //     if (this.month != null && this.month.length() == 1){
    //         return "0"+this.month;
    //     }
    //     return this.month;
    // }
}
