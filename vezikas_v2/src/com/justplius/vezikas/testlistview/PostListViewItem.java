package com.justplius.vezikas.testlistview;

import java.util.Date;

import com.justplius.vezikas.R;

import android.content.Context;
import android.text.format.Time;


public class PostListViewItem {

	Context context;
	
	//Post data
	private float rating;	
	private int seats_available;
	private String route;
	private Time date;
	private Time leavingTime;
	private Time droppingTime;
	private int thumbnail;
	private String nameSurname;
				
	//Constructor
	PostListViewItem (Context _context) {
		context = _context;
		rating = 4.5f;
		leavingTime = new Time();
		leavingTime.getCurrentTimezone();
		leavingTime.setToNow();
		droppingTime = new Time();
		droppingTime.set(leavingTime.toMillis(true) + 1000*60*60*3);
		route = new String("Ðiauliai -> Kaunas -> Vilnius");
		seats_available = 4;				
		date = new Time();
		date.set(leavingTime.toMillis(true));
		thumbnail = R.drawable.ic_launcher;
		nameSurname = "Vardenis Pavardenis";
	}
		
	public void setRating(float _rating){
	   	rating = _rating;
	}
				
	public void setRouteInformation(String _route){
	   	route = _route;
	}
				
	public void setDate(Time _date){
	   	date = _date;
	}
				
	public void setLeavingTime(Time _time){
		leavingTime = _time;
	}
		
	public void setDroppingTime(Time _time){
		droppingTime = _time;
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
		timeString.append(leavingTime.hour + ":" + leavingTime.minute + " - ");
		timeString.append(droppingTime.hour + ":" + droppingTime.minute);
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