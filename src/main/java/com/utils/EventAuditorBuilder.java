package com.utils;

import java.util.HashMap;
import java.util.Map;

import com.utils.json.JSONMapper;
import com.utils.json.JSONMapperException;

public class EventAuditorBuilder {
	
	private String input;
	private String output;
	private boolean hasFailed = false;
	private String idType;
	private String idValue;
	private EventType eventType;
	
	public EventAuditorBuilder addInput(String input){
		if (input == null) input = "";
		this.setInput(input);
		return this;
	}





	public EventAuditorBuilder addOutput(String output) {
		if (output == null) output = "";
		this.setOutput(output);
		return this;
	}



	public EventAuditorBuilder addFailure(boolean hasFailed) {
		this.setHasFailed(hasFailed);
		return this;
	}


	public EventAuditorBuilder addIdType(String idType) {
		if (idType == null) idType = "";
		this.setIdType(idType);
		return this;
	}



	public EventAuditorBuilder addIdValue(String idValue) {
		if (idValue == null) idValue = "";
		this.setIdValue(idValue);
		return this;
	}

	public EventAuditorBuilder addEventType(EventType eventType) {
		this.setEventType(eventType);
		return this;
	}
	private String getInput() {
		return this.input;
	}

	private void setInput(String input) {
		this.input = input;
	}

	private String getOutput() {
		return this.output;
	}

	private void setOutput(String output) {
		this.output = output;
	}

	private boolean isHasFailed() {
		return this.hasFailed;
	}

	private void setHasFailed(boolean hasFailed) {
		this.hasFailed = hasFailed;
	}

	private String getIdType() {
		return this.idType;
	}

	private void setIdType(String idType) {
		this.idType = idType;
	}

	private String getIdValue() {
		return this.idValue;
	}

	private void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	
	private EventType getEventType() {
		return this.eventType;
	}

	private void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString (){
		Map<String,String> map = new HashMap<String,String>();
		map.put("eventType", this.getEventType().getType());
		map.put("input", this.getInput());
		map.put("id_value", this.getIdValue());
		map.put("id_type", this.getIdType());
		map.put("failed", Boolean.toString(this.isHasFailed()));
		map.put("output", this.getOutput());
		return JSONMapper.toString(map);
		
	}
	
	public Map<String,String> getMap (){
		Map<String,String> map = new HashMap<String,String>();
		map.put("eventType", this.getEventType().getType());
		map.put("input", this.getInput());
		map.put("id_value", this.getIdValue());
		map.put("id_type", this.getIdType());
		map.put("failed", Boolean.toString(this.isHasFailed()));
		map.put("output", this.getOutput());
		return map;
	}

	
}
