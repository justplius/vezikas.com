package com.jeremyfeinstein.slidingmenu.example;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

import android.app.Activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.SubMenu;
//import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;


public class BaseActivity extends SlidingFragmentActivity implements OnClickListener {
//comment added
	private int mTitleRes;
	protected ListFragment mFrag;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
		
		//--Slider pajungimas
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadowright);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		//---Action bar pajungimas
		//pajungiame costom action bar
	    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    getActionBar().setCustomView(R.layout.actionbar);
	    //randame pagal id
	    ImageButton btnHome = (ImageButton) findViewById(R.id.home);
	    ImageButton btnRefresh = (ImageButton) findViewById(R.id.refresh);
	    ImageButton btnMainMenu = (ImageButton) findViewById(R.id.main_menu);
	    //priskiriame listener
	    btnHome.setOnClickListener(this);
	    btnRefresh.setOnClickListener(this);
	    btnMainMenu.setOnClickListener(this);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			Toast.makeText(this, "Paspaudei 'add'", Toast.LENGTH_SHORT ).show();
		case R.id.search:
			Toast.makeText(this, "Paspaudei 'search'", Toast.LENGTH_SHORT ).show();
			return true;
		case R.id.drivers:
			Toast.makeText(this, "Paspaudei 'drivers'", Toast.LENGTH_SHORT ).show();
			return true;
		case R.id.passengers:
			Toast.makeText(this, "Paspaudëte 'Passengers'", Toast.LENGTH_SHORT ).show();
			return true;
		case R.id.all:
			Toast.makeText(this, "Paspaudëte 'All'", Toast.LENGTH_SHORT ).show();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		 if(v.getId()==R.id.home){
             Toast. makeText(this, "Paspaudëte 'Home'", Toast.LENGTH_SHORT ).show();
             }
		 if(v.getId()==R.id.refresh){
             Toast. makeText(this, "Paspaudëte 'Refresh'", Toast.LENGTH_SHORT ).show();
             }
		 if(v.getId()==R.id.main_menu){
			 toggle();
             }
		
	}
	
}
