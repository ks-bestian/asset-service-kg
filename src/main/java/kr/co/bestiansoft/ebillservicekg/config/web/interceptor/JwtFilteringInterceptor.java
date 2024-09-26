package kr.co.bestiansoft.ebillservicekg.config.web.interceptor;

import kr.co.bestiansoft.ebillservicekg.config.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class JwtFilteringInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String reqServletPath = request.getServletPath();

        String jwt = resolveToken(request);

        if(        reqServletPath.contains("/api/save-route")
                || reqServletPath.contains("/api/authenticate")
                || reqServletPath.contains("/api/lngCode")
                || reqServletPath.contains("/api/menus/menuBreadcrumbs")
        ) {

            return true;

        }else {
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                return true;

            } else {
                String reqURL = request.getRequestURI();
                log.warn("유효한 JWT 토큰이 없습니다, uri: {}", reqURL);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효한 JWT 토큰이 없습니다");
                return false;
            }
        }

    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
