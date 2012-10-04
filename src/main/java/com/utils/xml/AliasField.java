package com.utils.xml;

import java.util.HashMap;
import java.util.Map;



public class AliasField {

	private Map<String , AliasInner> map = new HashMap<String , AliasInner>();
	
	
	public void add (String oldValue, Class<?> clazz, String newValue) {
		map.put(oldValue, new AliasInner(clazz, newValue));
	}
	
	
	public Map<String, AliasInner> getMap() {
		return map;
	}


	public void setMap(Map<String, AliasInner> map) {
		this.map = map;
	}


	class AliasInner {
		
		private Class<?> clazz;
		private String newValue;
		
		AliasInner (Class<?> clazz , String newValue) {
			this.clazz = clazz;
			this.newValue = newValue;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public String getNewValue() {
			return newValue;
		}

		public void setNewValue(String newValue) {
			this.newValue = newValue;
		}
		
		
	}
}
