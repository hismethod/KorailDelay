package com.praisehim.koraildelay.web;

import java.net.InetAddress;

import android.os.AsyncTask;
import android.util.Log;

public class CheckServer extends AsyncTask<Void, Void, Void>
{
	public static String[] hostList = {"183.106.251.203"};
	public static String[] serverList = {"http://" + hostList[0] + ":8080/ServerTest/InfoCenter?"};
	public static boolean SERVER_IS_ALIVE = false;
	public static final int DOING = 50;
	public static final int DONE = 100;
	public static int STATUS = 0;
	public static final String SERVER_DEAD = "서버죽음ㅡㅠ";

	public void isAlive(String host)
	{
		try
		{
			Log.e("url", host);
			InetAddress pingcheck = InetAddress.getByName(host);
			SERVER_IS_ALIVE = pingcheck.isReachable(3000);
			Log.e("서버체크", String.valueOf(SERVER_IS_ALIVE));
		}
		catch (Exception e){}
		
	}
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		STATUS = DOING;
//		publishProgress("DOING");
		
		isAlive(hostList[0]);
		return null;
		
//		for(int i=0; i < hostList.length; i++)
//		{
//			if(isAlive(hostList[i]))
//			{
//				publishProgress(serverList[i]);
//				return serverList[i];
//			}
//		}
//		
//		publishProgress(SERVER_DEAD);
//		return SERVER_DEAD;
	}

	@Override
	protected void onProgressUpdate(Void... serverURL)
	{
//		SearchFragment.text.setText(serverURL[0]);
	}

	@Override
	protected void onPostExecute(Void serverURL)
	{
//		DefaultSearchFragment.mainServerURL = serverURL;
//		SearchFragment.text.setText(serverURL);
		STATUS = DONE;
	}

	@Override
	protected void onCancelled()
	{
		super.onCancelled();
	}
}