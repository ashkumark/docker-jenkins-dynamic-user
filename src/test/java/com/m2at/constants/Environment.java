package com.m2at.constants;

public enum Environment {

	BROWSER("chrome"),
	URL("https://www.google.com");
	
	private String value;
	
	private Environment(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
