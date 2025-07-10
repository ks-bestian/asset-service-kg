package kr.co.bestiansoft.ebillservicekg.common.errLog.service.impl;


import jakarta.servlet.http.HttpServletRequest;

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

	@Override
	public void insertErrLog(ErrLogVo errLogVo) {
		errLogMapper.insertErrLog(errLogVo);
	}
	
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
