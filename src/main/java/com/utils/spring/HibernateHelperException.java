package com.utils.spring;

public class HibernateHelperException  extends Exception {

	private static final long serialVersionUID = 1L;

	public HibernateHelperException (String message) {
         super(message);
     }
	
	public HibernateHelperException (Throwable  e) {
         super(e);
    }

}
