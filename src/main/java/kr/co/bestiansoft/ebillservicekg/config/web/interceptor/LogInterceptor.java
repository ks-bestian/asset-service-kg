package kr.co.bestiansoft.ebillservicekg.config.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.bestiansoft.ebillservicekg.admin.acsHist.service.AcsHistService;
import kr.co.bestiansoft.ebillservicekg.admin.acsHist.vo.AcsHistVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private final AcsHistService acsHistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("===============================================");
        log.info("==================== BEGIN ====================");
        log.info("Request METHOD ===> {}", request.getMethod());
        log.info("Request URI ===> {}", request.getRequestURI());

        String userId = "";
        String accessIp = request.getRemoteAddr();
        String reqURL = request.getRequestURI();
        String reqMethod = request.getMethod();
        String reqServletPath = request.getServletPath();
        AcsHistVo ahVo = new AcsHistVo();
        
        if(       !reqServletPath.contains("/api/accessHist")
                &&!reqServletPath.contains("/api/save-route")
                &&!reqServletPath.contains("/api/lngCode")
                &&!reqServletPath.contains("/api/menus/menuBreadcrumbs")
                &&!reqServletPath.contains("/ws")
        ) {//로그조회는 제외
            if(!reqServletPath.contains("/login")) {
            	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            	if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            		userId = new SecurityInfoUtil().getAccountId();	
            	}
            }

            ahVo = new AcsHistVo();
            ahVo.setRegId(userId);
            ahVo.setReqUrl(reqURL);
            ahVo.setReqMethod(reqMethod);
            ahVo.setAcsIp(accessIp);
            acsHistService.createAcsHist(ahVo);
            
            if(reqURL.startsWith("/bill/") && !reqMethod.equals("GET")) {
            	acsHistService.createBillHist(ahVo);
            }
        }

        return true;
    }
}
