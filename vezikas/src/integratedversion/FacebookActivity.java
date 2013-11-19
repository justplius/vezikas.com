package com.justplius.vezikas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookActivity extends Activity {

	// Your Facebook APP ID
	private static String APP_ID = "356729764461219"; // Replace with your App ID

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;

	// Buttons
	Button btnFbLogin;
	Button btnFbGetProfile;
	Button btnPostToWall;
	Button btnShowAccessTokens;
	Button btnFbLogout;
	TextView routes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook);

		btnFbLogin = (Button) findViewById(R.id.btn_fblogin);
		btnFbGetProfile = (Button) findViewById(R.id.btn_get_profile);
		btnPostToWall = (Button) findViewById(R.id.btn_fb_post_to_wall);
		btnShowAccessTokens = (Button) findViewById(R.id.btn_show_access_tokens);
		btnFbLogout = (Button) findViewById(R.id.btn_fblogout);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		routes = (TextView) findViewById(R.id.routes);
		
		/**
		 * Login button Click event
		 * */
		btnFbLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Image Button", "button Clicked");
				loginToFacebook();
			}
		});

		/**
		 * Getting facebook Profile info
		 * */
		btnFbGetProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getProfileInformation();
			}
		});

		/**
		 * Posting to Facebook Wall
		 * */
		btnPostToWall.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				postToWall();
			}
		});
		
		/**
		 * Logout from Facebook
		 * */
		btnFbLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutFromFacebook();
			}
		});

		/**
		 * Showing Access Tokens
		 * */
		btnShowAccessTokens.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showAccessTokens();
			}
		});

	}

	/**
	 * Function to login into facebook
	 * */
	public void loginToFacebook() {

		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
			
			btnFbLogin.setVisibility(View.INVISIBLE);
			
			// Making get profile button visible
			btnFbGetProfile.setVisibility(View.VISIBLE);

			// Making post to wall visible
			btnPostToWall.setVisibility(View.VISIBLE);

			// Making show access tokens button visible
			btnShowAccessTokens.setVisibility(View.VISIBLE);
						
			// Making logout button visible
			btnFbLogout.setVisibility(View.VISIBLE);			

			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "read_stream", "email", "publish_stream", "user_birthday", "user_photos", "user_groups" },
					
					new DialogListener() {

						@Override
						public void onCancel() {
							// Function to handle cancel event
						}

						@Override
						public void onComplete(Bundle values) {
							// Function to handle complete event
							// Edit Preferences and update facebook acess_token
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									facebook.getAccessToken());
							editor.putLong("access_expires",
									facebook.getAccessExpires());
							editor.commit();

							// Making Login button invisible
							btnFbLogin.setVisibility(View.INVISIBLE);

							// Making logout Button visible
							btnFbGetProfile.setVisibility(View.VISIBLE);

							// Making post to wall visible
							btnPostToWall.setVisibility(View.VISIBLE);

							// Making show access tokens button visible
							btnShowAccessTokens.setVisibility(View.VISIBLE);
							
							// Making logout button visible
							btnFbLogout.setVisibility(View.VISIBLE);	
						}

						@Override
						public void onError(DialogError error) {
							// Function to handle error

						}

						@Override
						public void onFacebookError(FacebookError fberror) {
							// Function to handle Facebook errors

						}

					});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		facebook.authorizeCallback(requestCode, resultCode, data);
	}


	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
	public void getProfileInformation() {
		mAsyncRunner.request("me", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);
					
					// getting name of the user
					final String name = profile.getString("name");
					
					// getting email of the user
					final String email = profile.getString("email");

					// getting name of the user
					final String birthday = profile.getString("birthday");
					
					// getting id of the user
					final String id = profile.getString("id");
					
					/*JSONObject jo = new JSONObject(jsonString);
				    JSONArray ja;
				    jo = jo.getJSONObject("2011-05-12_2011-05-14");
				    ja = jo.getJSONArray("data");
				    int resultCount = ja.length();
				    for (int i = 0; i < resultCount; i++)
				    {
				        JSONObject resultObject = ja.getJSONObject(i);
				        resultObject.getJSONArray("12");
				        System.out.println("--");
				    }*/
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email + "\nId: " + id + "\nBirthday: " + birthday, Toast.LENGTH_LONG).show();
						}

					});
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
		
		mAsyncRunner.request("me/groups", new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject groups = new JSONObject(json);
					
					JSONArray inputArray = groups.getJSONArray("data");
					
					for(int i=0;i<inputArray.length();i++){

						 JSONObject json_data = inputArray.getJSONObject(i);
					
						 // getting name of the user
						final String name = json_data.getString("name");
						final String id = json_data.getString("id");
						//String routes_text = routes.getText().toString();
						Log.i("facebook", json_data.getString("name") + "(id: " + id + ")\n");
						 
					}
					
					/*JSONObject jo = new JSONObject(jsonString);
				    JSONArray ja;
				    jo = jo.getJSONObject("2011-05-12_2011-05-14");
				    ja = jo.getJSONArray("data");
				    int resultCount = ja.length();
				    for (int i = 0; i < resultCount; i++)
				    {
				        JSONObject resultObject = ja.getJSONObject(i);
				        resultObject.getJSONArray("12");
				        System.out.println("--");
				    }*/
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							//Toast.makeText(getApplicationContext(), "Name: " + name + "\nEmail: " + email + "\nId: " + id + "\nBirthday: " + birthday, Toast.LENGTH_LONG).show();
						}

					});
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	/**
	 * Function to post to facebook wall
	 * */
	public void postToWall() {
		// post on user's wall.
		facebook.dialog(this, "feed", new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}

	/**
	 * Function to show Access Tokens
	 * */
	public void showAccessTokens() {
		String access_token = facebook.getAccessToken();

		Toast.makeText(getApplicationContext(),
				"Access Token: " + access_token, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Function to Logout user from Facebook
	 * */
	public void logoutFromFacebook() {
		mAsyncRunner.logout(this, new RequestListener() {
			@Override
			public void onComplete(String response, Object state) {
				Log.d("Logout from Facebook", response);
				if (Boolean.parseBoolean(response) == true) {
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// make Login button visible
							btnFbLogin.setVisibility(View.VISIBLE);

							// making all remaining buttons invisible
							btnFbGetProfile.setVisibility(View.INVISIBLE);
							btnPostToWall.setVisibility(View.INVISIBLE);
							btnShowAccessTokens.setVisibility(View.INVISIBLE);
							btnFbLogout.setVisibility(View.INVISIBLE);
						}

					});

				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

}