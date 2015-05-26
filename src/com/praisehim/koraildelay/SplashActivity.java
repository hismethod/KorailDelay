package com.praisehim.koraildelay;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.praisehim.koraildelay.web.CheckServer;

public class SplashActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//new Initializer().execute();
		initialize();
	}
	
	private class Initializer extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			new CheckServer().execute(); //서버 체크
			MainActivity.observer = new InterestObserver(MainActivity.mainContext);
			XYInformationProcess db = new XYInformationProcess(MainActivity.mainContext);
			PreferenceData pref = new PreferenceData(MainActivity.mainContext);
			Data.INTEREST_LIST = new InterestObservableArray();
			
			Log.i("리스트생성완료!", Data.INTEREST_LIST.toString());
			InterestTrain.loadInterstContext();
			Data.setUserInputDep(pref.getValue(PreferenceData.DEFAULT_DEP_STSTION, "구미"));
			Data.setUserInputArr(pref.getValue(PreferenceData.DEFAULT_ARR_STSTION, "동대구"));	
			Data.STATIONLIST = db.getStationList();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void param)
		{
			try
			{
				Thread.sleep(3000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finish(); // 액티비티 종료
		}
		
	}

	private void initialize()
	{
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				Log.i("핸들러", "작동!!!!");
				
				
				new CheckServer().execute(); //서버 체크
				MainActivity.observer = new InterestObserver(MainActivity.mainContext);
				XYInformationProcess db = new XYInformationProcess(MainActivity.mainContext);
				PreferenceData pref = new PreferenceData(MainActivity.mainContext);
				Data.INTEREST_LIST = new InterestObservableArray();
				
				Log.i("리스트생성완료!", Data.INTEREST_LIST.toString());
				InterestTrain.loadInterstContext();
				Data.setUserInputDep(pref.getValue(PreferenceData.DEFAULT_DEP_STSTION, "구미"));
				Data.setUserInputArr(pref.getValue(PreferenceData.DEFAULT_ARR_STSTION, "동대구"));	
				Data.STATIONLIST = db.getStationList();
				finish();
			}
		};

		handler.sendEmptyMessageDelayed(0, 3000); // ms, 3초후 종료시킴
	}
}
