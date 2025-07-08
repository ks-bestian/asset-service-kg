package kr.co.bestiansoft.ebillservicekg.common.errLog.service.impl;


import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.common.errLog.repository.ErrLogMapper;
import kr.co.bestiansoft.ebillservicekg.common.errLog.service.ErrLogService;
import kr.co.bestiansoft.ebillservicekg.common.errLog.vo.ErrLogVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ErrLogServiceImpl implements ErrLogService {
    private final ErrLogMapper errLogMapper;

	/**
	 * Inserts an error log into the repository.
	 *
	 * @param errLogVo the error log data to be inserted
	 */
	@Override
	public void insertErrLog(ErrLogVo errLogVo) {
		errLogMapper.insertErrLog(errLogVo);
	}

	/**
	 * Saves an error log by collecting request and user information and delegating
	 * the data to be stored in the repository.
	 *
	 * @param request the HTTP request containing details such as IP, URL, and method
	 * @param errCd the error code representing the specific error encountered
	 * @param errMsg the error message providing details about the encountered issue
	 */
	@Override
    public void saveErrLog(HttpServletRequest request, int errCd, String errMsg) {
    	String accessIp = request.getRemoteAddr();
        String reqURL = request.getRequestURI();
        String reqMethod = request.getMethod();
        String reqServletPath = request.getServletPath();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication == null ? null : new SecurityInfoUtil().getAccountId();
        
    	ErrLogVo errLogVo = new ErrLogVo();
    	errLogVo.setAcsIp(accessIp);
    	errLogVo.setRqtUrl(reqURL);
    	errLogVo.setRqtMthd(reqMethod);
    	errLogVo.setErrCd(errCd);
    	errLogVo.setErrMsg(errMsg);
    	errLogVo.setRegId(userId);
    	
    	this.insertErrLog(errLogVo);
    }

}
