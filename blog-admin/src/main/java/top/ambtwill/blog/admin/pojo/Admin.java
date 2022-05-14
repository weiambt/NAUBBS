package top.ambtwill.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.omg.CORBA.IDLType;

/*
    2022/3/18 16:47
    @author ZW
    Project Name:blog-parent
     
*/
@Data
@AllArgsConstructor
public class Admin {
    @TableId(type = IdType.AUTO)//自增
    private Long id;

    private String username;

    private String password;
}
