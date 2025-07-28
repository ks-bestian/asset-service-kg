package kr.co.bestiansoft.ebillservicekg.config.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final TokenBlacklist tokenBlacklist;

    // Logic in the actual filterli
    // Performing a role to store the authentication information of tokens in SecurityContext
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

            if (tokenBlacklist==null||!tokenBlacklist.isBlacklisted(jwt)) {

            	Authentication authentication = tokenProvider.getAuthentication(jwt);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("I saved the '{}' authentication information in Security Context, URI: {}", authentication.getName(), requestURI);
            } else {
                logger.debug("JWT token on the blacklist, URI: {}", requestURI);
            }

        } else {
            logger.debug("valid JWT Token doesn't exist, uri: {}", requestURI);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header at token Information Remove For Method
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }



}