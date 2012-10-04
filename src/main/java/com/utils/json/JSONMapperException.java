package com.utils.json;

public class JSONMapperException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JSONMapperException(String message) {
             super("JSONMapperException - "+message);
     }

	public JSONMapperException() {
		super();
	}

	public JSONMapperException(String message, Throwable e) {
		super(message, e);
	}

	public JSONMapperException(Throwable e) {
		super(e);
	}

}
