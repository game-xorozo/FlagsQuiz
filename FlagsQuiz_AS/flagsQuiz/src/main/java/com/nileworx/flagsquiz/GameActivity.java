package com.nileworx.flagsquiz;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.nileworx.flagsquiz.R;

public class GameActivity extends BaseGameActivity implements OnTouchListener {

	GridView lettersGrid;
	String marketLink;

	LinearLayout spacesGrid1, spacesGrid2;
	TextView[] spaceViews;

	LettersAdapter leAdapter;

	DAO db;
	Cursor c;

	String flImageDir;
	private ImageLoader imgLoader;

	ArrayList<HashMap<String, String>> lettersArray;
	HashMap<String, String> lettersMap;

	ArrayList<HashMap<String, String>> spacesArray;
	HashMap<String, String> spacesMap;

	ArrayList<HashMap<String, String>> positionsArray;
	HashMap<String, String> positionsMap;

	static final String KEY_ID = "_flid";
	static final String KEY_COUNTRY = "fl_country";
	static final String KEY_WIKIPEDIA = "fl_wikipedia";
	static final String KEY_LETTER = "fl_letter";
	static final String KEY_IMAGE = "fl_image";
	static final String KEY_TRIES = "fl_tries";
	static final String KEY_POINTS = "fl_points";
	static final String KEY_COMPLETED = "fl_completed";
	static final String KEY_IMAGE_SDCARD = "fl_image_sdcard";
	static final String KEY_ORDER = "fl_order";
	static final String KEY_WEB_ID = "fl_web_id";

	static final String KEY_LETTER_GAME = "letter";
	static final String KEY_SPACE_GAME = "space";

	static final String KEY_LETTER_POSITION = "letter_position";
	static final String KEY_SPACE_POSITION = "space_position";

	int countSpaces;
	int flTries;
	int flPoints;
	int result;
	int coins;

	String flagId;
	String flSolution;
	String siteUrl, urlToShare;

	Animation animBlink, animShake, animShakeLetter, animZoomIn, animZoomOut;
	// MediaPlayer sound;
	SoundClass sou;
	CustomDialog dialog;
	String quizText, flCountry, flImageFile, isFlCompleted, flWikipedia, flLetter, flImageSDCard;
	int flIsCountry, flOrder, flWebId;
	char[] alphabetLettersArray, alphabetSpacesArray;

	SharedPreferences mSharedPreferences;
	Editor e;

	LinearLayout leftHelps, rightHelps;
	RelativeLayout layout;
	TextView scoreTitle, scoreValue, coinsX, coinsValue;

	ImageButton facebook, twitter, hide, letter, solution;

	ImageView flImage, flLargeImage;

	LayoutInflater layoutInflater;
	View popuplayout;

	View popupView;
	PopupWindow popupWindow;
	Button btnOk;
	int globalViewId;

	int isLetterHelpOn = 0;

	private InterstitialAd interstitial;
	private ConnectionDetector cd;

	int sWidth, sHeight;
	double screenInches;
	private long mLastClickTime = 0;

	// ==============================================================================

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

		sWidth = displaymetrics.widthPixels;
		sHeight = displaymetrics.heightPixels;

		int dens = displaymetrics.densityDpi;
		double wi = (double) sWidth / (double) dens;
		double hi = (double) sHeight / (double) dens;
		double x = Math.pow(wi, 2);
		double y = Math.pow(hi, 2);
		screenInches = Math.sqrt(x + y);

		if (sWidth > 480 && screenInches >= 4 && screenInches <= 5) {
//			Log.e("if", "1");
			setContentView(R.layout.activity_game_4x);
		} else if (screenInches >= 5 && screenInches <= 6.5) {
//			Log.e("if", "2");
			setContentView(R.layout.activity_game_5x);
		} else if (screenInches > 6.5 && screenInches < 9) {
//			Log.e("if", "3");
			setContentView(R.layout.activity_game_7x);
		} else {
//			Log.e("if", "4");
			setContentView(R.layout.activity_game);
		}

		dialog = new CustomDialog(GameActivity.this);
		sou = new SoundClass(GameActivity.this);

		cd = new ConnectionDetector(GameActivity.this);

		imgLoader = new ImageLoader(getApplicationContext());

		AdView ad = (AdView) findViewById(R.id.adView);
		if (ad != null) {
			ad.loadAd(new AdRequest.Builder().build());
		}

