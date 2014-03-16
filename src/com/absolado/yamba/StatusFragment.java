package com.absolado.yamba;

import com.absolado.yamba.clientlib.YambaClient;
import com.absolado.yamba.clientlib.YambaClientException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusFragment extends Fragment implements OnClickListener{

	private static final String TAG = "StatusActivity";
	private static final int MAX_COUNT = 140;
	private EditText editStatus;
	private Button buttonTweet;
	private TextView textCount;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
								Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_status, container, false);
		
		
		editStatus = (EditText) view.findViewById(R.id.editStatus);
		buttonTweet = (Button) view.findViewById(R.id.buttonTweet);
		textCount = (TextView) view.findViewById(R.id.textCount);
		
		buttonTweet.setOnClickListener(this);
		
		editStatus.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				int count = MAX_COUNT - editStatus.length();
				textCount.setText(Integer.toString(count));
				
				if (count < 10)
					textCount.setTextColor(Color.RED);
			}
			
			@Override
		    public void beforeTextChanged(CharSequence s,
                    int start, int count,
                    int after) { 
		    }
			
		    @Override
		    public void onTextChanged(CharSequence s, int start, int before,int count) {
		    }
		});
		
		return view;
	}

	@Override
	public void onClick(View view) {
		String status = editStatus.getText().toString();
		Log.d(TAG, "onClicked with status: " + status);
		
		new PostTask().execute(status);
	}
	
	private final class PostTask extends
					AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			 YambaClient yambaCloud =
					 new YambaClient("student", "password");
			 
			 try {
				 yambaCloud.postStatus(params[0]);
				 return "Successfully posted";
			 } catch (YambaClientException e) {
				 e.printStackTrace();
				 return "Failed to post to yamba service";
			 }
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			Toast.makeText(StatusFragment.this.getActivity(), result, Toast.LENGTH_LONG).show();
		}
	}
}
