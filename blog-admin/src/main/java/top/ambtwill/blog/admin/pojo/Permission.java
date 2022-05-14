package top.ambtwill.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/*
    2022/3/18 15:53
    @author ZW
    Project Name:blog-parent
     
*/
@Data
public class Permission {

        @TableId(type = IdType.AUTO)
        private Long id;

        private String name;

        private String path;

        private String description;

}
