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

    private String accessKey = "YOURACCESSKEY";
    private String secretKey = "YOURSECRETKEY";
    private String region = "YOURREGION"; // örn: "us-east-1"

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
