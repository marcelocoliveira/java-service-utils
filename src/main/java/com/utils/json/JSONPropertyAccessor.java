package com.utils.json;

import java.util.Map;

public class JSONPropertyAccessor {

	public <T> T getDirectProperty(Class<T> type, String property, Map<String, Object> messagePropertyMap) {
		Object value = messagePropertyMap.get(property);
		return value != null ? type.cast(value) : null;
	}

	public <T> T getProperty(Class<T> type, String property, Map<String, Object> messagePropertyMap) {
		return getProperty(type, null, property, messagePropertyMap);
	}

	@SuppressWarnings("unchecked")
	public <T> T getProperty(Class<T> type, String rootElement, String property, Map<String, Object> messagePropertyMap) {
		
		if (messagePropertyMap.size() == 0) {
			return null;
		}
		
		Map<String, Object> rootDataMap = messagePropertyMap;
		if (rootElement != null) {
			rootDataMap = (Map<String, Object>) messagePropertyMap.get(rootElement);
		}
		
		Map<String, Object> currentDataMap = rootDataMap;
		
		String[] properties = property.split("\\.");
		if (properties.length == 0) {
			properties = new String[] {property};
		}
		
		Object value = null;
		
		for (int i = 0; i < properties.length; i++) {
			if (i == properties.length -1) {
				value = doGet(type, currentDataMap, properties[i]);
			} else {
				Object nextDataElement = currentDataMap.get(properties[i]);
				if (nextDataElement instanceof Map<?,?>) {
					currentDataMap = (Map<String, Object>) nextDataElement;
				}
			}
		}
		return value != null ? type.cast(value) : null;
	}

	private <T> T doGet(Class<T> type, Map<String, Object> dataMap, String key) {

		Object v = dataMap.get(key);
		try {
			return type.cast(v);
		} catch (Exception e) {
			return coerce(type, v);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T coerce(Class<T> type, Object v) {
		
		if (String.class.isAssignableFrom(type)) {
			try {
				return (T) String.valueOf(v);
			} catch (Exception ee) { 
			}
		} else if (Integer.class.isAssignableFrom(type)){
			try {
				return (T) Integer.valueOf(v.toString());
			} catch (Exception ee) { 
			}
		} else if (Long.class.isAssignableFrom(type)){
			try {
				return (T) Long.valueOf(v.toString());
			} catch (Exception ee) { 
			}
		} else if (Float.class.isAssignableFrom(type)){
			try {
				return (T) Float.valueOf(v.toString());
			} catch (Exception ee) { 
			}
		} else if (Double.class.isAssignableFrom(type)){
			try {
				return (T) Double.valueOf(v.toString());
			} catch (Exception ee) { 
			}
		} 
		// TODO other primitive types here
		return null;
	}

}
