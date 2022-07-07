package org.self.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter @Getter
public class JwtRequest{
	private String username;
	private String password;
	
}
