package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.UtilException;
import com.atguigu.spzx.manager.config.MyMinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.*;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceimpl implements FileUploadService {
    @Autowired
    private MyMinioProperties myMinioProperties;
    @Override
    public String uploadFile(MultipartFile file) {
        try {

            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(myMinioProperties.getEndpointUrl())
                            .credentials(myMinioProperties.getAccessKey(), myMinioProperties.getSecreKey())
                            .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(myMinioProperties.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(myMinioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket 'spzx' already exists.");
            }


            String dateFormat = DateUtil.format(new Date(), "yyyy/MM/dd");
            String filename = file.getOriginalFilename();
            String suffix = FileNameUtils.getExtension(filename);
            String prefix = UUID.randomUUID().toString().replaceAll("-", "");
            String uploadFileName = dateFormat+"/"+prefix+"."+suffix;

            //创建PutObjectArg
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(myMinioProperties.getBucketName())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .object(uploadFileName)
                    .build();

            //上传文件
            minioClient.putObject(putObjectArgs);

            //设置要返回的文件的路径
            String fileUrl = myMinioProperties.getEndpointUrl()+"/"+myMinioProperties.getBucketName()+"/"+uploadFileName;
            return fileUrl;
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }
}
