package com.utils;

public interface SystemConfiguration {
	
	// Service names
	public static final String ACCOUNT = "ACCOUNT";
	public static final String METADATA = "METADATA";
	public static final String MIDLINK = "MIDLINK";
	public static final String MIDLINK_CRON_EXP = "CRON_EXP";
	
	public static final String TRACKING = "TRACKING";
	public static final String NETTY = "NETTY";
	public static final String NETTY_DEV_EVENTS = "NETTY_DEV_EVENTS";
	public static final String ASYNC = "ASYNC";
	public static final String APPAPI = "APPAPI";
	public static final String DATABASE = "DATABASE";
	public static final String ACTIVE_MQ = "ACTIVE_MQ";
	public static final String MSG_AUDIT = "MSG_AUDIT";
	public static final String MEMCACHED = "MEMCACHED";
	public static final String UI_ALERT = "UI_ALERT";
	
	public static final String NOTIFICATION = "NOTIFICATION";
	public static final String NOTIFICATION_CRON_EXP = "NOTIFICATION_CRON_EXP";
	public static final String NOTIFICATION_DEFAULT_SUBJECT = "DEFAULT_SUBJECT";
	public static final String NOTIFICATION_MAX_RETRIES = "MAX_RETRIES";
	public static final String NOTIFICATION_RETRY_AFTER_MINS = "RETRY_AFTER_MINS";
	public static final String NOTIFICATION_TWILIO_API_HOST = "TWILIO_API_HOST";
	public static final String NOTIFICATION_TWILIO_SMS_PATH = "TWILIO_SMS_PATH";
	public static final String NOTIFICATION_TWILIO_FROM_SMS = "TWILIO_FROM_SMS";
	
	// the period during we calculate the quota, example 30 in: "15 messages in 30 days" 
	public static final String NOTIFICATION_QUOTA_PERIOD = "NOTIFICATION_QUOTA_PERIOD";
	// the number of messages allowed, example 15 in: "15 messages in 30 days"
	public static final String NOTIFICATION_QUOTA = "NOTIFICATION_QUOTA";
	
	public static final String MIDLINK_DIST_THRESHOLD = "DIST_THRESHOLD";
	public static final String MIDLINK_TIME_THRESHOLD = "TIME_THRESHOLD";
	public static final String MIDLINK_MAX_RETRIES = "MAX_RETRIES";
	public static final String MIDLINK_SWITCH_DRYINPUT = "SWITCH_DRYINPUT";
	
	// Service Properties names
	public static final String URL = "url";
	public static final String PORT = "port";
	
	// convenient method that combines url and port in a URL like String
	public String getEndpointURL(String service);
	
	public String getUrl(String service);
	public String getPort(String service);
	
	// used to retrieve other single configuration parameters  
	public String getConfigElement(String service, String element);

	// used to retrieve other single configuration parameters  
	public String getConfigElement(String service, String element, String defaultValue);

}
