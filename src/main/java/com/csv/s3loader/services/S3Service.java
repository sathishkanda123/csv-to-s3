package com.csv.s3loader.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class S3Service {

    @Autowired
    AmazonS3 s3Client;

    @Value("${document.destination-bucket-name}")
    private String destinationBucketName;

    public void uploadChunk(){

    }

    public void uploadChunk(ByteArrayInputStream byteArrayInputStream, ObjectMetadata meta) {
        s3Client.putObject(destinationBucketName, null, byteArrayInputStream, meta);
    }
}