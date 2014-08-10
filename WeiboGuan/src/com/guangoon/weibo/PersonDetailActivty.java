package com.guangoon.weibo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

public class PersonDetailActivty extends Activity {
	private static final String TAG = "PersonDetailActivty";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_detail);
		getActionBar().hide();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
}
