package com.justplius.vezikas.testlistview;
 
import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.justplius.vezikas.R;
 
public class PostsFragment extends SherlockListFragment  {
	//2
	private ArrayList<PostListViewItem> driverPosts;
    private PostsListViewAdapter postListAdapter;
	
    @Override
    public SherlockFragmentActivity getSherlockActivity() {
        return super.getSherlockActivity();
    }
 
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        driverPosts = new ArrayList<PostListViewItem>();    
        
        PostListViewItem driverPost = new PostListViewItem(this.getActivity().getApplicationContext());
        
        driverPosts.add(driverPost); 
        /*flloat rating = 4.5f;
		leavingTime = new Time();
		leavingTime.getCurrentTimezone();
		leavingTime.setToNow();
		droppingTime = new Time();
		droppingTime.set(leavingTime.toMillis(true) + 1000*60*60*3);
		route = new String("Ðiauliai - Kaunas - Vilnius");
		seats_available = 4;				
		date = new Time();
		date.set(leavingTime.toMillis(true));
		thumbnail = R.drawable.ic_launcher;*/
        
        /*driverPost.setDate(new Time(Time.getCurrentTimezone()));
        driverPost.setDroppingTime(_time);
        driverPost.setLeavingTime(_time);*/
        driverPost.setRating(3);
        driverPost.setRouteInformation("Kaunas - Ðiauliai");
        driverPost.setSeatsAvailable(3);
        //driverPost.setThumbnailInt(_thumbnail);
        
        driverPosts.add(driverPost); 
        postListAdapter = new PostsListViewAdapter(this.getActivity().getApplicationContext(), driverPosts);
    	this.setListAdapter(postListAdapter);        
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.posts_list, container, false);
        
        return view;
    }
 
    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }*/
}