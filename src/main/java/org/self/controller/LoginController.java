package org.self.controller;

import java.util.Objects;

import org.self.model.User;
import org.self.request.LoginRequest;
import org.self.service.IUserService;
import org.self.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
public class LoginController {
	@Autowired
    private SecurityService securityService;
	@Autowired
    private IUserService userService;

    @PostMapping(name="/perform_login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) throws DisabledException,BadCredentialsException{
    	String username = request.getUsername();
    	String password = request.getPassword();
    	Objects.requireNonNull(username);
		Objects.requireNonNull(password);
        log.info("Got login request {}",request);
		try {
			securityService.autoLogin(username, password);
		} catch (DisabledException ex) {
			throw new DisabledException("USER_DISABLED", ex);
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		}
		return new ResponseEntity<User>(userService.findByUserName(username),HttpStatus.OK);
    }
    
    

}
