package com.absolado.yamba;

import java.util.List;

import com.absolado.yamba.clientlib.YambaClient;
import com.absolado.yamba.clientlib.YambaClient.Status;
import com.absolado.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class RefreshService extends IntentService {
	
	private static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG, "onCreated");
	}
	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onHandleIntent");
		Log.d(TAG, "start refresh the status");
		
		SharedPreferences prefs = 
				PreferenceManager.getDefaultSharedPreferences(this);
		
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			Toast.makeText(this, 
					"Please update your username and password", 
					Toast.LENGTH_LONG).show();
			return;
		}
		
		YambaClient client = new YambaClient(username, password);
		try {
			List<Status> timeline = client.getTimeline(20);
			for (Status status : timeline) {
				Log.d(TAG, String.format("%s.[%s]: %s - by %s",  
						status.getCreatedAt(), 
						status.getUser(), 
						status.getMessage()));
			}
		} catch (YambaClientException e) {
			Log.e(TAG, e.toString());
		}
		
		return;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
	}

}
