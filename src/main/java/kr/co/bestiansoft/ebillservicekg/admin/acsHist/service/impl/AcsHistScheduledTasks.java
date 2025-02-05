package kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.repository.AcsHistMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AcsHistScheduledTasks {

	private static final Logger logger = LoggerFactory.getLogger(AcsHistScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private AcsHistMapper acsHistMapper;
	
	//@Scheduled(cron = "0 0/5 * * * ?") // 매 5분마다
    ///@Scheduled(fixedRate = 300000) // 300000 밀리초 = 5분  5 60 1000
//    @Scheduled(fixedRate = 3600000) // 300000 밀리초 = 5분  5 60 1000  60 60 1000
	@Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void oldAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("접속이력 정리 배치 작업이 실행됩니다: " + formattedDate);
        acsHistMapper.deleteOldAcsHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("접속이력 정리 배치 작업이 종료됩니다: " + formattedDate);
    }
	
	//@Scheduled(cron = "0 0/5 * * * ?") // 매 5분마다
    ///@Scheduled(fixedRate = 300000) // 300000 밀리초 = 5분  5 60 1000
//    @Scheduled(fixedRate = 3600000) // 300000 밀리초 = 5분  5 60 1000  60 60 1000
	@Scheduled(cron = "0 0 0 * * *") // 매일 0시에 실행
    public void oldEbsAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("ebs 접속이력 정리 배치 작업이 실행됩니다: " + formattedDate);
        acsHistMapper.deleteOldBillHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("ebs 접속이력 정리 배치 작업이 종료됩니다: " + formattedDate);
    }
}
