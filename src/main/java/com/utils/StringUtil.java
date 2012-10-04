package com.utils;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {

	
	public static int compressString(String data, byte[] output, int len) {
	    Deflater deflater = new Deflater();
	    deflater.setInput(data.getBytes(Charset.forName("utf-8")));
	    deflater.finish();
	    return deflater.deflate(output, 0, len);
	}

	public static String decompressString(byte[] input, int len) {

	    String result = null;
	    try {
	        Inflater inflater = new Inflater();
	        inflater.setInput(input, 0, len);

	        byte[] output = new byte[100]; //todo may oveflow, find better solution
	        int resultLength = inflater.inflate(output);
	        inflater.end();

	        result = new String(output, 0, resultLength, Charset.forName("utf-8"));
	    } catch (DataFormatException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public static String generateReducedString (String string, int size){
		string = StringUtils.trimAllWhitespace(string);
		string = StringUtils.replace(string, "null", "x");
		String left = org.apache.commons.lang.StringUtils.left(string, size);
		String right = org.apache.commons.lang.StringUtils.right(string, size);
		String out =  left + right;
		return out;
	}
	
	public static String extractString (String str, String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
		    return matcher.group(1);
		}
		return "";
	}
	
	public static String getZeroIfNull (String value) {
		if (value == null) return "0";
		return value;
	}
}
