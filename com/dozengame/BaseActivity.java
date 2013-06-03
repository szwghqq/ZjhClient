package com.dozengame;

import android.app.Activity;
import android.util.Log;

public class BaseActivity extends Activity {
	@Override
	protected void onResume() {
		super.onResume();
		Log.i("test1", "BaseActivity onResume");
		GameApplication.testNetCun();
		GameApplication.currentActivity =this;
	}
}
