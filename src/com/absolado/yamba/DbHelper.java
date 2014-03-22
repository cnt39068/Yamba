package com.absolado.yamba;

import java.util.Locale;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private static final String TAG = DbHelper.class.getSimpleName();

	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		String sql = String.format(Locale.getDefault(), 
			"create table %s (%s int primary key, %s text, %s text, %s int)",
			StatusContract.TABLE,
			StatusContract.Column.ID,
			StatusContract.Column.USER,
			StatusContract.Column.MESSAGE,
			StatusContract.Column.CREATED_AT);
			
		Log.d(TAG, "onCreate with SQL: "+sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		Log.d(TAG, "onUpgrade");
		
		db.execSQL("drop table if exists " + StatusContract.TABLE);
		onCreate(db);
	}

}
