package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.common.file.service.TusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
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

	public String uploadTus(HttpServletRequest request, HttpServletResponse response) {

	    
        try {
            // 오늘 날짜를 yyyyMMdd 형식으로 생성
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String savePath = Paths.get(fileUploadDir, "mnl", today).toString(); // 예: /upload/mnl/20250803

            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs(); // 폴더 없으면 생성
            }

            // tus 업로드 서비스 구성
            TusFileUploadService tusService = new TusFileUploadService().withStoragePath(savePath);

            // 요청 처리
            TusServletRequest tusRequest = new TusServletRequest(request);
            TusServletResponse tusResponse = new TusServletResponse(response);

            tusService.process(tusRequest, tusResponse);

            return "Tus Upload OK";
        } catch (Exception e) {
            log.error("Tus upload failed", e);
            throw new RuntimeException("Tus upload failed", e);
        }
	}
}