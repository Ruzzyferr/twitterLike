package com.ruzzyfer.twitterlike.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    private String accessKey = "AKIAW3MEADUR3M3ZAEN5";
    private String secretKey = "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";
    private String region = "us-east-1"; // örn: "us-east-1"

    @Bean
    public AmazonS3 s3(){


        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);

    return AmazonS3ClientBuilder.
                    standard()
                    .withRegion(region)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .build();

    }

}
