package org.self.config;

import org.self.controller.UserController;
import org.self.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
class DatabaseMocker {
	@Value("${create.demo.user}")
	private boolean createUser;
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	CommandLineRunner initDatabase(UserController userController) {
		return args -> {
			CreateUserRequest cnuRequest = new CreateUserRequest();
			cnuRequest.setUsername("guest");
			cnuRequest.setPassword("guest123");
			cnuRequest.setEnabled((short) 1);
			cnuRequest.setEmail("guest@devil.com");
			if(createUser) {
				userController.createUser(cnuRequest);
			}
		};
	}
}
