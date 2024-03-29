package com.utils;


public enum EventType {
	
	UPDATE_ACTIVITY_REPORT ("UPDATE_ACTIVITY_REPORT"),
	UPDATE_ASYNC_AUDIT ("UPDATE_ASYNC_AUDIT"),
	UPDATE_DEVICE_HISTORY("UPDATE_DEVICE_HISTORY"),
	UPDATE_DEVICE_IDLE_TIME ("UPDATE_DEVICE_IDLE_TIME"),
	UPDATE_DEVICE_STATUS ("UPDATE_DEVICE_STATUS"),
	UPDATE_DISTANCE_GRAPH ("UPDATE_DISTANCE_GRAPH"),
	UPDATE_DRIVER_ALERT ("UPDATE_DRIVER_ALERT"),
	UPDATE_DRIVER_BEHAVIOR ("UPDATE_DRIVER_BEHAVIOR"),
	UPDATE_SPEED_ALERT ("UPDATE_SPEED_ALERT"),
	UPDATE_VEHICLE_FUEL_GRAPH ("UPDATE_VEHICLE_FUEL_GRAPH"),
	UPDATE_VEHICLE_SPEED_GRAPH ("UPDATE_VEHICLE_SPEED_GRAPH"),
	UPDATE_VEHICLE_STATUS ("UPDATE_VEHICLE_STATUS"),
	
	
	ADD_DEVICE ("ADD_DEVICE"),
	UPDATE_DEVICE ("UPDATE_DEVICE"),
	TRANSFER_DEVICE ("TRANSFER_DEVICE"),
	LIST_DEVICE ("LIST_DEVICE"),
	DELETE_DEVICE ("DELETE_DEVICE"),
	GET_DEVICE ("GET_DEVICE"),
	
	ADD_SUBSCRIPTION ("ADD_SUBSCRIPTION"),
	UPDATE_SUBSCRIPTION ("UPDATE_SUBSCRIPTION"),
	DELETE_SUBSCRIPTION ("DELETE_SUBSCRIPTION"),
	
	
	
	ADD_DRIVER ("ADD_DRIVER"),
	UPDATE_DRIVER ("UPDATE_DRIVER"),
	LIST_DRIVER ("LIST_DRIVER"),
	DELETE_DRIVER ("DELETE_DRIVER"),
	GET_DRIVER ("GET_DRIVER"),
	SEARCH_DRIVER("SEARCH_DRIVER"),
	
	ADD_VEHICLE ("ADD_VEHICLE"),
	ADD_VEHICLE_AND_DEVICES ("ADD_VEHICLE_AND_DEVICES"),
	UPDATE_VEHICLE_AND_DEVICES ("UPDATE_VEHICLE_AND_DEVICES"),
	UPDATE_VEHICLE ("UPDATE_VEHICLE"),
	LIST_VEHICLE ("LIST_VEHICLE"),
	DELETE_VEHICLE ("DELETE_VEHICLE"),
	GET_VEHICLE ("GET_VEHICLE"),
	
	ADD_DEVICE_VEHICLE ("ADD_DEVICE_VEHICLE"),
	UPDATE_DEVICE_VEHICLE ("UPDATE_DEVICE_VEHICLE"),
	DELETE_DEVICE_VEHICLE ("DELETE_DEVICE_VEHICLE"),
	LIST_DEVICE_VEHICLE ("LIST_DEVICE_VEHICLE"),
	GET_DEVICE_VEHICLE ("GET_DEVICE_VEHICLE"),
	
	ADD_DRIVER_VEHICLE ("ADD_DRIVER_VEHICLE"),
	UPDATE_DRIVER_VEHICLE ("UPDATE_DRIVER_VEHICLE"),
	DELETE_DRIVER_VEHICLE ("DELETE_DRIVER_VEHICLE"),
	GET_DRIVER_VEHICLE ("GET_DRIVER_VEHICLE"),
	ASSET_DRIVER_VEHICLE ("ASSET_DRIVER_VEHICLE"),
	LIST_DRIVER_VEHICLE ("LIST_DRIVER_VEHICLE"),
	
	ADD_DEFAULT_ALERT ("ADD_DEFAULT_ALERT"),
	UPDATE_DEFAULT_ALERT ("UPDATE_DEFAULT_ALERT"),
	DELETE_DEFAULT_ALERT ("DELETE_DEFAULT_ALERT"),
	LIST_DEFAULT_ALERT ("LIST_DEFAULT_ALERT"),
	
	ADD_GEOFENCE_ALERT ("ADD_GEOFENCE_ALERT"),
	UPDATE_GEOFENCE_ALERT ("UPDATE_GEOFENCE_ALERT"),
	DELETE_GEOFENCE_ALERT ("DELETE_GEOFENCE_ALERT"),
	LIST_GEOFENCE_ALERT ("LIST_GEOFENCE_ALERT"),
	
	ADD_ACCOUNT_USER ("ADD_ACCOUNT_USER"),
	UPDATE_ACCOUNT_USER ("UPDATE_ACCOUNT_USER"),
	DELETE_ACCOUNT_USER ("DELETE_ACCOUNT_USER"),
	GET_ACCOUNT_USER ("GET_ACCOUNT_USER"),
	PWD_RESTORE_ACCOUNT_USER ("PWD_RESTORE_ACCOUNT_USER"),
	LOGIN_ACCOUNT_USER ("LOGIN_ACCOUNT_USER"),
	EMAIL_VALIDATE_ACCOUNT_USER ("EMAIL_VALIDATE_ACCOUNT_USER"),
	LIST_ACCOUNT_USER ("LIST_ACCOUNT_USER"),
	
	ADD_AFFILIATE_USER ("ADD_AFFILIATE_USER"),
	UPDATE_AFFILIATE_USER ("UPDATE_AFFILIATE_USER"),
	DELETE_AFFILIATE_USER ("DELETE_AFFILIATE_USER"),
	GET_AFFILIATE_USER ("GET_AFFILIATE_USER"),
	PWD_RESTORE_AFFILIATE_USER ("PWD_RESTORE_AFFILIATE_USER"),
	LOGIN_AFFILIATE_USER ("LOGIN_AFFILIATE_USER"),
	EMAIL_VALIDATE_AFFILIATE_USER ("EMAIL_VALIDATE_AFFILIATE_USER"),
	LIST_AFFILIATE_USER("LIST_AFFILIATE_USER"),
	
	ADD_AFFILIATE ("ADD_AFFILIATE"),
	UPDATE_AFFILIATE ("UPDATE_AFFILIATE"),
	DELETE_AFFILIATE ("DELETE_AFFILIATE"),
	GET_AFFILIATE ("GET_AFFILIATE"),
	LIST_AFFILIATE("LIST_AFFILIATE"),
	
	ADD_ACCOUNT ("ADD_ACCOUNT"),
	UPDATE_ACCOUNT ("UPDATE_ACCOUNT"),
	DELETE_ACCOUNT ("DELETE_ACCOUNT"),
	GET_ACCOUNT ("GET_ACCOUNT"),
	LIST_ACCOUNT ("LIST_ACCOUNT");
	
	
	
	private final String type; 
	
	EventType(String type) {
       this.type = type;
    }
	
    public String getType()   { return type; }
    
    public static EventType getEventType (String type)  {
    	for (EventType vt : EventType.values()){
    		if (vt.getType().equals(type)) return vt;
    	}
    	return null;
    }
}
