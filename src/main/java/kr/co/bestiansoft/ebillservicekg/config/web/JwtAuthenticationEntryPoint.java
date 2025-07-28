package kr.co.bestiansoft.ebillservicekg.config.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.bestiansoft.ebillservicekg.common.errLog.service.ErrLogService;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ErrLogService errLogService;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

    	errLogService.saveErrLog(request, HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
    	//401 when trying to approach without providing valid credentials
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}