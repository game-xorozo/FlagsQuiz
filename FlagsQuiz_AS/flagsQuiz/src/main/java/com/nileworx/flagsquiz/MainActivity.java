package com.nileworx.flagsquiz;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.nileworx.flagsquiz.R;

public class MainActivity extends BaseGameActivity {

	String marketLink;
	SharedPreferences mSharedPreferences;
	Editor e;
	DAO db;

	JSONArray flags = null;

	String siteUrl, updatesUrl;
	int lastFlag;

	JSONObject json;
	String jsonResultNull = "";
	SoundClass sou;
	CustomDialog dialog;
	private ConnectionDetector cd;
	private long mLastClickTime = 0;

	// ==============================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		int sWidth = displaymetrics.widthPixels;
		int sHeight = displaymetrics.heightPixels;

		int dens = displaymetrics.densityDpi;
		double wi = (double) sWidth / (double) dens;
		double hi = (double) sHeight / (double) dens;
		double x = Math.pow(wi, 2);
		double y = Math.pow(hi, 2);
		double screenInches = Math.sqrt(x + y);
		
		if (sWidth > 480 && screenInches >= 4 && screenInches <= 5) {
			setContentView(R.layout.activity_main_4x);			
		} else if (screenInches > 6.5 && screenInches < 9) {
			setContentView(R.layout.activity_main_7x);			
		} else {
			setContentView(R.layout.activity_main);
		}			

		dialog = new CustomDialog(MainActivity.this);
		sou = new SoundClass(MainActivity.this);

		db = new DAO(this);
		db.open();

		cd = new ConnectionDetector(MainActivity.this);
		lastFlag = db.getLastFlag();

		siteUrl = getResources().getString(R.string.siteUrl);
		updatesUrl = siteUrl + "site/get_updates/" + String.valueOf(lastFlag);

		if (cd.isConnectingToInternet()) {
			// Internet Connection is not present

			Intent checkUpdates = new Intent(MainActivity.this, CheckUpdatesService.class);
			startService(checkUpdates);
		}

		marketLink = "https://play.google.com/store/apps/details?id=" + getPackageName();

		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
		e = mSharedPreferences.edit();

//		if (mSharedPreferences.getInt("usingNum", 0) != 100) {
//			countUsingNumForRating();
//		}
		
		

		final ImageButton play = (ImageButton) findViewById(R.id.play);
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.play);
				if (db.getNextFlag() != 0) {
					Intent intent = new Intent(MainActivity.this, GameActivity.class);
					intent.putExtra("FlagId", String.valueOf(db.getNextFlag()));
					startActivity(intent);
				} else {
					dialog.showDialog(R.layout.red_dialog, "finishDlg", getResources().getString(R.string.finishDlg), null);					
				}
				

			}
		});

		final ImageButton sound = (ImageButton) findViewById(R.id.sound);

		if (mSharedPreferences.getInt("sound", 1) == 1) {
			sound.setBackgroundResource(R.drawable.button_sound_on_main);
		} else {
			sound.setBackgroundResource(R.drawable.button_sound_off_main);
		}

		sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mSharedPreferences.getInt("sound", 1) == 1) {
					e.putInt("sound", 0);
					e.commit();
					sound.setBackgroundResource(R.drawable.button_sound_off_main);
				} else {
					e.putInt("sound", 1);
					e.commit();
					sound.setBackgroundResource(R.drawable.button_sound_on_main);

					sou.playSound(R.raw.buttons);
				}
				// e.commit(); // save changes
			}
		});

		final ImageButton share = (ImageButton) findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);

				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareMessage = getResources().getString(R.string.shareDlgMessage) + marketLink;
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.shareDlgSubject));
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
				startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.shareDlgTitle)));

			}
		});

		final ImageButton rate = (ImageButton) findViewById(R.id.rate);
		rate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);

				Intent intent = new Intent(Intent.ACTION_VIEW);

				intent.setData(Uri.parse("market://details?id=" + getPackageName()));

				if (!MyStartActivity(intent)) {
					// Market (Google play) app seems not installed, let's try
					// to open a webbrowser
					intent.setData(Uri.parse(marketLink));
					if (!MyStartActivity(intent)) {
						// Well if this also fails, we have run out of options,
						// inform the user.
						Toast.makeText(MainActivity.this, getResources().getString(R.string.noGooglePlayMessage), Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		final ImageButton settings = (ImageButton) findViewById(R.id.settings);
		settings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
				startActivity(intent);

			}
		});

		final ImageButton exit = (ImageButton) findViewById(R.id.exit);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);

				dialog.showDialog(R.layout.blue_dialog, "exitDlg", getResources().getString(R.string.exitDlg), null);

			}
		});

		final ImageButton leaderboard = (ImageButton) findViewById(R.id.leaderboard);
		leaderboard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);
				if (!isSignedIn()) {
					beginUserInitiatedSignIn();
				} else {
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getString(R.string.flags_score_leaderboard)), 2);
				}

			}
		});
	}

	// =========================================================================================

	@Override
	protected void onResume() {
		super.onResume();
		
		if (!mSharedPreferences.getString("flagsNum", "0").equals("0")) {
			String updatesDlgMsg = String.format(getResources().getString(R.string.updatesDlg), mSharedPreferences.getString("flagsNum", "0"));
			dialog.showDialog(R.layout.blue_dialog, "updatesDlg", updatesDlgMsg, mSharedPreferences.getString("flagsJSON", ""));
			e.putString("flagsNum", "0");
			e.commit();
		}
	}

	// =========================================================================================
	
	public void countUsingNumForRating() {

		e.putInt("usingNum", mSharedPreferences.getInt("usingNum", 0) + 1);
		e.commit();

		if (mSharedPreferences.getInt("usingNum", 0) >= 3) {
			cd = new ConnectionDetector(MainActivity.this);
			if (cd.isConnectingToInternet()) {
				String msg = getResources().getString(R.string.rateDlg);
				dialog.showDialog(R.layout.blue_dialog, "rateDlg", msg, marketLink);
			}
		}
	
	}	

	// ==============================================================================

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("tag", "onActivityResult(" + requestCode + "," + resultCode + "," + data);

		if (requestCode == 2 && resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED) {
			mHelper.disconnect();
			// update your logic here (show login btn, hide logout btn).
		} else {
			mHelper.onActivityResult(requestCode, resultCode, data);
		}
	}

	// ==============================================================================

	public boolean MyStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	// ========================================================================================================

	@Override
	public void onSignInSucceeded() {
	}

	// ========================================================================================================

	@Override
	public void onSignInFailed() {
	}

	private void addFlag2() {
		Log.e("flags2", "Done");
		Cursor cu = db.getFlags();

		if (cu.getCount() != 0) {

			do {
				db.addFlags2(cu.getString(cu.getColumnIndex("fl_name")), cu.getString(cu.getColumnIndex("fl_country")), cu.getString(cu.getColumnIndex("fl_city")), cu.getString(cu.getColumnIndex("fl_wikipedia")), cu.getInt(cu.getColumnIndex("fl_order")), cu.getInt(cu.getColumnIndex("fl_web_id")));
			} while (cu.moveToNext());
		}
	}

}
