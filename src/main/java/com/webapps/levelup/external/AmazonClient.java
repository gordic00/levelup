package com.webapps.levelup.external;

import com.webapps.levelup.configuration.AWSConfig;
import com.webapps.levelup.configuration.AppProperties;
import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.helper.FileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AmazonClient {
    private final AWSConfig awsConfig;
    private final AppProperties appProperties;

    public String uploadFile(MultipartFile multipartFile)
            throws Exception {
        String fileUrl;
        File file = convertMultiPartToFile(multipartFile);
        File watermarkImageFile = new File("/var/lib/tomcat9/webapps/files/levelup-watermark.png");
        FileHelper.addImageWatermark(watermarkImageFile, file);
        String fileName = generateFileName(multipartFile);
        fileUrl = appProperties.getS3BucketDomain() + fileName;
        try {
            uploadFileTos3bucket(fileName, file);
        } catch (Exception e) {
            file.delete();
            throw new CustomException(e.getMessage());
        }
        file.delete();
        return fileUrl;
    }

    public String uploadFile(File file) {
        String fileUrl;
        fileUrl = appProperties.getS3BucketDomain() + file.getName();
        try {
            uploadFileTos3bucket(file.getName(), file);
        } catch (Exception e) {
            file.delete();
            throw new CustomException(e.getMessage());
        }
        file.delete();
        return fileUrl;
    }

    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        awsConfig.amazonS3().deleteObject(appProperties.getS3BucketName(), fileName);
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        awsConfig.amazonS3().putObject(appProperties.getS3BucketName(), fileName, file);
    }

    private File convertMultiPartToFile(MultipartFile file)
            throws IOException {
        File convFile = new File("/var/lib/tomcat9/webapps/files/" + Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
