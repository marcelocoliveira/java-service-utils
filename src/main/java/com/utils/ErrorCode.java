package com.utils;

import java.util.Map;

public class ErrorCode {

	private Map<String, String> error;

	public String getError (String key) {
		if (error.containsKey(key)) return error.get(key);
		return null;
	}
	
	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}
	
	
}
