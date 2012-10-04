package com.utils;

public class ConversionUtils {
	
	private static final String METRIC= "METRIC";
	private static final String IMPERIAL="IMPERIAL";
	public static double milesToKilometers(double m) {
		
		return m * 1.609344;
	}
	
	public static double kilometersToMiles(double km) {
		return Math.ceil(km * 0.621371192);
	}
	
	public static boolean isMetric (String unitType){
		return unitType.toUpperCase().equals(METRIC);
	}
	
	public static boolean isImperial (String unitType){
		return unitType.toUpperCase().equals(IMPERIAL);
	}
	
	public static UnitType getUnitType (String unitType) {
		if (unitType.toUpperCase().equals(METRIC)) return UnitType.METRIC;
		else 
			return UnitType.IMPERIAL;
		
	}
	
	
}
