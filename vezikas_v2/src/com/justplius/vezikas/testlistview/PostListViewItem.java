package com.justplius.vezikas.testlistview;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.justplius.vezikas.R;

import android.content.Context;
import android.net.ParseException;
import android.text.format.Time;
import android.util.Log;


public class PostListViewItem {

	Context context;
	
	//Post data
	private float rating;	
	private int seats_available;
	private String route;
	private Time date;
	private Time leaving_time_from;
	private Time leaving_time_to;
	private int thumbnail;
	private String nameSurname;
				
	//Constructor
	public PostListViewItem (Context _context) {
		context = _context;
		leaving_time_from = new Time();
		leaving_time_to = new Time();		
		date = new Time();
		route = new String();
		thumbnail = R.drawable.ic_driver;
		nameSurname = new String();
	}
		
	public void setRating(float _rating){
	   	rating = _rating;
	}
				
	public void setRouteInformation(String _route){
	   	route = _route;
	}
				
	public void setDate(String _date) throws java.text.ParseException{  
		date.set(new SimpleDateFormat("yyyy-MM-dd").parse(_date).getTime());
	}
				
	public void setLeavingTimeFrom(String _time) throws java.text.ParseException{		
		leaving_time_from.set(new SimpleDateFormat("HH:mm:ss").parse(_time).getTime());
	}
		
	public void setLeavingTimeTo(String _time) throws java.text.ParseException{
		leaving_time_to.set(new SimpleDateFormat("HH:mm:ss").parse(_time).getTime());
	}
				
	public void setSeatsAvailable(int _seats_available){
	   	seats_available = _seats_available;
	}
		
	public void setThumbnailInt(int _thumbnail){
	   	thumbnail = _thumbnail;
	}
	
	public void setName(String _nameSurname){
		nameSurname = _nameSurname;
	}
		
	public float getRating(){
	   	return rating;
	}
				
	public String getRouteInformation(){
	   	return route;
	}
				
	public String getDate(){
		StringBuilder dateString = new StringBuilder();	
		switch (date.month){
		case 1:
			dateString.append(context.getResources().getString(R.string.month_1_ltu));
			break;
		case 2:
			dateString.append(context.getResources().getString(R.string.month_2_ltu));
			break;
		case 3:
			dateString.append(context.getResources().getString(R.string.month_3_ltu));
			break;
		case 4:
			dateString.append(context.getResources().getString(R.string.month_4_ltu));
			break;
		case 5:
			dateString.append(context.getResources().getString(R.string.month_5_ltu));
			break;
		case 6:
			dateString.append(context.getResources().getString(R.string.month_6_ltu));
			break;
		case 7:
			dateString.append(context.getResources().getString(R.string.month_7_ltu));
			break;
		case 8:
			dateString.append(context.getResources().getString(R.string.month_8_ltu));
			break;
		case 9:
			dateString.append(context.getResources().getString(R.string.month_9_ltu));
			break;
		case 10:
			dateString.append(context.getResources().getString(R.string.month_10_ltu));
			break;
		case 11:
			dateString.append(context.getResources().getString(R.string.month_11_ltu));
			break;
		case 12:
			dateString.append(context.getResources().getString(R.string.month_12_ltu));
			break;
		}
		dateString.append(" " + date.monthDay + " d.");
	   	return dateString.toString();
	}
				
	public String getTime(){
		StringBuilder timeString = new StringBuilder();		
		timeString.append(leaving_time_from.hour + ":");
		if (leaving_time_from.minute < 10){
			timeString.append("0"); 
		}
		timeString.append(leaving_time_from.minute + " - ");
		timeString.append(leaving_time_to.hour + ":");
		if (leaving_time_to.minute < 10){
			timeString.append("0"); 
		}
		timeString.append(leaving_time_to.minute);
	   	return timeString.toString();
	}
				
	public String getSeatsAvailable(){
	   	return String.valueOf(seats_available);
	}
		
	public int getThumbnailInt(){
	   	return thumbnail;
	}
	
	public String getNameSurname(){
	   	return nameSurname;
	}
}