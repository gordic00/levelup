package com.webapps.levelup.configuration;

import com.webapps.levelup.exception.CustomException;
import com.webapps.levelup.helper.TokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.webapps.levelup.constant.Token.REFRESH_TOKEN;
import static com.webapps.levelup.constant.Token.TOKEN;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenAuthAspect {
    private final TokenHelper tokenHelper;

    @Around("@annotation(com.webapps.levelup.configuration.TokenAuth)")
    public Object authentication(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) joinPoint.getArgs()[0];
        } catch (ClassCastException e) {
            throw new CustomException("Missing request.");
        }

        String token = null;
        String refreshToken = null;

        if (request.getHeader(TOKEN) != null) {
            token = request.getHeader(TOKEN);
        }
        if (request.getHeader(REFRESH_TOKEN) != null) {
            refreshToken = request.getHeader(REFRESH_TOKEN);
        }

        if (token == null && refreshToken == null) {
            log.error("Your session has expired");
            throw new CustomException("Your session has expired.");
        }
        try {
            if (token != null) {
                tokenHelper.validateToken(token);
            } else {
                tokenHelper.validateToken(refreshToken);
            }
        } catch (ExpiredJwtException ex) {
            log.error("Token expired. {}", ex.getMessage(), ex);
            tokenHelper.refreshToken(refreshToken, ex);
        }
        return joinPoint.proceed();
    }
}
