package com.webapps.levelup.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Configuration
public class AppProperties {
    /**
     * Security Allowed Methods.
     */
    public final String[] securityMethods = {
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "OPTIONS"
    };

    /**
     * Security Allowed Headers.
     */
    public final String[] securityHeaders = {
            "authorization", "content-type", "x-auth-token", "jwt-token", "permissions"
    };

    @Value("${token.expiration}")
    private Integer tokenExpiration;

    @Value("${refresh-token.expiration}")
    private Integer refreshTokenExpiration;

    @Value("${image.path}")
    private String imagePath;

    @Value("${s3.bucket.arn}")
    private String s3BucketName;

    @Value("${s3.bucket.domain}")
    private String s3BucketDomain;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.key}")
    private String secretKey;
}
