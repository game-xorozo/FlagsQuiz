package com.nileworx.flagsquiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	static InputStream is = null;

	static String json = "";

	// constructor
	public JSONParser() {

	}

	// ==============================================================================
	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		// try {
		// defaultHttpClient
		// DefaultHttpClient httpClient = new DefaultHttpClient();
		// // HttpFeed httpFeed = new HttpFeed(url);
		//
		// HttpGet httpGet = new HttpGet(url);
		//
		// HttpResponse httpResponse = httpClient.execute(httpGet);
		// HttpEntity httpEntity = httpResponse.getEntity();
		// is = httpEntity.getContent();

		try {
			URL newURL = new URL(url);

			HttpURLConnection con = (HttpURLConnection) newURL.openConnection();
			is = con.getInputStream();

		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		// DefaultHttpClient httpClient = new DefaultHttpClient();
		// // HttpFeed httpFeed = new HttpFeed(url);
		//
		// HttpGet httpGet = new HttpGet(url);
		// HttpResponse httpResponse = null;
		// try {
		// httpResponse = httpClient.execute(httpGet);
		//
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		try {
			// HttpEntity httpEntity = httpResponse.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			json = sb.toString();

			// System.out.println(json);
			// System.out.println(httpEntity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		// try parse the string to a JSON object
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			return null;
		}

		// return JSON String
		return jObj;

	}
}
