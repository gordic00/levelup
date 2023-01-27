package com.webapps.levelup.helper;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

@Component
public class FileHelper {
    /**
     * Check if files size bigger than maxSize.
     *
     * @param files   all files
     * @param maxSize Max size of files in Mb
     * @return boolean true if files size bigger then allowed
     */
    public static boolean checkSize(MultipartFile[] files, long maxSize) {
        long totalSize = 0;
        for (MultipartFile file : files) {
            totalSize += file.getSize();
        }

        return byteToMb(totalSize) > maxSize;
    }

    /**
     * check Single Size.
     *
     * @param file    MultipartFile
     * @param maxSize long
     * @return boolean
     */
    public static boolean checkSingleSize(MultipartFile file, long maxSize) {
        return byteToMb(file.getSize()) > maxSize;
    }

    /**
     * remove Extension.
     *
     * @param fileName String
     * @return String
     */
    public static String removeExtension(String fileName) {
        if (fileName.indexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
    }

    /**
     * Check if provided type is allowed.
     *
     * @param file File to check
     * @return boolean true if file type is allowed
     */
    public static boolean checkFileType(MultipartFile file, String[] allowedTypes) {
        return Arrays.asList(allowedTypes).contains(getFileExtension(file).toLowerCase());
    }

    /**
     * Get file extension from MultipartFile.
     *
     * @param file - MultipartFile file
     * @return String
     */
    public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        return FilenameUtils.getExtension(originalFileName);
    }

    /**
     * Get file extension from string.
     *
     * @param filename - file path
     * @return String
     */
    public static String getExtensionFromString(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * Convert Byte to Megabyte.
     *
     * @return long Provides bytes in mb
     */
    public static long byteToMb(long bytes) {
        return (bytes / 1024) / 1024;
    }

    /**
     * Read file from resource directory.
     *
     * @param path - Path to resource folder
     * @return String
     */
    public static String readResource(String path) {
        File resource;
        try {
            resource = new ClassPathResource(path).getFile();
            return Files.readString(resource.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get main resource path.
     *
     * @return String
     */
    public static String readResourcePath() {
        File resourcesDirectory = new File("src/main/resources");
        return resourcesDirectory.getAbsolutePath();
    }

    /**
     * Create file.
     *
     * @param filename - file destination path
     * @return boolean
     */
    public static boolean createFile(String filename) {
        try {
            File obj = new File(filename);
            return obj.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Write content in file.
     *
     * @param filename - target file
     * @param content  - text to write
     * @return boolean
     */
    public static boolean writeToFile(String filename, String content) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(content);
            myWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Base64 encode bytes.
     *
     * @param txt - text for encoding
     * @return String
     */
    public static byte[] base64EncodeBytes(String txt) {
        return Base64.getEncoder().encode(txt.getBytes());
    }

    /**
     * Base64 encode.
     *
     * @param txt - text for encoding
     * @return String
     */
    public static String base64Encode(String txt) {
        return Base64.getEncoder().encodeToString(txt.getBytes());
    }

    /**
     * Base64 decode.
     *
     * @param txt - hash for decoding
     * @return String
     */
    public static String base64Decode(String txt) {
        byte[] decodedBytes = Base64.getDecoder().decode(txt);
        return new String(decodedBytes);
    }

    /**
     * Replace string in template.
     *
     * @param source      - Source string
     * @param target      - Target sequence in string
     * @param replacement - Replacement sequence in string
     * @return String
     */
    public static String templateReplacer(String source, String target, String replacement) {
        return source.replace(target, replacement);
    }
}
