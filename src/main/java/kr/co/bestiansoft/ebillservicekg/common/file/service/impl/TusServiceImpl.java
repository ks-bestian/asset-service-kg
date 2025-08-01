package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

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
	        // tus 서버를 초기화 (파일 저장 경로 지정)
	        TusFileUploadService tusService = new TusFileUploadService();

	        // 업로드된 파일 저장 경로
	        String uploadDir = fileUploadDir+"\\mnl";
	        tusService.withStoragePath(uploadDir);

	        // 요청 핸들링
	        TusServletRequest tusRequest = new TusServletRequest(request);
	        TusServletResponse tusResponse = new TusServletResponse(response);

	        tusService.process(tusRequest, tusResponse);

	        return "Tus Upload OK";
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Tus upload failed", e);
	    }
	}
}