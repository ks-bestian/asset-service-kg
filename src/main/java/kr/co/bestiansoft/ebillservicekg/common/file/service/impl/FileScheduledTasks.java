package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(FileScheduledTasks.class);
	private final ComFileService storageService;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private EDVHelper edv;

	//@Scheduled(cron = "0 0/5 * * * ?") // 매 5분마다
    ///@Scheduled(fixedRate = 300000) // 300000 밀리초 = 5분  5 60 1000
    @Scheduled(fixedRate = 1000 * 60 * 5) // 300000 밀리초 = 5분  5 60 1000  60 60 1000
    public void edvFileDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("배치 작업이 실행됩니다: " + formattedDate);
        storageService.batchFileDelete();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("배치 작업이 종료됩니다: " + formattedDate);
    }
}