		// Create the interstitial.
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getResources().getString(R.string.adInterstitialUnitId));

		// Create ad request.
		AdRequest adRequest = new AdRequest.Builder().build();

		// Begin loading your interstitial.
		interstitial.loadAd(adRequest);

		db = new DAO(this);
		db.open();

		siteUrl = getResources().getString(R.string.siteUrl);

		flagId = getIntent().getStringExtra("FlagId");

		marketLink = "https://play.google.com/store/apps/details?id=" + getPackageName();

		mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
		e = mSharedPreferences.edit();

		if (mSharedPreferences.getInt("usingNum", 0) != 100) {
			countUsingNumForRating();
		}

		layout = (RelativeLayout) findViewById(R.id.titleBar);

		if (getResources().getString(R.string.langDirection).equals("rtl")) {
			LinearLayout scoreLayout = (LinearLayout) layout.findViewById(R.id.scoreLayout);
			ArrayList<View> scoreViews = new ArrayList<View>();
			for (int z = 0; z < scoreLayout.getChildCount(); z++) {
				scoreViews.add(scoreLayout.getChildAt(z));
			}
			scoreLayout.removeAllViews();
			for (int z = scoreViews.size() - 1; z >= 0; z--) {
				scoreLayout.addView(scoreViews.get(z));
			}

			LinearLayout coinsLayout = (LinearLayout) layout.findViewById(R.id.coinsLayout);
			ArrayList<View> coinsViews = new ArrayList<View>();
			for (int z = 0; z < coinsLayout.getChildCount(); z++) {
				coinsViews.add(coinsLayout.getChildAt(z));
			}
			coinsLayout.removeAllViews();
			for (int z = coinsViews.size() - 1; z >= 0; z--) {
				coinsLayout.addView(coinsViews.get(z));
			}
		}

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/" + getResources().getString(R.string.main_font));

		scoreTitle = (TextView) layout.findViewById(R.id.scoreTitle);
		scoreTitle.setText(getResources().getString(R.string.score));
		scoreTitle.setTypeface(tf);

		if (getResources().getString(R.string.langDirection).equals("rtl")) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(7, 0, 0, 0);
			scoreTitle.setLayoutParams(lp);
		}

		scoreValue = (TextView) layout.findViewById(R.id.scoreValue);
		scoreValue.setTypeface(tf);
		scoreValue.setText(String.valueOf(getTotalScoreNumber()));

		coinsX = (TextView) layout.findViewById(R.id.coinsX);
		coinsX.setTypeface(tf);

		coinsValue = (TextView) layout.findViewById(R.id.coinsValue);
		coinsValue.setTypeface(tf);
		coinsValue.setText(String.valueOf(getCoinsNumber()));

		facebook = (ImageButton) findViewById(R.id.facebook);
		twitter = (ImageButton) findViewById(R.id.twitter);
		hide = (ImageButton) findViewById(R.id.hide);
		letter = (ImageButton) findViewById(R.id.letter);
		solution = (ImageButton) findViewById(R.id.solution);

		// spacesGrid = (GridView) findViewById(R.id.spacesGrid);

		spacesGrid1 = (LinearLayout) findViewById(R.id.spacesGrid1);
		spacesGrid2 = (LinearLayout) findViewById(R.id.spacesGrid2);
		
		lettersGrid = (GridView) findViewById(R.id.lettersGrid);

		flImage = (ImageView) findViewById(R.id.flag);
		flLargeImage = (ImageView) findViewById(R.id.flLargeImage);

		leftHelps = (LinearLayout) findViewById(R.id.leftHelps);
		rightHelps = (LinearLayout) findViewById(R.id.rightHelps);

		c = db.getOneFlag(flagId);

		if (c.getCount() != 0) {
			flTries = c.getInt(c.getColumnIndex(KEY_TRIES));
			flCountry = c.getString(c.getColumnIndex(KEY_COUNTRY)).trim();
			flImageFile = c.getString(c.getColumnIndex(KEY_IMAGE)).trim();
			flWikipedia = c.getString(c.getColumnIndex(KEY_WIKIPEDIA)).trim();
			flLetter = c.getString(c.getColumnIndex(KEY_LETTER));
			isFlCompleted = c.getString(c.getColumnIndex(KEY_COMPLETED));
			flImageSDCard = c.getString(c.getColumnIndex(KEY_IMAGE_SDCARD));
			flOrder = c.getInt(c.getColumnIndex(KEY_ORDER));
			flWebId = c.getInt(c.getColumnIndex(KEY_WEB_ID));

			TextView title = (TextView) layout.findViewById(R.id.title);
			title.setText(String.valueOf(db.getFlagNumber()));

			quizText = flCountry;

			flSolution = quizText;

			if (flLetter == null || flLetter.equals("")) {
				flLetter = "1000";
			}

			if (Integer.parseInt(flImageSDCard) == 0) {
				AssetManager assetManager = getAssets();
				InputStream istr = null;
				try {
					istr = assetManager.open("flags/" + flImageFile);
				} catch (IOException e) {
					Log.e("assets", assetManager.toString());
					e.printStackTrace();
				}
				Bitmap bmp = BitmapFactory.decodeStream(istr);
				flImage.setImageBitmap(bmp);
				flLargeImage.setImageBitmap(bmp);
			} else {

				siteUrl = getResources().getString(R.string.siteUrl);

				flImageDir = siteUrl + "global/uploads/flags/";

				imgLoader.DisplayImage(flImageDir + flImageFile, flImage);
				imgLoader.DisplayImage(flImageDir + flImageFile, flLargeImage);

			}

			if (!isFlCompleted.equals("1")) {

				// if
				// (flImage.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.no_image).getConstantState()))
				// {
				if (flImage.getDrawable() == null) {
					if (!cd.isConnectingToInternet()) {
						AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
						builder.setTitle(getResources().getString(R.string.connectDlgTitle));
						builder.setMessage(getResources().getString(R.string.connectDlgMessage));
						builder.setPositiveButton(getResources().getString(R.string.connectDlgRefreshBtn), new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {
								// showInterstitialAd();

								Intent intent = getIntent();
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								finish();
								startActivity(intent);

							}

						});

						builder.setNegativeButton(getResources().getString(R.string.connectDlgBackBtn), new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int id) {

								Intent intent = new Intent(GameActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								finish();
								startActivity(intent);
							}

						});

						builder.show();
					}
				}

				generateSpaces(quizText);
				generateLetters(quizText);

				if (getResources().getString(R.string.langDirection).equals("rtl")) {
					rotateSpacesGrids();
				}

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						executeSpaceHelpIfAlreadyUsed();
						executeLettersHelpIfAlreadyUsed();
					}
				}, 0);

				leftHelps.setVisibility(View.VISIBLE);
				rightHelps.setVisibility(View.VISIBLE);

				facebook.setOnClickListener(helpClickHandler);
				twitter.setOnClickListener(helpClickHandler);
				hide.setOnClickListener(helpClickHandler);
				letter.setOnClickListener(helpClickHandler);
				solution.setOnClickListener(helpClickHandler);
				// }
			}

		}

		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sou.playSound(R.raw.buttons);
				finish();
			}
		});

