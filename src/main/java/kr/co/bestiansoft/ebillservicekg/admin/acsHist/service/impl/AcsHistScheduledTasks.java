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


    /**
     * Scheduled task that deletes old connection history records from the database.
     *
     * This task is configured to run daily at midnight (00:00:00).
     * It logs the start and end of the deletion process and invokes the database operation
     * to clean up outdated access history records.
     *
     * Dependencies:
     * - Utilizes the acsHistMapper to perform the actual deletion of records by invoking
     *   the {@code deleteOldAcsHist} method.
     *
     * Logging:
     * - Logs the start and end timestamps of the task execution.
     */
    @Scheduled(cron = "0 0 0 * * *") // Run at 0 o'clock every day
    public void oldAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("The arrangement of connection history is executed: " + formattedDate);
        acsHistMapper.deleteOldAcsHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("The arrangement of connection history is terminated: " + formattedDate);
    }

    /**
     * Scheduled task that deletes old billing access history records from the database.
     *
     * This task is configured to run daily at midnight (00:00:00).
     * It logs the start and end of the deletion process and invokes the database operation
     * to clean up outdated billing history records.
     *
     * Dependencies:
     * - Utilizes the acsHistMapper to perform the deletion of records by calling the
     *   {@code deleteOldBillHist} method.
     *
     * Logging:
     * - Logs the start and end times of the task execution process.
     */
	@Scheduled(cron = "0 0 0 * * *") //Run at 0 o'clock every day
    public void oldEbsAcsHistDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("ebs The arrangement of connection history is executed: " + formattedDate);
        acsHistMapper.deleteOldBillHist();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("EBS access history arrangement is terminated: " + formattedDate);
    }
}
