package org.self.controller;

import org.self.request.LogOutRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LogoutController {
	
	@PostMapping(path = "/perform_logout") 
	public ResponseEntity<String> logout(@RequestBody LogOutRequest request){
		 return new ResponseEntity<String>("Successfully logged out of the system",HttpStatus.OK);
		 
	 }

}
