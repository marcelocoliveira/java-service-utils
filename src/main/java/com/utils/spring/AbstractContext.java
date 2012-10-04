package com.utils.spring;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import com.utils.logging.MyLogger;


public abstract class AbstractContext {
      
	private static MyLogger logger = new MyLogger(AbstractContext.class);
    /*
     * This must be protected instead of a private in each subclass. 
     * All beans call the static getContext method which causes issues in unit tests vs Actual deployment.
     * 
     */
    protected static ApplicationContext sContext;
    protected static SessionFactory sFactory;
    
    public static ApplicationContext getContext() {
    	return sContext;
    }
    
    public static SessionFactory getSessionFactory() {
    	return sFactory;
    }

    public static void start() {
    	logger.logDebug("Context started");
    }
    
    public static void stop() {
    	logger.logDebug("Context stopped"); 
    }
}