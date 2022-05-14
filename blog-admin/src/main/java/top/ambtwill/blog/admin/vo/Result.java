package top.ambtwill.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
    2022/3/18 15:44
    @author ZW
    Project Name:blog-parent
     
*/
@Data
@AllArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
