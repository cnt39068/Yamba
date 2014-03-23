package com.absolado.yamba;

import java.util.List;
import java.util.Locale;

import com.absolado.yamba.clientlib.YambaClient;
import com.absolado.yamba.clientlib.YambaClient.Status;
import com.absolado.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.content.UriMatcher;
//import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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
		
//		DbHelper dbHelper = new DbHelper(this);
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		YambaClient client = new YambaClient(username, password);
		try {
			int count = 0;
			List<Status> timeline = client.getTimeline(20);
			for (Status status : timeline) {
				values.clear();
				values.put(StatusContract.Column.ID, status.getId());
				values.put(StatusContract.Column.USER, status.getUser());
				values.put(StatusContract.Column.MESSAGE, status.getMessage());
				values.put(StatusContract.Column.CREATED_AT,
						status.getCreatedAt().getTime());
				
				Uri uri = getContentResolver().insert(
							StatusContract.CONTENT_URI, values);
				if (uri != null) {
					count++;
//					Log.d(TAG, String.format(Locale.US, "%s: %s",
//							status.getUser(), status.getMessage()));
				}
//				db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
				
//				Log.d(TAG, String.format("%s.[%s]: %s - by %s",  
//						status.getCreatedAt(), 
//						status.getId(),
//						status.getMessage(), 
//						status.getUser()));
			}
			
			Log.d(TAG, String.format(Locale.US, "Insert %d records", count));
		} catch (YambaClientException e) {
			e.printStackTrace();
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
