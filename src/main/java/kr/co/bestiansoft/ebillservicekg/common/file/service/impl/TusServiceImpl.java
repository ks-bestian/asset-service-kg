package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.service.TusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.upload.UploadInfo;
import me.desair.tus.server.util.TusServletRequest;
import me.desair.tus.server.util.TusServletResponse;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class TusServiceImpl implements TusService {

	private static final Logger logger = LoggerFactory.getLogger(TusServiceImpl.class);
	
    @Value("${file.upload.path}")
    private String fileUploadDir;
    
    private TusFileUploadService tusService;

    @PostConstruct
    public void init() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String savePath = Paths.get(fileUploadDir, "mnl", today).toString();
        
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs(); // 폴더 없으면 생성
        }
        this.tusService = new TusFileUploadService().withStoragePath(savePath);
    }

    @Override
    public void uploadTus(HttpServletRequest request, HttpServletResponse response) {
        try {
        	
            TusServletRequest tusRequest = new TusServletRequest(request);
            TusServletResponse tusResponse = new TusServletResponse(response);
        	
            tusService.process(tusRequest, tusResponse);
            
            // Get upload information
            UploadInfo uploadInfo = tusService.getUploadInfo(request.getRequestURI());
            
            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
            	String fileName = uploadInfo.getFileName();
            	String fileExtn = (fileName != null && fileName.contains(".")) 
            	    ? fileName.substring(fileName.lastIndexOf(".") + 1) 
            	    : null;
            	
                // Progress status is successful: Create file
            	String uuid = extractUuidFromUri(request.getRequestURI());
            	String finalFileName = fileExtn != null ? uuid + "." + fileExtn : uuid;
            	createFile(tusService.getUploadedBytes(request.getRequestURI()), finalFileName);

                // Delete an upload associated with the given upload url
                tusService.deleteUpload(request.getRequestURI());



            }
        } catch (Exception e) {
            log.error("Tus upload failed", e);
            throw new RuntimeException("Tus upload failed", e);
        }
    }
    
    private void createFile(InputStream is, String filename) throws IOException {
    	
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String savePath = Paths.get(fileUploadDir, "mnl", today).toString();
        
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs(); // 폴더 없으면 생성
        }
        File file = new File(savePath, filename);
        FileUtils.copyInputStreamToFile(is, file);
    }
    
    private String extractUuidFromUri(String uri) {
        if (uri == null || uri.isEmpty()) return null;
        // /tus/uuid 형태에서 uuid만 추출
        int lastSlash = uri.lastIndexOf("/");
        return lastSlash != -1 ? uri.substring(lastSlash + 1) : uri;
    }


}