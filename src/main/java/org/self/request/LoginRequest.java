package org.self.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Setter @Getter @ToString
public class LoginRequest {
	private String username;
	private String password;
}
