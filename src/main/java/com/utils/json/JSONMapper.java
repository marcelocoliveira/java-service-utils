package com.utils.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings(value = {"rawtypes", "unchecked"})
public class JSONMapper {

	
	public static Object toObject (String payload, Class obj) throws JSONMapperException  {
		ObjectMapper oMapper = new ObjectMapper();
		Object output = null;
		try {
			output = oMapper.readValue(payload, obj);
		} catch (JsonParseException e) {
			throw new JSONMapperException("Failure parsing json payload. "+ e.getMessage());
		} catch (JsonMappingException e) {
			throw new JSONMapperException("Failure mapping json payload. "+ e.getMessage());
		} catch (IOException e) {
			throw new JSONMapperException("Failure reading json payload. "+ e.getMessage());
		}
		return output;
		
	}
	
	public static String toString (Object object)   {
		ObjectMapper oMapper = new ObjectMapper();
		String output = "";
		try {
			output = oMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			return e.getMessage();
		} catch (JsonMappingException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		return output;
	}
	
	public static Object load(String schemaFileName, Class obj) throws JSONMapperException  {

		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new File(schemaFileName), obj);
			
		} catch (Exception e) {
			throw new JSONMapperException("Failure parsing json file ", e);
		} 
		
	}
	
	public static String getString (String schema, String key) throws JSONException {
			JSONObject json = new JSONObject(schema);        
	        String coolness = json.getString( key);
	        return coolness;
		
	}
	
	public static Object loadClasspathResource(String schemaFileName, Class obj) throws JSONMapperException  {

		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream is = 
				JSONMapper.class.getClassLoader().getResourceAsStream(schemaFileName);
			return mapper.readValue(is, obj);
			
		} catch (Exception e) {
			throw new JSONMapperException("Failure parsing json file ", e);
		} 
		
	}

}
