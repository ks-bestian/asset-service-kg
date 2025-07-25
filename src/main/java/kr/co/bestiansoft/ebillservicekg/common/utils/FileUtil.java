package kr.co.bestiansoft.ebillservicekg.common.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {


    public static String upload(MultipartFile mf, String fileStorage, String fileType, String saveFileName) throws IOException {

        Path path = Paths.get(fileStorage, fileType, saveFileName);

        if(!Files.exists(path.getParent())){
            Files.createDirectories(path.getParent());
        }

        mf.transferTo(path);

        return path.toAbsolutePath().toString();
    }

    public static Resource loadFile(String fileName) {

        Path filePath = Paths.get(fileName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());

            if(!resource.exists()){
                // TODO 새로운 exception 필요
                throw new RuntimeException("File not found " + fileName);
            }

            return resource;

        } catch (MalformedURLException e) {
            // TODO 새로운 exception 필요
            throw new RuntimeException(e);
        }
    }

}
