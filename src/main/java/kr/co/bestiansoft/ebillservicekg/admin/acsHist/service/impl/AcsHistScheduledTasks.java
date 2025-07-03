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
	
	//@Scheduled(cron = "0 0/5 * * * ?") // Every 5 minutes
    ///@Scheduled(fixedRate = 300000) // 300000 milliseconds = 5 minutes  5 60 1000
//    @Scheduled(fixedRate = 3600000) // 300000 milliseconds = 5 minutes5 60 1000  60 60 1000
	@Scheduled(cron = "0 0 0 * * *") // Run at 0 o'clock every day
    public void oldAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("The arrangement of connection history is executed: " + formattedDate);
        acsHistMapper.deleteOldAcsHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("The arrangement of connection history is terminated: " + formattedDate);
    }
	
	//@Scheduled(cron = "0 0/5 * * * ?") //Every 5 minutes
    ///@Scheduled(fixedRate = 300000) //300000 milliseconds = 5 minutes  5 60 1000
//    @Scheduled(fixedRate = 3600000) //300000 milliseconds = 5 minutes 5 60 1000  60 60 1000
	@Scheduled(cron = "0 0 0 * * *") //Run at 0 o'clock every day
    public void oldEbsAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("ebs The arrangement of connection history is executed: " + formattedDate);
        acsHistMapper.deleteOldBillHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("EBS access history arrangement is terminated: " + formattedDate);
    }
}
