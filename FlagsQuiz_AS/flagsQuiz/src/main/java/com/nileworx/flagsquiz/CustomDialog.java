package com.nileworx.flagsquiz;

import com.nileworx.flagsquiz.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialog {

	public Context context;
	MediaPlayer sound;

	SharedPreferences mSharedPreferences;
	Editor editor;

	DAO db;
	Cursor c;
	SoundClass sou;

	// ==============================================================================

	public CustomDialog(Context context) {
		this.context = context;

		db = new DAO(context);
		db.open();

		sou = new SoundClass(context);

		mSharedPreferences = context.getSharedPreferences("MyPref", 0);
		editor = mSharedPreferences.edit();
	}

	// ==============================================================================

	public void showDialog(int layout, String dialogName, String msg, String data) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		dialog.setContentView(layout);

		Typeface hoboSTD = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getResources().getString(R.string.main_font));

		// set the custom dialog components - text, image and button
		TextView message = (TextView) dialog.findViewById(R.id.message);
		message.setText(msg.trim());
		message.setTypeface(hoboSTD);

		LinearLayout confirmDlg = (LinearLayout) dialog.findViewById(R.id.confirmDlg);
		LinearLayout wrongDlg = (LinearLayout) dialog.findViewById(R.id.wrongDlg);
		LinearLayout alertDlg = (LinearLayout) dialog.findViewById(R.id.alertDlg);
		LinearLayout askRateDlg = (LinearLayout) dialog.findViewById(R.id.askRateDlg);

		if (dialogName.equals("exitDlg")) {
			confirmDlg.setVisibility(View.VISIBLE);
			exitDlg(dialog);
		} else if (dialogName.equals("updatesActivityDlg")) {
			confirmDlg.setVisibility(View.VISIBLE);
			updatesActivityDlg(dialog, data);
		} else if (dialogName.equals("resetDlg")) {
			confirmDlg.setVisibility(View.VISIBLE);
			resetDlg(dialog);
		} else if (dialogName.equals("noUpdatesDlg")) {
			alertDlg.setVisibility(View.VISIBLE);
			noUpdatesDlg(dialog);
		} else if (dialogName.equals("updatesDlg")) {
			confirmDlg.setVisibility(View.VISIBLE);
			updatesDlg(dialog, data);
		} else if (dialogName.equals("noCoinsDlg")) {
			alertDlg.setVisibility(View.VISIBLE);
			noCoinsDlg(dialog);
		} else if (dialogName.equals("helpDlg")) {
			confirmDlg.setVisibility(View.VISIBLE);
			helpDlg(dialog);
		} else if (dialogName.equals("rateDlg")) {
			askRateDlg.setVisibility(View.VISIBLE);
			rateDlg(dialog, data);
		} else if (dialogName.equals("solutionDlg")) {
			alertDlg.setVisibility(View.VISIBLE);
			solutionDlg(dialog);
		} else if (dialogName.equals("correctDlg")) {
			GameActivity act = (GameActivity) context;
			correctDlg(dialog, data);
		} else if (dialogName.equals("wrongDlg")) {
			wrongDlg.setVisibility(View.VISIBLE);
			wrongDlg(dialog);
		} else if (dialogName.equals("finishDlg")) {
			Configuration config = context.getResources().getConfiguration();
			float textSize = 30f;

			if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
				textSize = 24f;
			} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
				textSize = 24f;
			}
			message.setTextSize(textSize);
			alertDlg.setVisibility(View.VISIBLE);
			finishDlg(dialog);
		}

		dialog.setCanceledOnTouchOutside(false);

		dialog.show();
	}

	// ==============================================================================

	private void exitDlg(final Dialog dialog) {

		Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
		// if button is clicked, close the custom dialog
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});

		Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
		// if button is clicked, close the custom dialog
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				((Activity) context).finish();
				System.exit(0);
			}
		});
	}

	// ==============================================================================

	private void updatesActivityDlg(final Dialog dialog, final String json) {

		Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
		// if button is clicked, close the custom dialog
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
				// ((Activity) context).finish();
			}
		});

		Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
		// if button is clicked, close the custom dialog
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
				// ((Activity) context).finish();
				Intent getUpdates = new Intent(context, GetUpdatesService.class);
				getUpdates.putExtra("json", json);
				((Activity) context).startService(getUpdates);
			}
		});

		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
					// ((Activity) context).finish();
					dialog.dismiss();
				}
				return false;
			}
		});

	}

	// ==============================================================================

	private void resetDlg(final Dialog dialog) {

		Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
		// if button is clicked, close the custom dialog
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});

		Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
		// if button is clicked, close the custom dialog
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				db.resetGame();
				Intent intent = ((Activity) context).getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				((Activity) context).finish();
				context.startActivity(intent);
				Toast.makeText(context, "The game has been reset successfully", Toast.LENGTH_LONG).show();
			}
		});

	}

	// ==============================================================================

	private void noUpdatesDlg(final Dialog dialog) {
		Button dismissBtn = (Button) dialog.findViewById(R.id.dismissBtn);
		// if button is clicked, close the custom dialog
		dismissBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});
	}

	// ==============================================================================

	private void updatesDlg(final Dialog dialog, final String json) {

		Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
		// if button is clicked, close the custom dialog
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});

		Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
		// if button is clicked, close the custom dialog
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				Intent getUpdates = new Intent(context, GetUpdatesService.class);
				getUpdates.putExtra("json", json);
				context.startService(getUpdates);

				dialog.dismiss();
			}
		});
	}

	// ==============================================================================

	private void rateDlg(final Dialog dialog, final String marketLink) {

		Button rateBtn = (Button) dialog.findViewById(R.id.rateBtn);
		// if button is clicked, close the custom dialog
		rateBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
				GameActivity gameAct = (GameActivity) context;
				Intent intent = new Intent(Intent.ACTION_VIEW);

				intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));

				if (!gameAct.MyStartActivity(intent)) {
					// Market (Google play) app seems not
					// installed, let's try
					// to open a webbrowser
					intent.setData(Uri.parse(marketLink));
					if (!gameAct.MyStartActivity(intent)) {
						// Well if this also fails, we have run
						// out of options,
						// inform the user.
						Toast.makeText(context, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
					} else {
						editor.putInt("usingNum", 100);
						editor.commit();
					}
				} else {
					editor.putInt("usingNum", 100);
					editor.commit();
				}
			}
		});

		Button laterBtn = (Button) dialog.findViewById(R.id.laterBtn);
		// if button is clicked, close the custom dialog
		laterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				editor.putInt("usingNum", 0);
				editor.commit();
				dialog.dismiss();
			}
		});
	}

	// ==============================================================================

	private void noCoinsDlg(final Dialog dialog) {
		Button dismissBtn = (Button) dialog.findViewById(R.id.dismissBtn);
		// if button is clicked, close the custom dialog
		dismissBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});
	}

	// ==============================================================================

	private void helpDlg(final Dialog dialog) {

		Button noBtn = (Button) dialog.findViewById(R.id.noBtn);
		// if button is clicked, close the custom dialog
		noBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});

		Button yesBtn = (Button) dialog.findViewById(R.id.yesBtn);
		// if button is clicked, close the custom dialog
		yesBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				GameActivity act = (GameActivity) context;

				String coins = "0";
				switch (act.globalViewId) {
				case R.id.facebook:
					coins = "5";
					break;
				case R.id.twitter:
					coins = "5";
					break;
				case R.id.hide:
					coins = "5";
					break;
				case R.id.letter:
					coins = "5";
					break;
				case R.id.solution:
					coins = "10";
					break;
				}

				db.addUsedCoins(coins);
				act.coinsValue.setText(String.valueOf(act.getCoinsNumber()));
				dialog.dismiss();
				act.executeHelp(act.globalViewId);

			}
		});
	}

	// ==============================================================================

	private void solutionDlg(final Dialog dialog) {
		Button dismissBtn = (Button) dialog.findViewById(R.id.dismissBtn);
		// if button is clicked, close the custom dialog
		dismissBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});
	}

	// ==============================================================================

	private void correctDlg(final Dialog dialog, final String flagId) {
		int nextFlag = db.getNextFlag();
		final String nextFlagId = String.valueOf(nextFlag);
		final String flWikipedia = db.getFlagWikipedia(flagId);

		Button mainBtn = (Button) dialog.findViewById(R.id.mainBtn);
		// if button is clicked, close the custom dialog
		mainBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				Intent intent = new Intent(context, MainActivity.class);

				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				((Activity) context).finish();
				context.startActivity(intent);
			}
		});

		Button wikipediaBtn = (Button) dialog.findViewById(R.id.wikipediaBtn);
		if (!flWikipedia.equals("") && flWikipedia != null && URLUtil.isValidUrl(flWikipedia)) {

			wikipediaBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(flWikipedia);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					context.startActivity(intent);
				}
			});
		} else {
			wikipediaBtn.setVisibility(View.GONE);
		}

		Button nextBtn = (Button) dialog.findViewById(R.id.nextBtn);
		// if button is clicked, close the custom dialog
		nextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);

				Intent intent = new Intent(context, GameActivity.class);
				intent.putExtra("FlagId", nextFlagId);
				((Activity) context).finish();
				context.startActivity(intent);

			}
		});

		if (nextFlagId.equals("0")) {
			nextBtn.setVisibility(View.GONE);
		}

		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
					Intent intent = new Intent((Activity) context, MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					((Activity) context).finish();
					context.startActivity(intent);
				}
				return false;
			}
		});
	}

	// ==============================================================================

	// private void openFlagsDlg(final Dialog dialog) {
	// Button purchaseBtn = (Button) dialog.findViewById(R.id.purchaseBtn);
	// // if button is clicked, close the custom dialog
	// purchaseBtn.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// sou.playSound(R.raw.buttons);
	// dialog.dismiss();
	// FlagsActivity act = (FlagsActivity) context;
	//
	// FlagsActivity.mHelper.launchPurchaseFlow(,
	// FlagsActivity.SKU_OPEN_LEVELS, 10001,
	// act.mPurchaseFinishedListener,
	// "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
	// }
	// });
	//
	// Button thanksBtn = (Button) dialog.findViewById(R.id.thanksBtn);
	// // if button is clicked, close the custom dialog
	// thanksBtn.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// sou.playSound(R.raw.buttons);
	// dialog.dismiss();
	// }
	// });
	//
	// dialog.setOnKeyListener(new OnKeyListener() {
	// @Override
	// public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
	// {
	// if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==
	// KeyEvent.ACTION_UP) {
	// Intent intent = ((Activity) context).getIntent();
	// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	// ((Activity) context).finish();
	// context.startActivity(intent);
	// }
	// return false;
	// }
	// });
	// }

	// ==============================================================================

	private void wrongDlg(final Dialog dialog) {
		Button mainBtn = (Button) dialog.findViewById(R.id.mainBtn);
		// if button is clicked, close the custom dialog
		mainBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				Intent intent = new Intent(context, MainActivity.class);

				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				((Activity) context).finish();
				context.startActivity(intent);
			}
		});

		Button retryBtn = (Button) dialog.findViewById(R.id.retryBtn);
		// if button is clicked, close the custom dialog
		retryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);

				Intent intent = ((Activity) context).getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				((Activity) context).finish();
				context.startActivity(intent);
			}
		});

		dialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
					Intent intent = ((Activity) context).getIntent();
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					((Activity) context).finish();
					context.startActivity(intent);
				}
				return false;
			}
		});
	}

	// ==============================================================================

	private void finishDlg(final Dialog dialog) {
		Button dismissBtn = (Button) dialog.findViewById(R.id.dismissBtn);
		// if button is clicked, close the custom dialog
		dismissBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				dialog.dismiss();
			}
		});
	}

}