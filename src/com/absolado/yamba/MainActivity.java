package com.absolado.yamba;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Log.d(TAG, "Start settings");
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.action_tweet:
			Log.d(TAG, "Start tweet");
			startActivity(new Intent(this, StatusActivity.class));
			return true;
		case R.id.action_refresh:
			Log.d(TAG, "Start refresh service");
			startService(new Intent(this, RefreshService.class));
			return true;
		default:
			Log.i(TAG, "Other button clicked");
			return false;	
		}
	}

}
