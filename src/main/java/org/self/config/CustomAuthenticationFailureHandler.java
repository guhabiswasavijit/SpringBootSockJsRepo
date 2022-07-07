package org.self.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException {
    	 String message = ex.getMessage();
    	 StringBuffer loginFailedUrl = new StringBuffer();
    	 loginFailedUrl.append("/login?error=true&errorMsg=");
    	 loginFailedUrl.append(message);
    	 String redirectUrl = URLEncoder.encode(loginFailedUrl.toString(),StandardCharsets.UTF_8.toString());
         log.info("Authentication failed {}",redirectUrl);
         httpServletResponse.sendRedirect(redirectUrl);
    }
}