package com.nileworx.flagsquiz;

import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nileworx.flagsquiz.R;

public class SettingsActivity extends Activity {

	SharedPreferences mSharedPreferences;
	Editor e;

	String marketLink = "https://play.google.com/store/apps/details?id=com.engahmedgalal.successquotes";

	DAO db;
	Cursor c;

	UpdateClass update;
	SoundClass sou;
	CustomDialog dialog;
	private long mLastClickTime = 0;

	// =========================================================================================
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		dialog = new CustomDialog(SettingsActivity.this);
		sou = new SoundClass(SettingsActivity.this);

		AdView ad = (AdView) findViewById(R.id.adView);
		if (ad != null) {
			ad.loadAd(new AdRequest.Builder().build());
		}

		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
		e = mSharedPreferences.edit();

		db = new DAO(this);
		db.open();

		RelativeLayout layout = (RelativeLayout) findViewById(R.id.titleBar);

		TextView title = (TextView) layout.findViewById(R.id.title);
		title.setText(getResources().getString(R.string.settingsTitle).toUpperCase());

		LinearLayout scoreAndCoins = (LinearLayout) layout.findViewById(R.id.scoreAndCoins);
		scoreAndCoins.setVisibility(View.GONE);

		final RelativeLayout sound = (RelativeLayout) findViewById(R.id.sound);
		final TextView soundText = (TextView) findViewById(R.id.soundText);

		if (mSharedPreferences.getInt("sound", 1) == 1) {
			soundText.setText(getResources().getString(R.string.soundBtnOn));
		} else {
			soundText.setText(getResources().getString(R.string.soundBtnOff));
		}

		sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (mSharedPreferences.getInt("sound", 1) == 1) {
					e.putInt("sound", 0);
					soundText.setText(getResources().getString(R.string.soundBtnOff));
				} else {
					e.putInt("sound", 1);
					soundText.setText(getResources().getString(R.string.soundBtnOn));

					MediaPlayer sound = new MediaPlayer();

					AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.play);
					try {
						sound.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
						sound.prepare();
						sound.start();

						sound.setOnCompletionListener(new OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								// Do the work after completion of audio
								mp.release();
							}
						});

					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				e.commit(); // save changes
			}
		});

		final RelativeLayout vibrate = (RelativeLayout) findViewById(R.id.vibrate);
		final TextView vibrateText = (TextView) findViewById(R.id.vibrateText);

		if (mSharedPreferences.getInt("vibrate", 1) == 1) {
			vibrateText.setText(getResources().getString(R.string.vibrateBtnOn));
		} else {
			vibrateText.setText(getResources().getString(R.string.vibrateBtnOff));
		}

		vibrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				if (mSharedPreferences.getInt("vibrate", 1) == 1) {
					e.putInt("vibrate", 0);
					vibrateText.setText(getResources().getString(R.string.vibrateBtnOff));
				} else {
					Vibrator vib = (Vibrator) SettingsActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
					// Vibrate for 500 milliseconds
					vib.vibrate(500);

					e.putInt("vibrate", 1);
					vibrateText.setText(getResources().getString(R.string.vibrateBtnOn));
				}
				e.commit(); // save changes

			}
		});

		final RelativeLayout rate = (RelativeLayout) findViewById(R.id.rate);
		final TextView rateText = (TextView) findViewById(R.id.rateText);
		rateText.setText(getResources().getString(R.string.rateBtn));
		
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
					intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
					if (!MyStartActivity(intent)) {
						// Well if this also fails, we have run out of options,
						// inform the user.
						Toast.makeText(SettingsActivity.this, getResources().getString(R.string.noGooglePlayMessage), Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

		final RelativeLayout share = (RelativeLayout) findViewById(R.id.share);
		final TextView shareText = (TextView) findViewById(R.id.shareText);
		shareText.setText(getResources().getString(R.string.shareBtn));
		
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

		final RelativeLayout checkUpdates = (RelativeLayout) findViewById(R.id.check);
		final TextView checkUpdatesText = (TextView) findViewById(R.id.checkText);
		checkUpdatesText.setText(getResources().getString(R.string.checkBtn));
		
		checkUpdates.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);
				update = new UpdateClass(SettingsActivity.this);
				update.handleUpdates();

			}
		});

		final RelativeLayout reset = (RelativeLayout) findViewById(R.id.reset);
		final TextView resetText = (TextView) findViewById(R.id.resetText);
		resetText.setText(getResources().getString(R.string.resetBtn));
		
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
					return;
				}
				mLastClickTime = SystemClock.elapsedRealtime();
				sou.playSound(R.raw.buttons);
				String msg = getResources().getString(R.string.resetDlg);
				dialog.showDialog(R.layout.blue_dialog, "resetDlg", msg, null);

			}
		});

		ImageButton back = (ImageButton) layout.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();

			}
		});
		if (getResources().getString(R.string.langDirection).equals("rtl")) {
			ImageView soundImage = (ImageView) findViewById(R.id.soundImage);
			ImageView vibrateImage = (ImageView) findViewById(R.id.vibrateImage);
			ImageView rateImage = (ImageView) findViewById(R.id.rateImage);
			ImageView shareImage = (ImageView) findViewById(R.id.shareImage);
			ImageView checkImage = (ImageView) findViewById(R.id.checkImage);
			ImageView resetImage = (ImageView) findViewById(R.id.resetImage);
			
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) soundImage.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);			

			soundImage.setLayoutParams(params);
			vibrateImage.setLayoutParams(params);
			rateImage.setLayoutParams(params);
			shareImage.setLayoutParams(params);
			checkImage.setLayoutParams(params);
			resetImage.setLayoutParams(params);
		}
		

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

	// ==============================================================================

	private boolean MyStartActivity(Intent aIntent) {
		try {
			startActivity(aIntent);
			return true;
		} catch (ActivityNotFoundException e) {
			return false;
		}
	}

	// ==============================================================================
	// private void addLevels() {
	// c = db.getLevels2();
	//
	// if (c.getCount() != 0) {
	//
	// do {
	// db.addLevels2(c.getString(c.getColumnIndex("le_country")),
	// c.getInt(c.getColumnIndex("_leid")));
	//
	// } while (c.moveToNext());
	// }
	// }

	// ==============================================================================
	// private void addFlags() {
	// c = db.getFlags2();
	//
	// if (c.getCount() != 0) {
	//
	// do {
	// db.addFlags2(c.getString(c.getColumnIndex("lo_name")),
	// c.getInt(c.getColumnIndex("lo_level")),
	// c.getString(c.getColumnIndex("lo_wikipedia")),
	// c.getString(c.getColumnIndex("lo_info")),
	// c.getString(c.getColumnIndex("lo_player")),
	// c.getInt(c.getColumnIndex("_loid")));
	//
	// } while (c.moveToNext());
	// }
	// }

}
