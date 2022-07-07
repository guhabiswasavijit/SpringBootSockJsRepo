package org.self.service;

public interface SecurityService {
	boolean isAuthenticated();
    boolean autoLogin(String username, String password);
}
