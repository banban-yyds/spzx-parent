package com.atguigu.spzx.minio.test;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileUploader {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://localhost:9000/")
                            .credentials("minioadmin", "minioadmin")
                            .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("spzx").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("spzx").build());
            } else {
                System.out.println("Bucket 'spzx' already exists.");
            }


            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("spzx")
                            .object("spzx-01.jpg")
                            .filename("/home/user/Photos/asiaphotos.zip")
                            .build());


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}