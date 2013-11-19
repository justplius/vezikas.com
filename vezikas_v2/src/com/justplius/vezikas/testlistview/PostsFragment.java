package com.justplius.vezikas.testlistview;
 
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;
import com.justplius.vezikas.R;
import com.justplius.vezikas.dbinteraction.ServerDbQuerry;
 
public class PostsFragment extends SherlockListFragment  {
	
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
    private PostsFragment postsFragment = this;
    
    @Override
    public SherlockFragmentActivity getSherlockActivity() {
        return super.getSherlockActivity();
    }
 
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        driverPosts = new ArrayList<PostListViewItem>();    
    	GetPostsTask getPostsTask = new GetPostsTask();
    	getPostsTask.execute();
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Get the view from fragmenttab1.xml
        View view = inflater.inflate(R.layout.post_items_list, container, false);
        context = this.getActivity().getApplicationContext();
        return view;
    }
 
    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }*/
    
    private class GetPostsTask extends AsyncTask<Void, Void, String> {
        
    	protected void onPreExecute () {
    		url = getString(R.string.url_get_posts);
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
    	            driverPost.setDate(json_data.getString("leaving_date"));
    	            driverPost.setLeavingTimeFrom(json_data.getString("leaving_time_to"));
    	            driverPost.setLeavingTimeTo(json_data.getString("leaving_time_from"));
    	            
    	            if (json_data.getInt("ratings_count") > 0){
    	            	//driverPost.setRating(Float.parseFloat("4.76"));//TODO
    	            }
    	            driverPosts.add(driverPost);    	            
    	        }
    	        postListAdapter = new PostsListViewAdapter(context, driverPosts);
    	        postsFragment.setListAdapter(postListAdapter);
    	        postListAdapter.notifyDataSetChanged();
    	        
    	    }catch(JSONException e){
    	    	Log.e("PostsFragment.java", "get posts async task error: "+e.toString());
    	    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
	     }
    }
}