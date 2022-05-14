package top.ambtwill.blog.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ambtwill.blog.utils.QiniuUtils;
import top.ambtwill.blog.vo.Result;

import java.util.UUID;

/*
    2022/3/16 15:52
    @author 张渭
    Project Name:blog-parent
     
    theme:上传文件
*/
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){
        //原始文件名称a.png
        String originalFilename = file.getOriginalFilename();

        //唯一文件名称 123png
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(QiniuUtils.url+"/"+ fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
