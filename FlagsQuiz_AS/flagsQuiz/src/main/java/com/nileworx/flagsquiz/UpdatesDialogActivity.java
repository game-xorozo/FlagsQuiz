package com.nileworx.flagsquiz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class UpdatesDialogActivity extends Activity {

	CustomDialog dialog;
	SharedPreferences mSharedPreferences;
	Editor e;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
		e = mSharedPreferences.edit();
		
		e.putString("flagsNum", getIntent().getStringExtra("flagsNum"));
		e.putString("flagsJSON", getIntent().getStringExtra("json"));
		e.commit();
		finish();
		
//		dialog = new CustomDialog(UpdatesDialogActivity.this);
//		dialog.showDialog(R.layout.blue_dialog, "updatesActivityDlg", "There are new " + getIntent().getStringExtra("flagsNum") + " flags. Download?", getIntent().getStringExtra("json"));
	}

	// ==============================================================================

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();		
	}


}
