package com.utils;
import com.ocpsoft.pretty.time.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	public static final String DEFAULT_DATE_FORMAT = "EEE, d/MM/yyyy hh:mm:ss z";

	public static int getDiffInMinutes (Timestamp start, Timestamp end){
		long diff = Math.abs(end.getTime () - start.getTime ());
		return  (int) ((diff % (1000*60*60)) / (1000*60));
		
	}
	public static Date getUTCDateAndTime (){
		return new Date(getGMTDateAndTime());
	}
	
	public static Long getGMTDateAndTime (){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return cal.getTimeInMillis();
	}
	
	
	public static int getDiffInMinutes (long start){
		long diff = Math.abs(new Date(System.currentTimeMillis()).getTime () - start);
		return  (int) ((diff % (1000*60*60)) / (1000*60));
	}
	
	public static String getDiffInMillis (long time1, long time2){
		long diff = time1 - time2;
		int seconds = (int) (diff / 1000) % 60 ;
		int minutes = (int) ((diff / (1000*60)) % 60);
		int hours   = (int) ((diff / (1000*60*60)) % 24);
		return hours +":"+ minutes+":"+seconds;
	}
	
	
	public static boolean getDiffInMinutes (Timestamp timestamp, int minutes){
		Timestamp currTimestamp  = new Timestamp(System.currentTimeMillis());
		long value = DateUtil.getDiffInMinutes(timestamp, currTimestamp);
		return (value > minutes);
	}
	
	public static Timestamp timestampDiffHours (int hours){
		Timestamp currTimestamp  = new Timestamp(System.currentTimeMillis());
		long diff = currTimestamp.getTime() - (hours * (1000*60*60));
		return new Timestamp(diff);
	}
	
	public static Date getDiffInHours (long hours){
		Timestamp currTimestamp  = new Timestamp(System.currentTimeMillis());
		long diff = currTimestamp.getTime() - (hours * (1000*60*60));
		return new Date(diff);
	}
	
	public static Date getDate (String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date d = df.parse(date);
            return d; 
        } catch (ParseException e) {
            return new Date();
        }
	}
	
	
	public static String formatDate (String date) {
		DateFormat df = new SimpleDateFormat("MMddyyyy");
        try {
            Date d = df.parse(date);
            return getDate(d.getTime()); 
        } catch (ParseException e) {
            return getDate(new Date().getTime());
        }
	}
	
	public static Calendar getDate (String date, String format) {
		try {
			Calendar cal = new GregorianCalendar();
        	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        	dateFormat.setCalendar(cal); 
        	dateFormat.parse(date);
			return dateFormat.getCalendar();
		} catch (ParseException e) {
			return null;
		}
   }
	
	
	
	public static String getDate (Long date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
          return  df.format(new Date(date)).toString();
        
	}
	
	public static String getYesterday () {
		  Calendar cal = Calendar.getInstance();
          DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
          cal.add(Calendar.DATE, -1);
         return dateFormat.format(cal.getTime());  
    }
	
	public static String getToday () {
		  Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
       return dateFormat.format(cal.getTime());  
  }
	
	
	public static String[] getAllTimezones(){
		
		String[] allTimeZones = TimeZone.getAvailableIDs();
		return allTimeZones;
	}
	

	
	public static String applyTimeZoneOffset(long gmtTimestamp, TimeZone tZone){
		
		return applyTimeZoneOffset(gmtTimestamp, tZone, DEFAULT_DATE_FORMAT);
		
	}
	
	public static String applyTimeZoneOffset(long gmtTimestamp, TimeZone tZone, String simpleDateFormat){
			
			// get a calendar object
			Calendar c = Calendar.getInstance();
			
			// set the time in millis
			c.setTimeInMillis(gmtTimestamp);
			
			// set the timezone of the calendar
			c.setTimeZone(tZone);
			
			// create the formatter
			java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(simpleDateFormat);
			
			// set the timezone of the formatter
			dateFormatter.setTimeZone(tZone);
			
			// format the Calendar
			return dateFormatter.format(c.getTime());
		
		
	}
	
	public static String applyTimeZoneOffset(long gmtTimestamp, TimeZone tZone, String simpleDateFormat, Locale locale){
		
		// get a calendar object
		Calendar c = Calendar.getInstance();
		
		// set the time in millis
		c.setTimeInMillis(gmtTimestamp);
		
		// set the timezone of the calendar
		c.setTimeZone(tZone);
		
		// create the formatter
		java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat(simpleDateFormat, locale);
		
		// set the timezone of the formatter
		dateFormatter.setTimeZone(tZone);
		
		// format the Calendar
		return dateFormatter.format(c.getTime());
	
	
         }
         
         public static String applyTimeZoneOffsetPretty(long gmtTimestamp, TimeZone tZone, PrettyTime prettyTime){
         	 
         	 
			// get a calendar object
			Calendar c = Calendar.getInstance();
			
			// set the time in millis
			c.setTimeInMillis(gmtTimestamp);
			
			// set the timezone of the calendar
			c.setTimeZone(tZone);
			
			Date dateConvert = new Date(c.getTimeInMillis());
			
						
			// format the Date
			return prettyTime.format(dateConvert);
         	 
         }
         
	
}
