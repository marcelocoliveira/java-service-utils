package com.utils;

public class SystemConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemConfigurationException() {
	}

	public SystemConfigurationException(String message) {
		super(message);
	}

	public SystemConfigurationException(Throwable e) {
		super(e);
	}

	public SystemConfigurationException(String message, Throwable e) {
		super(message, e);
	}

}
