package kr.co.bestiansoft.ebillservicekg.config.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import kr.co.bestiansoft.ebillservicekg.common.errLog.service.ErrLogService;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	
	@Autowired
	private ErrLogService errLogService;
	
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
    	
    	errLogService.saveErrLog(request, HttpServletResponse.SC_FORBIDDEN, "FORBIDDEN");
    	
        //When trying to approach without the necessary authority 403
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}