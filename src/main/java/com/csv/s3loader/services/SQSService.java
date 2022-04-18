package com.csv.s3loader.services;

import com.amazonaws.services.s3.event.S3EventNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class SQSService {

    @Value("${document.source-bucket-name}")
    private String sourceBucketName;

    @Autowired
    FileConversionService fileConversionService;

    @SqsListener(value = "test-my-process",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(S3EventNotification s3EventNotification) {
        System.out.println("s3 s3EventNotification "+ s3EventNotification.toString());
        S3EventNotification.S3EventNotificationRecord notificationRecord = s3EventNotification.getRecords().get(0);
        String srcKey = notificationRecord.getS3().getObject().getUrlDecodedKey();
        this.fileConversionService.convertToDestination(srcKey,notificationRecord.getS3().getBucket().getName());
    }
}