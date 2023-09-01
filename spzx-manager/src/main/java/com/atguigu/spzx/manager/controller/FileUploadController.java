package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/system/file")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result<String> uploadFile(@RequestParam(value = "file")MultipartFile file){
        String fileUrl = fileUploadService.uploadFile(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS );
    }

}
