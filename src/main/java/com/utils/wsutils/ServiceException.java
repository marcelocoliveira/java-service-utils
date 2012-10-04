package com.utils.wsutils;




public class ServiceException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException (Throwable e){
		super(e);
		
	}
	
	public ServiceException (String str){
		super(str);
		
	}
	
	
}
