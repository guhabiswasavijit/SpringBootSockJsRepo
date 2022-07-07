package org.self.config;

public enum EventType {
	LOGIN_SUCCESS("LOGIN_SUCCESS"),
	LOGIN_FAILED("LOGIN_FAILED");
    private String type;
	EventType(String name) {
		type=name;
	}
	public String getValue() {
		return this.type;
	}
}
