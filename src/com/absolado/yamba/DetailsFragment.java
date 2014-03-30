package com.absolado.yamba;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {
	
	private static final String TAG = "DetailsFragment";

	private TextView textUser, textMsg, textCreatedAt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_details, null, false);
		
		textUser = (TextView) view.findViewById(R.id.text_details_user);
		textMsg = (TextView) view.findViewById(R.id.text_details_message);
		textCreatedAt = (TextView) view.findViewById(R.id.text_details_created_at);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		long id = getActivity().getIntent().getLongExtra(
						StatusContract.Column.ID, -1);
		
		updateView(id);
	}
	
	public void updateView(long id) {
		if (id == -1) {
			Log.d(TAG, "Invalid id provided");
			textUser.setText("");
			textMsg.setText("");
			textCreatedAt.setText("");
			return;
		}
		
		Uri uri = ContentUris.withAppendedId(StatusContract.CONTENT_URI, id);
		Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
		
		if (!cursor.moveToFirst()) {
			Log.d(TAG, "No data returned!");
			return;
		}
		
		textUser.setText(cursor.getString(cursor
				.getColumnIndex(StatusContract.Column.USER)));
		textMsg.setText(cursor.getString(cursor
				.getColumnIndex(StatusContract.Column.MESSAGE)));
		textCreatedAt.setText(DateUtils.getRelativeTimeSpanString(
				cursor.getLong(cursor.getColumnIndex(
						StatusContract.Column.CREATED_AT))));
	}

}
