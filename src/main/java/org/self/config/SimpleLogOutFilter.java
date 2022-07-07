package org.self.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SimpleLogOutFilter extends LogoutFilter {
	
	private LogoutHandler handler;
	private LogoutSuccessHandler logoutSuccessHandler;

	public SimpleLogOutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl,handlers);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (requiresLogout(httpRequest,httpResponse)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (log.isDebugEnabled()) {
				log.debug("Logging out {}",auth);
			}
			this.handler.logout((HttpServletRequest) request, (HttpServletResponse) response, auth);
			this.logoutSuccessHandler.onLogoutSuccess(httpRequest,httpResponse, auth);
			Cookie sessionCookie = new Cookie("JSESSIONID", "");
			sessionCookie.setMaxAge(0);
			httpResponse.addCookie(sessionCookie);
			return;
		}
		chain.doFilter(request, response);
	}

}
