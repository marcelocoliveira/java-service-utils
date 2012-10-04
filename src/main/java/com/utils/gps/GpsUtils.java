package com.utils.gps;

public class GpsUtils {

	private static final String[] courses = {
		"NE", "N", "NW", "W", "SW", "S", "SE", "E"
	};
	
	private static final float[] angleCourses = {
		45.0F, 90.0F, 135.0F, 180.0F, 225.0F, 270.0F, 315.0F, 0.0F
	};
	
	private static final float D = 22.5F;
	
	public static final String angleToCourse(float angle) {
		String course = courses[7];
		
		if (Math.abs(0.0 - angle) < D || 
				Math.abs(360.0F - angle) < D) {
			return course;
		}
		
		for (int i=0; i<angleCourses.length; i++) {
			if (Math.abs(angleCourses[i] - angle) < D) {
				course = courses[i];
				break;
			}
		}
		
		return course;
	}
}
