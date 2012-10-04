package com.utils.math;

import org.apache.commons.lang.math.NumberUtils;

public class NumberUtil extends NumberUtils {

	
	public static boolean isInteger (String number){
		try {
			Integer.parseInt(number);
			return true;
		}
		catch (NumberFormatException e){
			return false;
		}
	}
	
	public static boolean isFloat (String number){
		try {
			Float.parseFloat(number);
			return true;
		}
		catch (NumberFormatException e){
			return false;
		}
	}
	
	public static boolean isDouble (String number){
		try {
			Double.parseDouble(number);
			return true;
		}
		catch (NumberFormatException e){
			return false;
		}
	}
}
