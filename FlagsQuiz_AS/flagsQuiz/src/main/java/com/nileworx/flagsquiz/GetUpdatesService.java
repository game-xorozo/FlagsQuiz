package com.nileworx.flagsquiz;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nileworx.flagsquiz.R;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;



public class GetUpdatesService extends IntentService {

	DAO db;
	Context context;
	JSONArray entries = null;

	Handler mHandler;

	Cursor c;
	
	String flImageDir;

	String siteUrl;

	public ImageLoader imgLoader;

	String jsonExtra;
	JSONObject json;

	JSONArray flags = null;

	private ConnectionDetector cd;
	Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

	private NotificationManager nm;
	Notification mBuilder;
	RemoteViews contentView;

	int mCount, mMax;

	// ==============================================================================

	public GetUpdatesService() {
		super("getUpdatesService");
		mHandler = new Handler();
	}

	// ==============================================================================

	// @Override
	public void onDestroy() {
		db.closeDatabase();		
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	// ==============================================================================

	@Override
	protected void onHandleIntent(Intent intent) {		
		Bundle extras = intent.getExtras();
		if (extras != null) {
			jsonExtra = extras.getString("json");
		}

		cd = new ConnectionDetector(context);

		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present

			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle(context.getResources().getString(R.string.internetErrorDlgTitle));
			builder.setMessage(context.getResources().getString(R.string.internetErrorDlgMessage));

			builder.setNeutralButton(context.getResources().getString(R.string.internetErrorDlgOkBtn), new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {

				}

			});

			builder.show();

		} else {

			CharSequence title = context.getResources().getString(R.string.downloadingInitializing);

			contentView = new RemoteViews(getPackageName(), R.layout.download_progress);
			contentView.setImageViewResource(R.id.status_icon, R.drawable.app_icon_medium);
			contentView.setTextViewText(R.id.status_text, title);
			contentView.setProgressBar(R.id.status_progress, 100, 0, false);

			Intent in = new Intent(context, SettingsActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);			

			PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

			mBuilder = new NotificationCompat.Builder(context).setTicker(context.getResources().getString(R.string.downloadingInitializing)).setSmallIcon(R.drawable.app_icon_sml).setContentIntent(contentIntent).build();

			mBuilder.contentView = contentView;

			nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

			nm.notify(1, mBuilder);

			getUpdates(jsonExtra);

		}

	}

	// ==============================================================================

	public int onStartCommand(Intent intent, int flags, int startId) {
		
		context = getApplicationContext();
		db = new DAO(context);
		db.open();

		siteUrl = context.getResources().getString(R.string.siteUrl);
		
		flImageDir = siteUrl + "global/uploads/flags/";

		imgLoader = new ImageLoader(context);

		return super.onStartCommand(intent, flags, startId);
	}

	// ==============================================================================

	public void getUpdates(final String j) {

		new Thread(new Runnable() {
			public void run() {
				mCount = 0;
				// mRun = true;
				// while (mRun) {

				// SystemClock.sleep(1000);

				try {
					json = new JSONObject(j);
			
					// Getting Array of flags
					flags = json.getJSONArray("flags");

					mMax = flags.length();

					

					// looping through All flags
					for (int i = 0; i < flags.length(); i++) {

						JSONObject e = flags.getJSONObject(i);

						// Storing each json item in variable						
						String _flid = e.getString("_flid");
						String fl_country = e.getString("fl_country");
						String fl_image = e.getString("fl_image");						
						String fl_wikipedia = e.getString("fl_wikipedia");						

						db.addFlag(fl_country, fl_image, fl_wikipedia, Integer.parseInt(_flid));

						++mCount;

						CharSequence title = context.getResources().getString(R.string.downloadingProgressBar) + " " + (int) (((double) mCount / (double) mMax) * 100) + "%";

						contentView.setTextViewText(R.id.status_text, title);
						contentView.setProgressBar(R.id.status_progress, mMax, mCount, false);

						mBuilder.contentView = contentView;
						nm.notify(1, mBuilder);
					}

					if ((mCount % mMax) == 0) {
						nm.cancelAll();

						Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon_medium);

						NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setTicker(context.getResources().getString(R.string.updateSuccess)).setLargeIcon(largeIcon).setSmallIcon(R.drawable.app_icon_sml).setContentTitle("Flags Quiz").setContentText(context.getResources().getString(R.string.updateSuccess))
								.setAutoCancel(true);

						PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);

						mBuilder.setContentIntent(contentIntent);

						NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						// mId allows you to update the notification
						// later on.
						mNotificationManager.notify(1, mBuilder.build());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	// ==============================================================================

	public String downloadImage(String imageUrl, String id, String folder) {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "Flags Quiz/" + folder + "/";
		Log.d("path", path);
		File FlagsQuiz = new File(path);
		FlagsQuiz.mkdirs();
		String data = "";
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);

			// create a File object for the parent directory
			File FlagsQuizDirectory = new File(path, id + ".png");

			// have the object build the directory structure, if needed.
			FlagsQuizDirectory.getParentFile().mkdirs();
			FlagsQuizDirectory.createNewFile();
			data = String.valueOf(id + ".png");

			FileOutputStream stream = new FileOutputStream(path + data);

			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			myBitmap.compress(Bitmap.CompressFormat.PNG, 85, outstream);
			byte[] byteArray = outstream.toByteArray();

			stream.write(byteArray);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}
}