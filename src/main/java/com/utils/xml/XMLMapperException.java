package com.utils.xml;

public class XMLMapperException  extends Exception {
	
	private static final long serialVersionUID = 1L;

	public XMLMapperException(String message) {
             super("XMLMapperException - "+message);
     }
	
	public XMLMapperException(Throwable e) {
        super("XMLMapperException - "+ e.getMessage(), e);
}

}

