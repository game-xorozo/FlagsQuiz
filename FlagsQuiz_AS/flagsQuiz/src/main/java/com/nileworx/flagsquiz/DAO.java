package com.nileworx.flagsquiz;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DAO {

	// All Static variables

	private SQLiteDatabase database;
	private DataBaseHandler dbHandler;

	private static final String TABLE_FLAGS = "flags";
	private static final String TABLE_SETTINGS = "settings";
	private static final String TABLE_COINS = "coins";
	private static final String TABLE_HELPS = "helps";

	// Flags Table Columns names

	private static final String FL_ID = "_flid";
	private static final String FL_IMAGE = "fl_image";
	private static final String FL_WIKIPEDIA = "fl_wikipedia";
	private static final String FL_COMPLETED = "fl_completed";
	private static final String FL_POINTS = "fl_points";
	private static final String FL_TRIES = "fl_tries";
	private static final String FL_LETTER = "fl_letter";
	private static final String FL_ORDER = "fl_order";
	private static final String FL_WEB_ID = "fl_web_id";
	private static final String FL_IMAGE_SDCARD = "fl_image_sdcard";

	// Hints Table Columns names

	private static final String COINS_ID = "_coid";
	private static final String TOTAL_COINS = "total_coins";
	private static final String USED_COINS = "used_coins";

	// Helps Table Columns names

	private static final String HE_FLAG = "he_flag";

	// ==============================================================================

	public DAO(Context context) {
		dbHandler = new DataBaseHandler(context);
		try {

			dbHandler.createDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		try {

			dbHandler.openDataBase();

		} catch (SQLException sqle) {

			throw sqle;

		}
		// Log.e("path2",
		// context.getDatabasePath("FootballFlagQuiz").toString());
	}

	// ==============================================================================

	// Getting One Flag
	public Cursor getOneFlag(String FlagID) {
		// Select All Query

		String query = "SELECT * FROM " + TABLE_FLAGS + " WHERE " + FL_ID + " = '" + FlagID + "'";
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor;

	}

	// ==============================================================================

	public void setFlagCompleted(String flID, int points) {
		open();
		ContentValues values = new ContentValues();
		values.put(FL_COMPLETED, "1");
		values.put(FL_POINTS, points);

		// Update Row
		database.update(TABLE_FLAGS, values, FL_ID + "=?", new String[] { flID });

	}

	// ==============================================================================

	public void setTries(String flID, int tries) {
		open();
		ContentValues values = new ContentValues();
		values.put(FL_TRIES, tries);

		// Update Row
		database.update(TABLE_FLAGS, values, FL_ID + "=?", new String[] { flID });
	}

	// ==============================================================================

	public int getNextFlag() {
		String query = "SELECT " + FL_WEB_ID + " FROM " + TABLE_FLAGS + " WHERE " + FL_COMPLETED + " = 0 " + "ORDER BY " + FL_WEB_ID + " ASC LIMIT 1";

		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			return cursor.getInt(cursor.getColumnIndex(FL_WEB_ID));
		} else {
			return 0;
		}
	}

	// ==============================================================================

	public Integer getNextFlag(String flID) {
		open();
		
		String query = "SELECT " + FL_ID + ", " + FL_WEB_ID + ", " + FL_IMAGE + ", " + FL_IMAGE_SDCARD + " FROM " + TABLE_FLAGS + " WHERE " + FL_COMPLETED + " = 0" + " ORDER BY " + FL_WEB_ID + " ASC LIMIT 1";
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();

		if (cursor.getCount() > 0) {
			return cursor.getInt(cursor.getColumnIndex(FL_WEB_ID));
		} else {
			return 0;
		}

	}

	// ==============================================================================

	public Cursor getStatsScore() {

		String query = "SELECT SUM(" + FL_POINTS + ") AS total_score FROM " + TABLE_FLAGS;
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor;

	}

	// ==============================================================================

	public Integer getFlagNumber() {

		String query = "SELECT COUNT(" + FL_ID + ") AS completed_number FROM " + TABLE_FLAGS + " WHERE " + FL_COMPLETED + " = 1";
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("completed_number")) + 1;

	}

	// ==============================================================================

	public Integer getMaxOrder() {

		String query = "SELECT MAX(" + FL_ORDER + ") AS max_order FROM " + TABLE_FLAGS;
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("max_order"));

	}

	// ==============================================================================

	public void resetGame() {
		open();

		ContentValues flagsValues = new ContentValues();
		flagsValues.put(FL_LETTER, "");
		flagsValues.put(FL_TRIES, 0);
		flagsValues.put(FL_POINTS, 0);
		flagsValues.put(FL_COMPLETED, "0");
		database.update(TABLE_FLAGS, flagsValues, null, null);

		ContentValues coinsValues = new ContentValues();
		coinsValues.put(TOTAL_COINS, 25);
		coinsValues.put(USED_COINS, 0);
		database.update(TABLE_COINS, coinsValues, null, null);

		String emptyQuery = "DELETE FROM " + TABLE_HELPS;
		database.execSQL(emptyQuery);

	}

	// ==============================================================================

	public void addTotalCoins(int coins) {
		open();

		String query = "UPDATE " + TABLE_COINS + " SET " + TOTAL_COINS + " = " + TOTAL_COINS + " + " + coins + " WHERE " + COINS_ID + " = 1";

		database.execSQL(query);

	}

	// ==============================================================================

	public void addUsedCoins(String coins) {
		open();

		String query = "UPDATE " + TABLE_COINS + " SET " + USED_COINS + " = " + USED_COINS + " + " + coins + " WHERE " + COINS_ID + " = 1";
		database.execSQL(query);

	}

	// ==============================================================================

	public void addLetterHelpPos(String flID, String pos) {
		open();
		ContentValues values = new ContentValues();
		values.put(FL_LETTER, pos);

		// Update Row
		database.update(TABLE_FLAGS, values, FL_ID + "=?", new String[] { flID });
	}

	// ==============================================================================

	public void updateHelpState(String flHelpID, String flHelpField) {
		open();

		ContentValues flagHintsValues = new ContentValues();
		flagHintsValues.put(flHelpField, 1);
		int result = database.update(TABLE_HELPS, flagHintsValues, HE_FLAG + "=?", new String[] { flHelpID });
		if (result == 0) {
			flagHintsValues.put(HE_FLAG, flHelpID);
			database.insert(TABLE_HELPS, null, flagHintsValues);
		}
	}

	// ==============================================================================

	public void setFlagPoints(String FlagID, int points) {
		open();
		ContentValues values = new ContentValues();
		values.put(FL_POINTS, points);

		// Update Row
		database.update(TABLE_FLAGS, values, FL_ID + "=?", new String[] { FlagID });

	}

	// ==============================================================================

	public Cursor getTotalScore() {
		open();

		String query = "SELECT SUM(" + FL_POINTS + ") AS total_score " + " FROM " + TABLE_FLAGS;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();

		return cursor;
	}

	// ==============================================================================

	public Cursor getCoinsCount() {
		open();

		String query = "SELECT * " + " FROM " + TABLE_COINS + " WHERE " + COINS_ID + " = 1";
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();

		return cursor;
	}

	// ==============================================================================

	public Cursor getHelpState(String flID) {
		open();

		String query = "SELECT *" + " FROM " + TABLE_HELPS + " WHERE " + HE_FLAG + " = " + flID;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();

		return cursor;
	}

	// ==============================================================================

	public int getLastFlag() {
		String query = "SELECT " + FL_WEB_ID + " FROM " + TABLE_FLAGS + " ORDER BY " + FL_WEB_ID + " DESC LIMIT 1";

		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex(FL_WEB_ID));

	}

	// ==============================================================================

	public String getFlagWikipedia(String flID) {
		String query = "SELECT " + FL_WIKIPEDIA + " FROM " + TABLE_FLAGS + " WHERE " + FL_ID + " = " + flID;

		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor.getString(cursor.getColumnIndex(FL_WIKIPEDIA));

	}

	// ==============================================================================

	public void addFlag(String fl_country, String fl_image, String fl_wikipedia, int fl_web_id) {
		open();
		ContentValues v = new ContentValues();
		v.put("fl_country", fl_country);
		v.put("fl_image", fl_image);
		v.put("fl_wikipedia", fl_wikipedia);
		v.put("fl_tries", 0);
		v.put("fl_score", 0);
		v.put("fl_points", 0);
		v.put("fl_completed", "0");
		v.put("fl_image_sdcard", 1);
		v.put("fl_order", getMaxOrder() + 1);
		v.put("fl_web_id", fl_web_id);

		database.insert("flags", null, v);

	}

	// ==============================================================================

	public Cursor getFlags() {

		String query = "SELECT * FROM " + TABLE_FLAGS + " ORDER BY  " + FL_ORDER + " ASC";
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
		return cursor;

	}

	//
	public void addFlags2(String fl_name, String fl_country, String fl_city, String fl_wikipedia, int fl_order, int fl_web_id) {
		open();
		ContentValues v = new ContentValues();
		v.put("fl_name", fl_name);
		v.put("fl_country", fl_country);
		v.put("fl_city", fl_city);
		if (fl_country != null && !fl_country.equals("")) {
			v.put("fl_is_country", 1);
		} else {
			v.put("fl_is_country", 0);
		}
		v.put("fl_image", "0" + String.valueOf(fl_order) + ".jpg");
		v.put("fl_wikipedia", fl_wikipedia);
		v.put("fl_tries", 0);
		v.put("fl_score", 0);
		v.put("fl_points", 0);
		v.put("fl_completed", "0");
		v.put("fl_image_sdcard", 0);
		v.put("fl_order", fl_order);
		v.put("fl_status", 1);
		v.put("fl_web_id", fl_web_id);

		database.insert("flags2", null, v);

	}

	//
	// public Cursor getFlags2() {
	//
	// String query = "SELECT * FROM " + TABLE_LOGOS + " ORDER BY  " + FL_LEVEL
	// + " ASC";
	// Cursor cursor = database.rawQuery(query, null);
	//
	// cursor.moveToFirst();
	// return cursor;
	//
	// }
	//
	// public void addFlags2(String fl_name, int fl_level, String fl_wikipedia,
	// String fl_info, String fl_player, int fl_id) {
	//
	// ContentValues v = new ContentValues();
	// v.put("fl_name", fl_name);
	// v.put("fl_image", String.valueOf(fl_id) + ".png");
	// v.put("fl_level", fl_level);
	// v.put("fl_wikipedia", fl_wikipedia);
	// v.put("fl_info", fl_info);
	// v.put("fl_player", fl_player);
	// v.put("fl_tries", 0);
	// v.put("fl_points", 0);
	// v.put("fl_completed", "0");
	// v.put("fl_image_sdcard", 0);
	// v.put("fl_order", fl_id);
	// v.put("fl_status", 1);
	// v.put("fl_web_id", fl_id);
	//
	// database.insert("flags_2", null, v);
	//
	// }

	// ==============================================================================

	public void open() throws SQLException {
		database = dbHandler.getWritableDatabase();

	}

	// ==============================================================================

	public void closeDatabase() {
		dbHandler.close();
	}

	// ==============================================================================

}
