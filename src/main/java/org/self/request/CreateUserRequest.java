package org.self.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = false)
@Setter @Getter
public class CreateUserRequest{

	private String username;
	private String password;
	private String email;
	private Short enabled;

}
