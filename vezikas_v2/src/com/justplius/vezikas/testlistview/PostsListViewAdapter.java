package com.justplius.vezikas.testlistview;

import java.util.ArrayList;

import com.justplius.vezikas.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PostsListViewAdapter extends ArrayAdapter<PostListViewItem> {

	private Context context;
	private ArrayList<PostListViewItem> allPostsListViewItems;
	private LayoutInflater mInflater;
	private boolean mNotifyOnChange = true;
	
	public PostsListViewAdapter(Context _context, ArrayList<PostListViewItem> _allPostsListViewItems) {
	    super(_context, R.layout.passenger_post_item);
	    this.context = _context;
	    this.allPostsListViewItems = new ArrayList<PostListViewItem>(_allPostsListViewItems);
	    this.mInflater = LayoutInflater.from(_context);
	}
	
	@Override
	public int getCount() {
	    return allPostsListViewItems .size();
	}
	
	@Override
	public PostListViewItem getItem(int position) {
	    return allPostsListViewItems.get(position);
	}
	
	@Override
	public long getItemId(int position) {
	    return position;
	}
	
	@Override
	public int getPosition(PostListViewItem item) {
	    return allPostsListViewItems .indexOf(item);
	}
	
	@Override
	public int getViewTypeCount() {
	    return 1; //Number of types + 1 !!!!!!!!
	}
	
	@Override
	public int getItemViewType(int position) {
	    return 1;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    final ViewHolder holder;
	    int type = getItemViewType(position);
	    if (convertView == null) {
	        holder = new ViewHolder();
	        switch (type) {
	        case 1:
	        	 convertView = mInflater.inflate(R.layout.passenger_post_item,parent, false);
		            
		            holder.post_item_background = (RelativeLayout) convertView.findViewById(R.id.postItemBackground);
		            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.pattern_green);         
		            //Drawable d = new BitmapDrawable(context.getResources(),getRoundedCornerBitmap(b)); 
		            Drawable d = new CurvedAndTiled(b, 15);
		            holder.post_item_background.setBackgroundDrawable(d);
		            
		            holder.route_information = (TextView) convertView.findViewById(R.id.route_information);            
		            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);	            
		            holder.seats_available = (TextView) convertView.findViewById(R.id.seats_available);	            
		            holder.date_information = (TextView) convertView.findViewById(R.id.date_information);	            
		            holder.time_information = (TextView) convertView.findViewById(R.id.time_information);
		            holder.name_surname = (TextView) convertView.findViewById(R.id.name_surname);
		            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
	            break;
	        }
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    holder.route_information.setText(allPostsListViewItems.get(position).getRouteInformation());
	    holder.thumbnail.setImageResource(allPostsListViewItems.get(position).getThumbnailInt());	    
	    holder.seats_available.setText(allPostsListViewItems.get(position).getSeatsAvailable());
	    holder.date_information.setText(allPostsListViewItems.get(position).getDate());
	    holder.time_information.setText(allPostsListViewItems.get(position).getTime());
	    holder.rating.setRating(allPostsListViewItems.get(position).getRating());;	    
	    holder.name_surname.setText(allPostsListViewItems.get(position).getNameSurname());	 
	    
	    
	    holder.pos = position;
	    return convertView;
	}
	
	@Override
	public void notifyDataSetChanged() {
	    super.notifyDataSetChanged();
	    mNotifyOnChange = true;
	}
	
	public void setNotifyOnChange(boolean notifyOnChange) {
	    mNotifyOnChange = notifyOnChange;
	}
	
	
	//---------------static views for each row-----------//
	static class ViewHolder {
	
		TextView route_information;
		ImageView thumbnail;
		TextView seats_available;
		TextView date_information;
		TextView time_information;
		RatingBar rating;
		TextView name_surname;
		RelativeLayout post_item_background;
		int pos; //to store the position of the item within the list

	}
	     
}