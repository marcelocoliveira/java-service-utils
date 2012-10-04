package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class ResourceUtil extends ResourceUtils {

	private static String propFile = "/app.properties";
	
	public static String getPropValue (String key) throws IOException{
		URL url = new ResourceUtil().getClass().getResource(propFile);
		Properties prop = new Properties();
		FileInputStream resource = new java.io.FileInputStream(url.getFile());
		prop.load(resource);
		return  prop.getProperty(key);
	}

	public static String getPropFile() {
		return propFile;
	}

	public static void setPropFile(String propFile) {
		ResourceUtil.propFile = propFile;
	}
	
	
}
