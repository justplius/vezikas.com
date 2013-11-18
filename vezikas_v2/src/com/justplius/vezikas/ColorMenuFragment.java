package com.justplius.vezikas;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.justplius.vezikas.R;
import com.justplius.vezikas.facebook.FacebookLogin;
import com.justplius.vezikas.testlistview.PostsFragment;

public class ColorMenuFragment extends ListFragment {

	private ProfilePictureView profilePictureView;
	private TextView nameSurname;
	private LoginButton loginButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_items_list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
		SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String user_id = p.getString("FB_ID", "");
		profilePictureView = (ProfilePictureView) this.getActivity().findViewById(R.id.profilePicture);
		profilePictureView.setProfileId(user_id);
		String name_surname = p.getString("FB_NAME_SURNAME", "");
		nameSurname = (TextView) this.getActivity().findViewById(R.id.nameSurname);
		nameSurname.setText(name_surname);
		loginButton = (LoginButton) this.getActivity().findViewById(R.id.login_button);
		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {                
                Session session = Session.getActiveSession();
                if (session.isClosed()) {
                	session.closeAndClearTokenInformation();
                	Intent intent = new Intent(getActivity(), FacebookLogin.class);
                	startActivity(intent);
                	getActivity().finish();
                }
            }
        });
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		Bundle args;
		switch (position) {
		case 0:
			Intent intent = new Intent(this.getActivity(), FacebookLogin.class);
	        startActivity(intent);
			break;
		case 1:
			newContent = new PostsFragment();
			break;
		case 2:
			newContent = new ColorFragment(R.color.blue);
			break;
		case 3:
			newContent = new ColorFragment(android.R.color.white);
			break;
		case 4:
			newContent = new ColorFragment(android.R.color.black);
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		} else if (getActivity() instanceof ResponsiveUIActivity) {
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
		}
	}


}
