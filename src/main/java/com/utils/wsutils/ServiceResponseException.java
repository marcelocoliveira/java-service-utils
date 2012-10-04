package com.utils.wsutils;

import java.io.PrintWriter;
import java.io.StringWriter;
import com.utils.json.JSONMapper;

public class ServiceResponseException  {

	public static String generateResponse(Throwable e) {
		ServiceResponse serviceResponse = new ServiceResponse();
		serviceResponse.setReturnValue(false);
		serviceResponse.setMessage(e.getMessage());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		serviceResponse.setStackTrace(sw.toString());
		return JSONMapper.toString(serviceResponse);
	}
}
