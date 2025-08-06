package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    
    @Value("${file.upload.path}")
    private String fileUploadDir;

    public static String upload(MultipartFile mf, String fileStorage, String fileType, String saveFileName) throws IOException {

        Path path = Paths.get(fileStorage, fileType, saveFileName);

        if(!Files.exists(path.getParent())){
            Files.createDirectories(path.getParent());
        }

        mf.transferTo(path);

        return path.toAbsolutePath().toString();
    }

    public static Resource loadFile(String filePath) {

        Path path = Paths.get(filePath).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());

            if(!resource.exists()){
                // TODO 새로운 exception 필요
                throw new RuntimeException("File not found " + filePath);
            }

            return resource;

        } catch (MalformedURLException e) {
            // TODO 새로운 exception 필요
            throw new RuntimeException(e);
        }
    }
    
    public String makeUploadPath(String middleDir) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String savePath = Paths.get(fileUploadDir, middleDir, today).toString();
        return savePath;
    }
    
    
    public static boolean delete(String filePath, String fileName) {
        if (filePath == null || fileName == null) {
        	LOGGER.warn("파일 삭제 실패: 경로 또는 파일명이 null입니다.");
            return false;
        }

        File file = new File(filePath, fileName);

        if (!file.exists()) {
        	LOGGER.info("파일이 존재하지 않아 삭제하지 않음: {}", file.getAbsolutePath());
            return false;
        }

        boolean deleted = file.delete();
        if (deleted) {
        	LOGGER.info("파일 삭제 성공: {}", file.getAbsolutePath());
        } else {
        	LOGGER.error("파일 삭제 실패: {}", file.getAbsolutePath());
        }

        return deleted;
    }

}
