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

    /**
     * A scheduled task that executes every 5 minutes to delete expired or unnecessary files from the storage.
     *
     * This method uses the {@code batchFileDelete} method of the {@code storageService} to perform
     * the deletion operation and logs the start and end timestamps of the batch process.
     *
     * The scheduling interval is fixed and set to 5 minutes using the {@code @Scheduled} annotation.
     *
     * The method executes in the following steps:
     * 1. Logs the start time of the batch process.
     * 2. Invokes the {@code batchFileDelete} method from {@code storageService} to delete files.
     * 3. Logs the end time of the batch process.
     */
	//@Scheduled(cron = "0 0/5 * * * ?") // hawk 5Every minute
    ///@Scheduled(fixedRate = 300000) // 300000 Millycho = 5minute  5 60 1000
    @Scheduled(fixedRate = 1000 * 60 * 5) // 300000 Millycho = 5minute  5 60 1000  60 60 1000
    public void edvFileDeleteTask() {
    	String formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("Batch work is executed: " + formattedDate);
        storageService.batchFileDelete();
        formattedDate = dateFormat.format(new Date(System.currentTimeMillis()));
        logger.info("Batch work is terminated:" + formattedDate);
    }
}
