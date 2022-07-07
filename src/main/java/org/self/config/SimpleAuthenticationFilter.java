package org.self.config;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public SimpleAuthenticationFilter(AuthenticationManager auth) {
		super(auth);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("Called SimpleAuthenticationFilter");
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);
		Authentication auth = this.getAuthenticationManager().authenticate(authRequest);
		return auth;
	}

	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		username = Optional.ofNullable(username).orElse("");
		password = Optional.ofNullable(password).orElse("");
		log.info("Called SimpleAuthenticationFilter {} {}", username, password);
		return new UsernamePasswordAuthenticationToken(username, password);
	}

}