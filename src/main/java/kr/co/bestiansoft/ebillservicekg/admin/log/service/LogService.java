package kr.co.bestiansoft.ebillservicekg.admin.log.service;

import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogCreate;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogList;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogRead;
import kr.co.bestiansoft.ebillservicekg.admin.log.domain.Logs;

import java.util.List;


public interface LogService {

	LogList logList(LogRead logRead);
	void createLog(LogCreate create);


}
