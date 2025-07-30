package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

}
