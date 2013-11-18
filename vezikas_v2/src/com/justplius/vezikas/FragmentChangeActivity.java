package com.justplius.vezikas;

import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RatingBar;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.justplius.vezikas.R;
import com.justplius.vezikas.dbinteraction.ServerDbQuerry;
import com.justplius.vezikas.testlistview.PostListViewItem;
import com.justplius.vezikas.testlistview.PostsFragment;
import com.justplius.vezikas.testlistview.PostsListViewAdapter;

public class FragmentChangeActivity extends BaseActivity {
	
	private Fragment mContent;
	private ArrayList<PostListViewItem> driverPosts;
    private PostsListViewAdapter postListAdapter;
    private PostListViewItem driverPost;
    private JSONArray jArray;
    private String url;
    private ServerDbQuerry sdq;
    private String result;
    private JSONObject json_data;
    private ArrayList<NameValuePair> nameValuePairs;
    private Context context;
    private RatingBar ratingBar;    
    public float rating;
    public SharedPreferences p;
	
	public FragmentChangeActivity() {
		super(R.string.changing_fragments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new ColorFragment();	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new ColorMenuFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
		String used_id = p.getString("FB_ID", "");
		
		rating = 5;
    	Handler handler = new Handler();
    	final Runnable r = new Runnable (){
	
			public void run() {
				rating = p.getFloat("FB_RATING", 0);
				ratingBar = (RatingBar) findViewById(R.id.setRating);
				ratingBar.setRating(rating);
			}
    	};
    	handler.postDelayed(r, 1000);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}
	/*
	private class GetFacebookInformationTask extends AsyncTask<Void, Void, String> {
        
    	protected void onPreExecute () {
    		//url = getString(R.string.url_get_facebook_info);
    	}
    	
		@Override
		protected String doInBackground(Void... params) {
            sdq = new ServerDbQuerry(url);
            result = sdq.returnJSON();   
			return result;
		}
		
		protected void onPostExecute(String result) {
			
            //if this is new route, insert it to db
    	    try{
    	        jArray = new JSONArray(result);
    	        for(int i=0;i<jArray.length();i++){
    	            json_data = jArray.getJSONObject(i);
    	            driverPost = new PostListViewItem(context); 
    	            driverPost.setName(json_data.getString("name_surname"));    	            
    	            driverPost.setRouteInformation(json_data.getString("route"));
    	            driverPost.setSeatsAvailable(json_data.getInt("seats_available"));
    	            //driverPost.setThumbnailInt(json_data.getInt("image"));
    	            
    	            driverPost.setDate(json_data.getString("leaving_date"));
    	            driverPost.setLeavingTimeFrom(json_data.getString("leaving_time_to"));
    	            driverPost.setLeavingTimeTo(json_data.getString("leaving_time_from"));
    	            
    	            if (json_data.getInt("ratings_count") > 0){
    	            	//driverPost.setRating(Float.parseFloat("4.76"));//TODO
    	            }
    	            driverPosts.add(driverPost);    	            
    	        }
    	        postListAdapter = new PostsListViewAdapter(context, driverPosts);
    	        //postsFragment.setListAdapter(postListAdapter);
    	        postListAdapter.notifyDataSetChanged();
    	        
    	    }catch(JSONException e){
    	    	Log.e("log_tag", "selectFromRoute.php or insertToRoute.php error: "+e.toString());
    	    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
	     }
    }*/

}
