package com.absolado.yamba;

import android.app.Activity;
import android.os.Bundle;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// check whether it's created before
		if (savedInstanceState == null) {
			DetailsFragment details = new DetailsFragment();
			
			getFragmentManager()
				.beginTransaction()
					.add(android.R.id.content, 
						details, 
						details.getClass().getSimpleName()).commit();
		}
	}

}
