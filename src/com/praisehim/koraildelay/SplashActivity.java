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
			new CheckServer().execute(); //���� üũ
			MainActivity.observer = new InterestObserver(MainActivity.mainContext);
			XYInformationProcess db = new XYInformationProcess(MainActivity.mainContext);
			PreferenceData pref = new PreferenceData(MainActivity.mainContext);
			Data.INTEREST_LIST = new InterestObservableArray();
			
			Log.i("����Ʈ�����Ϸ�!", Data.INTEREST_LIST.toString());
			InterestTrain.loadInterstContext();
			Data.setUserInputDep(pref.getValue(PreferenceData.DEFAULT_DEP_STSTION, "����"));
			Data.setUserInputArr(pref.getValue(PreferenceData.DEFAULT_ARR_STSTION, "���뱸"));	
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
			finish(); // ��Ƽ��Ƽ ����
		}
		
	}

	private void initialize()
	{
		Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				Log.i("�ڵ鷯", "�۵�!!!!");
				
				
				new CheckServer().execute(); //���� üũ
				MainActivity.observer = new InterestObserver(MainActivity.mainContext);
				XYInformationProcess db = new XYInformationProcess(MainActivity.mainContext);
				PreferenceData pref = new PreferenceData(MainActivity.mainContext);
				Data.INTEREST_LIST = new InterestObservableArray();
				
				Log.i("����Ʈ�����Ϸ�!", Data.INTEREST_LIST.toString());
				InterestTrain.loadInterstContext();
				Data.setUserInputDep(pref.getValue(PreferenceData.DEFAULT_DEP_STSTION, "����"));
				Data.setUserInputArr(pref.getValue(PreferenceData.DEFAULT_ARR_STSTION, "���뱸"));	
				Data.STATIONLIST = db.getStationList();
				finish();
			}
		};

		handler.sendEmptyMessageDelayed(0, 3000); // ms, 3���� �����Ŵ
	}
}
