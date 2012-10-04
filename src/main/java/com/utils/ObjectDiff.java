package com.utils;

import java.lang.reflect.Field;

import com.utils.logging.MyLogger;

public class ObjectDiff {

	private static MyLogger logger = new MyLogger(ObjectDiff.class);
	
	public static Object process (Object from , Object to) {
		logger.logInfo("Process object diff");	
		Class<?> fromClass = from.getClass();
		Class<?> toClass = to.getClass();
     try{
	    for(Field field : toClass.getDeclaredFields()) {
	    		  field.setAccessible(true);
	    	      String name = field.getName();
	    	      Object value = field.get(to);
	    	      logger.logInfo("field name: "+ name +" value: "+value);
	              if (value == null){
	            	  Field fromField =fromClass.getDeclaredField(name);
	            	  fromField.setAccessible(true);
	            	  Object oValue = fromField.get(from);
	            	  logger.logInfo("Replace "+name+" with value:  "+ oValue);
	            	  field.set(to, oValue);
	              }
	    }
	    
	    for(Field field : toClass.getSuperclass().getDeclaredFields()) {
  		  field.setAccessible(true);
  	      String name = field.getName();
  	      Object value = field.get(to);
  	      logger.logInfo("field name: "+ name +" value: "+value);
            if (value == null){
          	  Field fromField =fromClass.getSuperclass().getDeclaredField(name);
          	  fromField.setAccessible(true);
          	  Object oValue = fromField.get(from);
          	  logger.logInfo("Replace "+name+" with value:  "+ oValue);
          	  field.set(to, oValue);
            }
  }
	} catch (IllegalArgumentException e) {
		logger.logError(e.getMessage(), e);
		return from;
	} catch (IllegalAccessException e) {
		logger.logError(e.getMessage(), e);
		return from;
	} catch (SecurityException e) {
		logger.logError(e.getMessage(), e);
		return from;
	} catch (NoSuchFieldException e) {
		logger.logError(e.getMessage(), e);
		return from;
	}
	    return to;
	}
	
	
}
