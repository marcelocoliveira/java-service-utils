package com.utils.math;

import java.util.ArrayList;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Geofence {
	
	private static double PI = 3.14159265;
	private static double TWOPI = 2*PI;
	
	
	public static double Angle2D(
		    double x1, double y1, double x2, double y2)
		{
		   double dtheta,theta1,theta2;

		   theta1 = Math.atan2(y1,x1);
		   theta2 = Math.atan2(y2,x2);
		   dtheta = theta2 - theta1;
		   while (dtheta > PI)
		      dtheta -= TWOPI;
		   while (dtheta < -PI)
		      dtheta += TWOPI;

		   return(dtheta);
		}
	
	public static boolean is_valid_gps_coordinate(
            double latitude, double longitude)
	{
    if (latitude > -90 && latitude < 90 && 
            longitude > -180 && longitude < 180)
    {
        return true;
    }
    return false;
}
	
	public static boolean is_coordinate_inside_polygon(
		    double latitude, double longitude, 
		    ArrayList<Double> lat_array, ArrayList<Double> long_array)
		{       
		       int i;
		       double angle=0;
		       double point1_lat;
		       double point1_long;
		       double point2_lat;
		       double point2_long;
		       int n = lat_array.size();

		       for (i=0;i<n;i++) {
		          point1_lat = lat_array.get(i) - latitude;
		          point1_long = long_array.get(i) - longitude;
		          point2_lat = lat_array.get((i+1)%n) - latitude;  //here be dragons.
		          point2_long = long_array.get((i+1)%n) - longitude;
		          angle += Angle2D(point1_lat,point1_long,point2_lat,point2_long);
		       }

		       if (Math.abs(angle) < PI)
		          return false;
		       else
		          return true;
		}
	
	public static boolean is_coordinate_outside_polygon (
		    double latitude, double longitude, 
		    ArrayList<Double> lat_array, ArrayList<Double> long_array) {
		
		return !is_coordinate_inside_polygon(latitude, longitude, lat_array, long_array);
	}
	
	public static double distFrom (double lat1, double lng1, double lat2, double lng2, LengthUnit unit) {
		LatLng point1 = new LatLng(lat1, lng1);
		LatLng point2 = new LatLng(lat2, lng2);
		double distance = LatLngTool.distance(point1, point2, unit);
	    return distance;
	    }
}
