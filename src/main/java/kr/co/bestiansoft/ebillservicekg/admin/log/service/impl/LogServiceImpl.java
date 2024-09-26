package kr.co.bestiansoft.ebillservicekg.admin.log.service.impl;

import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogCreate;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogList;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogRead;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.Logs;
import kr.co.bestiansoft.ebillservicekg.admin.log.repository.LogMapper;
import kr.co.bestiansoft.ebillservicekg.admin.log.repository.LogRepository;
import kr.co.bestiansoft.ebillservicekg.admin.log.repository.entity.LogEntity;
import kr.co.bestiansoft.ebillservicekg.admin.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

	private final LogMapper logMapper;
	private final LogRepository logRepository;

	@Override
	public LogList logList(LogRead logRead) {
		List<Logs> logs = logMapper.selectLogs(logRead);
		Long count = logMapper.selectLogCount(logRead);

		return LogList.builder().logs(logs).count(count).build();
	}

	@Transactional
	@Override
	public void createLog(LogCreate create) {

		logRepository.save(LogEntity.from(create));

	}
}