//		final RelativeLayout largeImageLayout = (RelativeLayout) findViewById(R.id.flLargeImageLayout);
//		flImage.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				largeImageLayout.setVisibility(View.VISIBLE);
//				animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
//				largeImageLayout.setAnimation(animZoomIn);
//			}
//		});

//		largeImageLayout.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
//				largeImageLayout.startAnimation(animZoomOut);
//				largeImageLayout.setVisibility(View.GONE);
//				animZoomOut.setAnimationListener(new Animation.AnimationListener() {
//					@Override
//					public void onAnimationStart(Animation animation) {
//
//					}
//
//					@Override
//					public void onAnimationEnd(Animation animation) {
//						largeImageLayout.clearAnimation();
//					}
//
//					@Override
//					public void onAnimationRepeat(Animation animation) {
//
//					}
//				});
//			}
//		});

		setButtonsStateForUsedHelps();
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

	public void generateSpaces(String quizText) {

		alphabetSpacesArray = quizText.toCharArray();
		spacesArray = new ArrayList<HashMap<String, String>>();

		spaceViews = new TextView[quizText.length()];
		for (int i = 0; i < quizText.length(); i++) {
			spacesMap = new HashMap<String, String>();
			spacesMap.put(KEY_SPACE_GAME, Character.toString(quizText.charAt(i)));

			spacesArray.add(spacesMap);

			spaceViews[i] = new TextView(this);
			// letterViews[i].setText(Character.toString(quizText.charAt(i)));

			Configuration config = getResources().getConfiguration();

			int width = 0;
			int height = 0;
			int textSize = 0;

			if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
				width = 30;
				height = 30;
				textSize = 24;
			} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
				width = 60;
				height = 60;
				textSize = 20;
//				Log.e("inches", "hh");
//				Log.e("sWidth", String.valueOf(sWidth));
				if (sWidth <= 320) {
					width = 30;
					height = 30;
					textSize = 20;
				}
				if (sWidth > 480 && screenInches >= 4 && screenInches <= 5) {
					if (sWidth >= 1080) {
						width = 120;
						height = 120;
						textSize = 22;
					} else {
						width = 80;
						height = 80;
						textSize = 22;
					}
//					Log.e("inches", "tt");
				}
			} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
				width = 50;
				height = 50;
				textSize = 28;
