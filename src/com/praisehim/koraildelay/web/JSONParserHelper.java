package com.praisehim.koraildelay.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class JSONParserHelper
{
	static JSONArray jsonArray = null;

	public static JSONArray getJSONFromURL(String url)
	{
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpPost = new HttpGet(url);
			Log.e("url", url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
			{
				Log.e("line", line);
				jsonArray = new JSONArray(line);
			}
			inputStream.close();
		}
		catch (ClientProtocolException e){e.printStackTrace();}
		catch (IOException e){e.printStackTrace();}
		catch (JSONException e){e.printStackTrace();}

		return jsonArray;
	}
}