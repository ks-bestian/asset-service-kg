package kr.co.bestiansoft.ebillservicekg.config.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

//    private final LogService logService;

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

        //getCurrentUserId
        if(       !reqServletPath.contains("/api/accessHist")
                &&!reqServletPath.contains("/api/save-route")
                &&!reqServletPath.contains("/api/lngCode")
                &&!reqServletPath.contains("/api/menus/menuBreadcrumbs")
        ) {//로그조회는 제외
//            if(!reqServletPath.contains("/api/authenticate")) {
//                userId = new SecurityInfoUtil().getAccountId();
//            }

            //reqMethod
//            logService.createLog(new LogCreate(userId, accessIp, reqURL, reqMethod));
        }

        return true;
    }
}
