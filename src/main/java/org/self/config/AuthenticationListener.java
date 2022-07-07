package org.self.config;

import java.util.Optional;

import org.self.model.LoginEvent;
import org.self.repository.LoginEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {
    @Autowired
	private LoginEventRepository loginEventRepository;
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		log.info("called login event listener {}",event.getClass());
		if(event instanceof AuthenticationSuccessEvent) {
            log.info("login success");
			AuthenticationSuccessEvent successEvent = (AuthenticationSuccessEvent)event;
			LoginEvent loginEvent = new LoginEvent();
			loginEvent.setUserName(successEvent.getAuthentication().getName());
			loginEvent.setLoginSuccess((short)1);
			loginEvent.setEventName(EventType.LOGIN_SUCCESS.getValue());
			loginEventRepository.save(loginEvent);
		}
		else if(event instanceof AuthenticationFailureBadCredentialsEvent) {
			log.info("login failed");
			AuthenticationFailureBadCredentialsEvent failureEvent = (AuthenticationFailureBadCredentialsEvent)event;
			LoginEvent loginEvent = new LoginEvent();
			loginEvent.setUserName(failureEvent.getAuthentication().getName());
			loginEvent.setLoginSuccess((short)0);
			loginEvent.setEventName(EventType.LOGIN_FAILED.getValue());
			AuthenticationException authEx = failureEvent.getException();
			if(Optional.ofNullable(authEx).isPresent()) {
				loginEvent.setReason(authEx.getMessage());
			}
			loginEventRepository.save(loginEvent);
		}
		
	}

}
