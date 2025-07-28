package kr.co.bestiansoft.ebillservicekg.common.errLog.service;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.bestiansoft.ebillservicekg.common.errLog.vo.ErrLogVo;

public interface ErrLogService {
	void insertErrLog(ErrLogVo errLogVo);
	void saveErrLog(HttpServletRequest request, int errCd, String errMsg);
}
