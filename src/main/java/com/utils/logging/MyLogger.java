package com.utils.logging;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

public class MyLogger {

	private Class<?> className = MyLogger.class;

	private Logger qLog = null;

	public MyLogger() {
		qLog = Logger.getLogger(className.getCanonicalName());
	}
	
	public MyLogger(Class <?> className) {
		qLog = Logger.getLogger(className.getCanonicalName());
	}
	
	
	public MyLogger(Class<?> className, String file){
		qLog = Logger.getLogger(className.getCanonicalName());
		FileAppender appender = null;
		try {
			PatternLayout layout =  new PatternLayout();
			layout.setConversionPattern("%d [%t] %-5p %c - %m%n");
			layout.activateOptions();
			appender = new FileAppender(layout, file.toLowerCase() + ".out");
			qLog.addAppender(appender);
		} catch (IOException e) {
			// unable to create a file.. don't create appender.
		}
		
	}
	
	public MyLogger(String id, String file){
		qLog = Logger.getLogger(id);
		FileAppender appender = null;
		try {
			PatternLayout layout =  new PatternLayout();
			layout.setConversionPattern("%d [%t] %-5p %c - %m%n");
			layout.activateOptions();
			appender = new FileAppender(layout, file);
			qLog.addAppender(appender);
		} catch (IOException e) {
			// unable to create a file.. don't create appender.
		}
	}
	
	public MyLogger(String className) {
		qLog = Logger.getLogger(className);
	}

	public void logInfo(String str) {
		qLog.info(str);
	}

	public void logDebug(String str) {
		qLog.debug(str);
	}

	public void logError(String str) {
		qLog.error(str);
	}

	public void logError(String str, Throwable t) {
		qLog.error(str, t);
	}
	
	public void logExecutionTime( String methodName, long startTime, long endTime){
		                                     
		String str = "PERFORMANCE_METHOD=" + methodName + " PERFORMANCE_DATA="  + (endTime - startTime);
		//String str = "PERFORMANCE_DATA|" + methodName + "|" + (endTime - startTime);
		qLog.info(str);

	}

	public void logExecutionTime( String methodName, long startTime){
		
		long endTime = System.currentTimeMillis();
		String str = "PERFORMANCE_METHOD=" + methodName + " PERFORMANCE_DATA="  + (endTime - startTime);
		//String str = "PERFORMANCE_DATA|" + methodName + "|" + (endTime - startTime);
		qLog.info(str);

	}

}