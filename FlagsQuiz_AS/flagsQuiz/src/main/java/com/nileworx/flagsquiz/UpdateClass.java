package com.nileworx.flagsquiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nileworx.flagsquiz.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.KeyEvent;

public class UpdateClass {

	public Context context;

	String siteUrl, updatesUrl;

	DAO db;
	Cursor c;

	String flImageDir;
	int lastFlag;

	JSONArray flags = null;

	JSONObject json;
	String jsonResultNull = "";
	CustomDialog dialog;

	private ConnectionDetector cd;
	Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

	// ==============================================================================

	public UpdateClass(Context context) {
		this.context = context;

		db = new DAO(context);
		db.open();

		lastFlag = db.getLastFlag();

		siteUrl = context.getResources().getString(R.string.siteUrl);
		updatesUrl = siteUrl + "site/get_updates/" + String.valueOf(lastFlag);

		flImageDir = siteUrl + "global/ufloads/flags/";

		dialog = new CustomDialog(context);

	}

	// ==============================================================================

	public void handleUpdates() {

		// check first for internet
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
			new CheckUpdates().execute(new String[] { updatesUrl });
		}
	}

	private class CheckUpdates extends AsyncTask<String, Void, Void> {

		ProgressDialog mProgressDialog;

		@Override
		protected void onPostExecute(Void result) {

			mProgressDialog.dismiss();
			if (!isCancelled()) {
				if (json != null) {
					if (jsonResultNull.equals("true")) {

						dialog.showDialog(R.layout.blue_dialog, "noUpdatesDlg", context.getResources().getString(R.string.noUpdatesDlg), null);

					} else {
						String updatesDlgMsg = String.format(context.getResources().getString(R.string.updatesDlg), String.valueOf(flags.length()));
						dialog.showDialog(R.layout.blue_dialog, "updatesDlg", updatesDlgMsg, json.toString());
					}

				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle(context.getResources().getString(R.string.internetErrorDlgTitle));
					builder.setMessage(context.getResources().getString(R.string.internetErrorDlgMessage));

					builder.setNeutralButton(context.getResources().getString(R.string.internetErrorDlgOkBtn), new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int id) {

						}

					});

					builder.show();
				}
			}

		}

		// ------------------------------------------------------------------------

		@Override
		protected void onPreExecute() {

			mProgressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.checkUpdatesDlgTitle), context.getResources().getString(R.string.checkUpdatesDlgMessage));
			mProgressDialog.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
						cancel(true);
						mProgressDialog.dismiss();
					}
					return false;
				}
			});

		}

		// ------------------------------------------------------------------------

		@Override
		protected Void doInBackground(String... params) {

			// Creating JSON Parser instance
			JSONParser jParser = new JSONParser();

			json = jParser.getJSONFromUrl(params[0]);

			//
			try {
				if (json != null) {
					flags = json.getJSONArray("flags");

					if (flags.length() == 0) {
						jsonResultNull = "true";
					}
				} else {
					jsonResultNull = "true";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onCancelled() {
			mProgressDialog.dismiss();
		}
	}
}