//				Log.e("inches", "aqq");
				if (screenInches > 6.5 && screenInches < 9) {
					width = 70;
					height = 70;
					textSize = 36;
				}
			} else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
				width = 100;
				height = 96;
				textSize = 56;
			} else {
				width = 60;
				height = 60;
				textSize = 40;
			}

			// http://www.designbyexperience.com/px-to-dp-converter/

			spaceViews[i].setLayoutParams(new LayoutParams(width, height));

			spaceViews[i].setGravity(Gravity.CENTER);
			spaceViews[i].setTextColor(Color.WHITE);
			if (getResources().getString(R.string.langDirection).equals("rtl")) {
				rotateView(spaceViews[i]);
			}
			if (!Character.toString(quizText.charAt(i)).equals(" ")) {
				spaceViews[i].setBackgroundResource(R.drawable.letter_space);
				// spaceViews[i].setTextAppearance(this,
				// android.R.style.TextAppearance_Large);
				spaceViews[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
				spaceViews[i].setTypeface(Typeface.DEFAULT_BOLD);
			} else {
				spaceViews[i].setLayoutParams(new LayoutParams(width / 3, height));
				spaceViews[i].setBackgroundColor(Color.TRANSPARENT);
				spaceViews[i].setPadding(0, 0, 0, 0);
				spaceViews[i].setVisibility(View.INVISIBLE);
				spaceViews[i].setText(" ");
			}

			// add to layout
			if (i < 8) {
				spacesGrid1.addView(spaceViews[i]);
			} else if (i < 16) {
				spacesGrid2.addView(spaceViews[i]);
			}
			// else if (i < 24) {
			// spacesGrid3.addView(spaceViews[i]);
			// } else if (i < 32) {
			// spacesGrid4.addView(spaceViews[i]);
			// }
			spaceViews[i].setOnClickListener(new spacesItemClickHandler(i));
		}

	}

	// ==============================================================================

	public void generateLetters(String quizText) {

		int y = 16;
		String alphabet = getResources().getString(R.string.alphabet);

		countSpaces = quizText.length() - quizText.replace(" ", "").length();

		alphabetLettersArray = quizText.toCharArray();

		lettersArray = new ArrayList<HashMap<String, String>>();

		lettersGrid.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}

		});

		quizText = quizText.replace(" ", "");

		for (int i = 0; i < quizText.length(); i++) {

			lettersMap = new HashMap<String, String>();
			lettersMap.put(KEY_LETTER_GAME, Character.toString(quizText.charAt(i)));
			lettersMap.put("is_real", "1");

			lettersArray.add(lettersMap);

			if (i == quizText.length() - 1) {
				if (quizText.length() >= y) {
					y = quizText.length() + 4;
				}
				for (int x = quizText.length(); x < y; x++) {
					Random random = new Random();
					int chNum = random.nextInt(alphabet.length());
					lettersMap = new HashMap<String, String>();
					lettersMap.put(KEY_LETTER_GAME, Character.toString(alphabet.charAt(chNum)));
					lettersMap.put("is_real", "0");
					lettersArray.add(lettersMap);
				}
			}
		}

		Collections.shuffle(lettersArray);

		leAdapter = new LettersAdapter(this, lettersArray);
		lettersGrid.setAdapter(leAdapter);

		positionsArray = new ArrayList<HashMap<String, String>>();

		// Click event for single grid row
		lettersGrid.setOnItemClickListener(lettersItemClickHandler);

	}

	// ==============================================================================

	private void addLetters(int position) {
		for (int i = 0; i < spaceViews.length; i++) {

			TextView leSpace = (TextView) spaceViews[i];
			if (leSpace.getVisibility() == View.INVISIBLE) {
				continue;
			}
			// TextView leSpace = (TextView) v.findViewById(R.id.letterSpace);
			if (leSpace.getText().equals("") || leSpace.getText().equals("?")) {
				leSpace.setText(lettersArray.get(position).get(KEY_LETTER_GAME).toUpperCase());

				positionsMap = new HashMap<String, String>();
				positionsMap.put(KEY_LETTER_POSITION, String.valueOf(position));

				positionsMap.put(KEY_SPACE_POSITION, String.valueOf(i));

				positionsArray.add(positionsMap);

				checkIfFinal();

				break;
			}
		}

	}

	// ==============================================================================

	private void checkIfFinal() {

		if (spaceViews.length == positionsArray.size() + countSpaces) {
			for (int x = 0; x < spaceViews.length; x++) {
				// View vFinal = (View) spacesGrid1.getChildAt(x);
				TextView leSpaceFinal = (TextView) spaceViews[x];
				if (leSpaceFinal.getText().toString().equals(String.valueOf(alphabetLettersArray[x]).toUpperCase()) == false) {
					if (flTries < 4) {
						flTries++;
						db.setTries(flagId, flTries);
						result = 0;
					}
					break;
				} else {
					if (x == spaceViews.length - 1) {

						flPoints = 0;
						switch (flTries) {
						case 0:
							flPoints = 100;
							break;

						case 1:
							flPoints = 80;
							break;

						case 2:
							flPoints = 60;
							break;

						case 3:
							flPoints = 40;
							break;

						case 4:
							flPoints = 20;
							break;
						}
						result = 1;
					}
				}

			}

			isRight(result);

		}
	}

	// ==============================================================================

	private void addPoints() {

		db.setFlagPoints(flagId, flPoints);
		scoreValue.setText(String.valueOf(getTotalScoreNumber()));
		if (getApiClient().isConnected()) {

			Games.Leaderboards.submitScore(getApiClient(), getString(R.string.flags_score_leaderboard), getTotalScoreNumber());
		}

	}

	// ==============================================================================

	private void addCoins() {
		coins = 0;
		if (flPoints == 100) {
			coins = 2;

		} else if (flPoints > 0 && flPoints < 100) {
			coins = 1;

		}
		db.addTotalCoins(coins);

		coinsValue.setText(String.valueOf(getCoinsNumber()));

	}

	// ==============================================================================

	private void isRight(int result) {
		e.putInt("playingNum", mSharedPreferences.getInt("playingNum", 0) + 1);
		e.commit();

		if (result == 0) {

			if (mSharedPreferences.getInt("vibrate", 1) == 1) {

				Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
				// Vibrate for 500 milliseconds
				v.vibrate(500);

				LinearLayout flagLayout = (LinearLayout) findViewById(R.id.flagLayout);

				animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
				flagLayout.startAnimation(animShake);
			}

			sou.playSound(R.raw.wrong_crowd);
			dialog.showDialog(R.layout.red_dialog, "wrongDlg", getResources().getString(R.string.wrongDlg), null);

		} else {
			sou.playSound(R.raw.right_crowd);
			db.setFlagCompleted(flagId, flPoints);
			addPoints();
			addCoins();
			dialog.showDialog(R.layout.correct_dialog, "correctDlg", getResources().getString(R.string.correctDlg), String.valueOf(flagId));
		}

		if (mSharedPreferences.getInt("playingNum", 0) >= 5) {
			showInterstitialAd();
			e.putInt("playingNum", 0);
			e.commit();
		}
	}

	// ==============================================================================

	public void showInterstitialAd() {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (interstitial.isLoaded()) {
					interstitial.show();
				}

			}
		}, 3000);

	}

	// ==============================================================================

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	// ==============================================================================

	public class spacesItemClickHandler implements View.OnClickListener {
		private final int position;

		public spacesItemClickHandler(final int position) {
//			if (getResources().getString(R.string.langDirection).equals("rtl")) {
//				this.position = Math.abs(position - (spaceViews.length - 1));
//			} else {
//				this.position = position;
//			}
			
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			TextView leSpace = (TextView) spaceViews[position];
			if (!leSpace.getText().equals("") && position != Integer.parseInt(flLetter)) {
				for (int i = 0; i < positionsArray.size(); i++) {

					if (positionsArray.get(i).get(KEY_SPACE_POSITION).equals(String.valueOf(position))) {
						int letterPos = Integer.parseInt(positionsArray.get(i).get(KEY_LETTER_POSITION));
						lettersGrid.getChildAt(letterPos).setVisibility(View.VISIBLE);

						leSpace.setText("");
						sou.playSound(R.raw.space);

						positionsArray.remove(i);

						break;
					}

				}
			}
		}
	}

	// ==============================================================================

	private GridView.OnItemClickListener lettersItemClickHandler = new GridView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			if (spaceViews.length > positionsArray.size() + countSpaces) {
				lettersGrid.getChildAt(position).setVisibility(View.INVISIBLE);

				sou.playSound(R.raw.buttons);

				addLetters(position);
			}
		}
	};

	// ==============================================================================

	private View.OnClickListener helpClickHandler = new View.OnClickListener() {
		public void onClick(View v) {
			if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
				return;
			}
			mLastClickTime = SystemClock.elapsedRealtime();
			sou.playSound(R.raw.buttons);
			GameActivity.this.getHelp(v.getId());
		}
	};

	// ==============================================================================

	public void getHelp(final int viewId) {
		int remainCoins = Integer.parseInt(coinsValue.getText().toString());
		if (isHelpUsed(viewId) != 1) {
			boolean noFacebookCoins = (viewId == R.id.facebook && remainCoins < 5);
			boolean noTwitterCoins = (viewId == R.id.twitter && remainCoins < 5);
			boolean noHideCoins = (viewId == R.id.hide && remainCoins < 5);
			boolean noLetterCoins = (viewId == R.id.letter && remainCoins < 5);
			boolean noSolutionCoins = (viewId == R.id.solution && remainCoins < 10);

			if (noFacebookCoins || noTwitterCoins || noHideCoins || noLetterCoins || noSolutionCoins) {

				dialog.showDialog(R.layout.blue_dialog, "noCoinsDlg", getResources().getString(R.string.noCoinsDlg), null);

			} else {

				String msg = "";

				switch (viewId) {
				case R.id.facebook:
					msg = getResources().getString(R.string.facebookHelpDlg);
					break;

				case R.id.twitter:
					msg = getResources().getString(R.string.twitterHelpDlg);
					break;
				case R.id.hide:
					msg = getResources().getString(R.string.hideHelpDlg);
					break;
				case R.id.letter:
					msg = getResources().getString(R.string.letterHelpDlg);
					break;
				case R.id.solution:
					msg = getResources().getString(R.string.solutionHelpDlg);
					break;
				}

				globalViewId = viewId;
				dialog.showDialog(R.layout.blue_dialog, "helpDlg", msg, null);

			}

		} else {
			executeHelp(viewId);
		}

	}

	// ==============================================================================

	private int isHelpUsed(int viewId) {
		int state = 0;
		c = db.getHelpState(flagId);
		if (c.getCount() != 0) {
			switch (viewId) {
			case R.id.facebook:
				state = c.getInt(c.getColumnIndex("he_facebook"));
				break;
			case R.id.twitter:
				state = c.getInt(c.getColumnIndex("he_twitter"));
				break;
			case R.id.hide:
				state = c.getInt(c.getColumnIndex("he_hide"));
				break;
			case R.id.letter:
				state = c.getInt(c.getColumnIndex("he_letter"));
				break;
			case R.id.solution:
				state = c.getInt(c.getColumnIndex("he_solution"));
				break;
			}
		}

		return state;
	}

	// ==============================================================================

	private void setButtonsStateForUsedHelps() {

		c = db.getHelpState(flagId);

		if (c.getCount() != 0) {
			if (c.getInt(c.getColumnIndex("he_hide")) == 1) {
				hide.setSelected(true);
				hide.setEnabled(false);
			}

			if (c.getInt(c.getColumnIndex("he_letter")) == 1 && !flLetter.equals("1000")) {
				letter.setSelected(true);
				letter.setEnabled(false);
			}

			if (c.getInt(c.getColumnIndex("he_facebook")) == 1) {
				facebook.setAlpha(140);
			}

			if (c.getInt(c.getColumnIndex("he_twitter")) == 1) {
				twitter.setAlpha(140);
			}
		}
	}

	// ==============================================================================

	public void executeHelp(int viewId) {

		switch (viewId) {

		case R.id.facebook:
			db.updateHelpState(flagId, "he_facebook");

			urlToShare = siteUrl + "site/show_level/" + String.valueOf(flWebId);

			Intent fIntent = new Intent(Intent.ACTION_SEND);
			fIntent.setType("text/plain");
			fIntent.putExtra(Intent.EXTRA_TEXT, urlToShare);

			String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
			fIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));

			startActivity(fIntent);
			break;

		case R.id.twitter:
			db.updateHelpState(flagId, "he_twitter");

			urlToShare = siteUrl + "site/show_flag/" + String.valueOf(flWebId);

			Intent tTntent = new Intent(Intent.ACTION_SEND);

			String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s", urlEncode(getResources().getString(R.string.twitterHelpMessage) + " " + urlToShare));
			tTntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

			startActivity(tTntent);
			break;

		case R.id.hide:

			db.updateHelpState(flagId, "he_hide");
			sou.playSound(R.raw.explosion);

			animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
			hide.setSelected(true);
			hide.setEnabled(false);

			for (int i = 0; i < lettersArray.size(); i++) {
				if (lettersArray.get(i).get("is_real").equals("0")) {

					for (int x = 0; x < positionsArray.size(); x++) {
						if (positionsArray.get(x).get(KEY_LETTER_POSITION).equals(String.valueOf(i))) {
							String spacePos = positionsArray.get(x).get(KEY_SPACE_POSITION);

							TextView leSpaceHide = (TextView) spaceViews[Integer.parseInt(spacePos)];
							// leSpaceHide.setAnimation(animBlink);
							leSpaceHide.setText("");
							positionsArray.remove(x);
						}
					}
					lettersGrid.getChildAt(i).setAnimation(animBlink);
					lettersGrid.getChildAt(i).setVisibility(View.INVISIBLE);
				}
			}

			break;
		case R.id.letter:

			db.updateHelpState(flagId, "he_letter");
			animBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

			// if (isHelpUsed(R.id.letter) == 0 && flLetter.equals("1000")) {
			isLetterHelpOn = 1;
			for (int i = 0; i < spaceViews.length; i++) {
				final int position;
				// if
				// (getResources().getString(R.string.langDirection).equals("rtl"))
				// {
				// position = Math.abs(i - (spaceViews.length - 1));
				// } else {
				// position = i;
				// }

				position = i;

				final TextView leSpaceLetter = (TextView) spaceViews[position];
				if (leSpaceLetter.getVisibility() == View.INVISIBLE) {
					continue;
				}
				if (leSpaceLetter.getText().equals("")) {
					leSpaceLetter.setText("?");

					leSpaceLetter.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							int newPos;
							// if
							// (getResources().getString(R.string.langDirection).equals("rtl"))
							// {
							// newPos = Math.abs(position - (spaceViews.length -
							// 1));
							// } else {
							// newPos = position;
							// }

							newPos = position;
							if (spaceViews[newPos].getText().equals("?")) {
								letter.setSelected(true);
								letter.setEnabled(false);

								hideLetter(newPos);

								spaceViews[newPos].setText(String.valueOf(alphabetSpacesArray[newPos]).toUpperCase());
								spaceViews[newPos].setTextColor(Color.YELLOW);

								coinsValue.setText(String.valueOf(getCoinsNumber()));

								db.addLetterHelpPos(flagId, String.valueOf(newPos));
								flLetter = String.valueOf(newPos);
								stopLetterHelp();
								checkIfFinal();

							}
						}
					});
					animShakeLetter = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_letter);
					lettersGrid.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							Toast.makeText(GameActivity.this, getResources().getString(R.string.stopLetterHelp), Toast.LENGTH_LONG).show();

							if (mSharedPreferences.getInt("vibrate", 1) == 1) {

								Vibrator v = (Vibrator) GameActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
								// Vibrate for 500 milliseconds
								v.vibrate(500);

								animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
								lettersGrid.startAnimation(animShake);
							}

						}

					});
				}
			}

			break;

		case R.id.solution:
			db.updateHelpState(flagId, "he_solution");

			dialog.showDialog(R.layout.red_dialog, "solutionDlg", flSolution, null);

			break;

		}

	}

	// ==============================================================================

	private void hideLetter(int pos) {

		boolean foundIt = false;
		int invisibleChar = 0;
		String spaceChar = String.valueOf(alphabetSpacesArray[pos]).toUpperCase();

		View vLetterHelp;
		for (int i = 0; i < lettersGrid.getChildCount(); i++) {
			vLetterHelp = (View) lettersGrid.getChildAt(i);

			TextView leLetterLetter = (TextView) vLetterHelp.findViewById(R.id.letterButton);

			if (leLetterLetter.getText().equals(spaceChar) && lettersArray.get(i).get("is_real").equals("1")) {

				if (vLetterHelp.getVisibility() == View.INVISIBLE) {

					invisibleChar = i;
					continue;

				}

				positionsMap = new HashMap<String, String>();
				positionsMap.put(KEY_LETTER_POSITION, String.valueOf(i));
				positionsMap.put(KEY_SPACE_POSITION, String.valueOf(pos));
				positionsArray.add(positionsMap);

				vLetterHelp.setAnimation(animBlink);
				vLetterHelp.setVisibility(View.INVISIBLE);
				foundIt = true;
				break;
			}

		}

		if (foundIt == false) {
			for (int x = 0; x < positionsArray.size(); x++) {
				int letter = Integer.parseInt(positionsArray.get(x).get(KEY_LETTER_POSITION));
				int space = Integer.parseInt(positionsArray.get(x).get(KEY_SPACE_POSITION));
				if (letter == invisibleChar) {
					String thisChar = String.valueOf(alphabetSpacesArray[space]).toUpperCase();

					TextView leSpaceLetter = (TextView) spaceViews[space];
					if (!leSpaceLetter.getText().equals(thisChar)) {

						leSpaceLetter.setText("");
						positionsArray.remove(x);
					}

				}
			}

			vLetterHelp = (View) lettersGrid.getChildAt(invisibleChar);

			positionsMap = new HashMap<String, String>();
			positionsMap.put(KEY_LETTER_POSITION, String.valueOf(invisibleChar));
			positionsMap.put(KEY_SPACE_POSITION, String.valueOf(pos));
			positionsArray.add(positionsMap);

			vLetterHelp.setAnimation(animBlink);
			vLetterHelp.setVisibility(View.INVISIBLE);
			foundIt = true;
		}

	}

	// ==============================================================================

	public void stopLetterHelp() {
		isLetterHelpOn = 0;
		for (int i = 0; i < spaceViews.length; i++) {

			final TextView leSpaceLetter2 = (TextView) spaceViews[i];
			if (leSpaceLetter2.getVisibility() == View.INVISIBLE) {
				continue;
			}
			if (leSpaceLetter2.getText().equals("?")) {
				leSpaceLetter2.setText("");
			}

			spaceViews[i].setOnClickListener(new spacesItemClickHandler(i));
		}

		lettersGrid.setOnItemClickListener(lettersItemClickHandler);
	}

	// ==============================================================================

	public void executeSpaceHelpIfAlreadyUsed() {
		if (isHelpUsed(R.id.letter) == 1 && !flLetter.equals("1000")) {
			final int pos = Integer.parseInt(flLetter);
			TextView leSpaceLetter = (TextView) spaceViews[pos];
			leSpaceLetter.setText(String.valueOf(alphabetSpacesArray[pos]).toUpperCase());
			leSpaceLetter.setTextColor(Color.YELLOW);

			lettersGrid.post(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < lettersGrid.getChildCount(); i++) {
						View vLetterHelp = (View) lettersGrid.getChildAt(i);
						TextView flLetterView = (TextView) vLetterHelp.findViewById(R.id.letterButton);

						String spaceChar = String.valueOf(alphabetSpacesArray[pos]).toUpperCase();
						if (flLetterView.getText().equals(spaceChar) && lettersArray.get(i).get("is_real").equals("1")) {
							positionsMap = new HashMap<String, String>();
							positionsMap.put(KEY_LETTER_POSITION, String.valueOf(i));
							positionsMap.put(KEY_SPACE_POSITION, String.valueOf(pos));
							positionsArray.add(positionsMap);

							vLetterHelp.setVisibility(View.GONE);
							break;
						}
					}
				}
			});
		}
	}

	// ==============================================================================

	public void executeLettersHelpIfAlreadyUsed() {
		if (isHelpUsed(R.id.hide) == 1) {
			for (int i = 0; i < lettersArray.size(); i++) {
				final int fi = i;
				if (lettersArray.get(fi).get("is_real").equals("0")) {
					lettersGrid.post(new Runnable() {
						@Override
						public void run() {
							lettersGrid.getChildAt(fi).setVisibility(View.INVISIBLE);
						}
					});
				}
			}
		}
	}

	// ==============================================================================

	private static String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.wtf("encoder error", "UTF-8 should always be supported", e);
			throw new RuntimeException("URLEncoder.encode() failed for " + s);
		}
	}

	// ==============================================================================

	public int getTotalScoreNumber() {
		Cursor cScore = db.getTotalScore();

		int totalScore = cScore.getInt(cScore.getColumnIndex("total_score"));
		return totalScore;
	}

	// ==============================================================================

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(GameActivity.this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		startActivity(intent);
	}

	// ==============================================================================

	public int getCoinsNumber() {
		Cursor cCoins = db.getCoinsCount();

		int coinsNumber = cCoins.getInt(cCoins.getColumnIndex("total_coins")) - cCoins.getInt(cCoins.getColumnIndex("used_coins"));
		return coinsNumber;
	}

	// ==============================================================================

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub

	}

	// ==============================================================================

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub

	}

	// =========================================================================================

	public void countUsingNumForRating() {

		e.putInt("usingNum", mSharedPreferences.getInt("usingNum", 0) + 1);
		e.commit();

		if (mSharedPreferences.getInt("usingNum", 0) >= 8) {

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					cd = new ConnectionDetector(GameActivity.this);
					if (cd.isConnectingToInternet()) {
						String msg = getResources().getString(R.string.rateDlg);
						dialog.showDialog(R.layout.blue_dialog, "rateDlg", msg, marketLink);
					}
				}
			}, 3000);

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

	// =========================================================================================

	public void rotateView(TextView view) {

//		RotateAnimation animation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		animation.setDuration(0);
//		animation.setFillAfter(true);
//		view.startAnimation(animation);
		
		view.setRotation(180.0f);
	}

	// =========================================================================================

	public void rotateSpacesGrids() {

//		RotateAnimation animation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//		animation.setDuration(0);
//		animation.setFillAfter(true);
//		spacesGrid1.startAnimation(animation);
//		spacesGrid2.startAnimation(animation);	
		
		spacesGrid1.setRotation(180.0f);
		spacesGrid2.setRotation(180.0f);	

	}

}
