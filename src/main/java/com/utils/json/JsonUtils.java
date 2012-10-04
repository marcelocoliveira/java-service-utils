package com.utils.json;

import java.util.Map;

public class JsonUtils {
	
	@SuppressWarnings("unchecked")
	public static Object getPropertyValue(Object wrapper) {
		if (wrapper instanceof Map<?, ?>) {
			Map<String, ?> map = (Map<String, ?>) wrapper;
			for (String k : map.keySet()) {
				return map.get(k);
			}
		}
		return null;
	}
	
	public static String asSafeString(Json json, String property) {
		try {
			if (json != null && json.has(property)) {
				return json.at(property).asString();
			}
		} catch (Exception e) { 
			// intentionally blank 
		}
		return null;
	}

	public static Long asSafeLong(Json json, String property) {
		try {
			if (json != null && json.has(property)) {
				return json.at(property).asLong();
			}
		} catch (Exception e) { 
			// intentionally blank 
		}
		return null;
	}
}
