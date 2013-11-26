package com.justplius.vezikas.facebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import com.facebook.*;
import com.facebook.model.GraphUser;
import com.facebook.widget.*;
import com.facebook.Request;
import com.justplius.vezikas.FragmentChangeActivity;
import com.justplius.vezikas.R;
import com.justplius.vezikas.dbinteraction.ServerDbQuerry;


import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLogin extends Activity {

    private LoginButton loginButton;
    private GraphUser user;
    public SharedPreferences p;
    private ArrayList<NameValuePair> nameValuePairs;
    private UiLifecycleHelper uiHelper;
    private Session session;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("FacebookLogin.java", String.format("Error in login: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("FacebookLogin.java", "Successful login!");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        p = PreferenceManager.getDefaultSharedPreferences(this);
        
        setContentView(R.layout.facebook_login);
        
       //Remove title bar
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        loginButton = (LoginButton) findViewById(R.id.login_button);
 
        loginButton.setReadPermissions(Arrays.asList("read_stream", "email", "user_groups"));      
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            @SuppressWarnings({ "unchecked", "deprecation" })
            public void onUserInfoFetched(GraphUser user) {
            	/*session = Session.getActiveSession();
            	
            	if (session == null || user == null) {
            		session.closeAndClearTokenInformation();
                } */
            	
                FacebookLogin.this.user = user;
                
                session = Session.getActiveSession();
                
                final SelectUserRatingTask selectUserRatingTask = new SelectUserRatingTask();
                final UpdateUserInformationTask updateUserInformationTask = new UpdateUserInformationTask();

                if (session != null && session.isOpened() && user != null) {
    	
                	// Request user data and show the results
                   Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                       
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                            	String id = user.getId();
                            	String name_surname = user.getFirstName() + " " + user.getLastName();
                            	String email = user.getProperty("email").toString();
                            	p.edit().putString("FB_ID", id).commit();
                            	p.edit().putString("FB_NAME_SURNAME", name_surname).commit();
                            	nameValuePairs = new ArrayList<NameValuePair>();
                            	nameValuePairs.add(new BasicNameValuePair("id", id));
                            	nameValuePairs.add(new BasicNameValuePair("name_surname", name_surname));
                            	nameValuePairs.add(new BasicNameValuePair("email", email));
                            	updateUserInformationTask.execute();  
                            	selectUserRatingTask.execute();  
                            }
                        }
                    });
                	Intent intent = new Intent(FacebookLogin.this, FragmentChangeActivity.class);
                	intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                	startActivity(intent);
                	finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if ( exception instanceof FacebookOperationCanceledException ||
                exception instanceof FacebookAuthorizationException ) {
                new AlertDialog.Builder(FacebookLogin.this)
                    .setTitle(R.string.cancelled)
                    .setMessage(R.string.permission_not_granted)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    }

    private class UpdateUserInformationTask extends AsyncTask<ArrayList<NameValuePair>, Void, Void> {
        private String url;
        @SuppressWarnings("unused")
		private ServerDbQuerry sdq;
        
    	protected void onPreExecute () {
    		url = getString(R.string.url_update_user);
    	}

		@Override
		protected Void doInBackground(ArrayList<NameValuePair>... nameValuePair) {
			sdq = new ServerDbQuerry(nameValuePairs, url);
			return null;
		}
    }
    
    
    private class SelectUserRatingTask extends AsyncTask<Void, Void, String> {
    	private JSONArray jArray;
        private String url;
        private ServerDbQuerry sdq;
        private String result;
        private JSONObject json_data;
        
    	protected void onPreExecute () {
    		url = getString(R.string.url_select_user_rating);
    	}
    	
		@Override
		protected String doInBackground(Void... params) {
            sdq = new ServerDbQuerry(url);
            result = sdq.returnJSON();   
			return result;
		}
		
		protected void onPostExecute(String result) {
    	        try {
					jArray = new JSONArray(result);
					json_data = jArray.getJSONObject(0);
					p.edit().putFloat("FB_RATING", Float.parseFloat(json_data.getString("rating")));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("FacebookLogin.java", "get user rating error: " + e.toString());   
				}      
    	        	    
	     }
    }
}
