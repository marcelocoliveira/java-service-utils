package com.utils.wsutils;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.utils.json.JSONMapper;
import com.utils.json.JSONMapperException;


public class ServiceResponse {

	private boolean returnValue;
	private String message;
	private String stackTrace;
	
	public ServiceResponse (){};
	
	public ServiceResponse (boolean returnValue, String message, String stackTrace) {
		this.message = message;
		this.returnValue = returnValue;
		this.stackTrace = stackTrace;
	}
	
	public ServiceResponse (boolean returnValue, String message) {
		this.message = message;
		this.returnValue = returnValue;
		this.stackTrace = "N/A";
	}
	
	public static ServiceResponse generateErrorResponse(Throwable e) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setReturnValue(false);
		serviceResponse.setMessage(e.getMessage());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		serviceResponse.setStackTrace(sw.toString());
		return serviceResponse;
	}
	
	public static String getSafeServiceResponse (boolean returnValue, String message, String stackTrace) {
		
			return JSONMapper.toString(new ServiceResponse(returnValue, message,  stackTrace));
		
	}
	
	public static String getSafeServiceResponse (boolean returnValue, String message) {
			return JSONMapper.toString(new ServiceResponse(returnValue, message));
	}
	
	public static String getSafeSingleObjectResponse (boolean returnValue, String message, String object) {
		
		try {
			SingleObjectResponse resp = new SingleObjectResponse();
			resp.setMessage(message);
			resp.setReturnValue(returnValue);
			resp.setObject(object);
			return JSONMapper.toString(resp);
		} catch (Exception e) {
			return getJsonResponse(returnValue, message, "");
		}
	}

	public static String getServiceResponse (boolean returnValue, String message, String stackTrace) throws JSONMapperException {
		return JSONMapper.toString(new ServiceResponse(returnValue, message,  stackTrace));
	}
	
	
	public static String getServiceResponse (boolean returnValue, String message) {
			return JSONMapper.toString(new ServiceResponse(returnValue, message));
	}
	
	public static String getSingleObjectResponse (boolean returnValue, String message, String object) throws JSONMapperException {
		SingleObjectResponse resp = new SingleObjectResponse();
		resp.setMessage(message);
		resp.setReturnValue(returnValue);
		resp.setObject(object);
		return JSONMapper.toString(resp);
	}

	public static String getJsonResponse(boolean returnValue, String message, String stackTrace) {
		return "{\"message\":\"" + message + "\",\"stackTrace\":\"" + stackTrace + "\",\"returnValue\":\"" + returnValue + "\"}";
	}
	
	public boolean isReturnValue() {
		return returnValue;
	}

	public void setReturnValue(boolean returnValue) {
		this.returnValue = returnValue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	


	
}
