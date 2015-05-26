package com.praisehim.koraildelay.web;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.praisehim.koraildelay.Data;
import com.praisehim.koraildelay.DefaultSearchFragment;
import com.praisehim.koraildelay.MainActivity;
import com.praisehim.koraildelay.ResultActivity;

public class ClientCrawler extends AsyncTask<String, Integer, Void>
{
	private boolean isRefresh = false;
	
	public ClientCrawler(boolean isRefresh)
	{
		this.isRefresh = isRefresh;
	}
	
	@Override
	protected void onPreExecute()
	{
		DefaultSearchFragment.progressBar.setVisibility(View.VISIBLE);
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... strData)
	{
		Data.TRAINLIST = WebExtract.searchTrainInfo(strData[0],
					strData[1], strData[2], strData[3], strData[4]);
		publishProgress(50);
		
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... progress)
	{
		DefaultSearchFragment.progressBar.setProgress(progress[0]);
	}

	@Override
	protected void onPostExecute(Void param)
	{
		DefaultSearchFragment.progressBar.setVisibility(View.GONE);
		
		if(Data.TRAINLIST == null)
		{
			Toast.makeText(MainActivity.mainContext, "조회결과가 없습니다.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(isRefresh)
		{
			return;
		}

		Intent resultIntent = new Intent(MainActivity.mainContext, ResultActivity.class);

		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MainActivity.mainContext.startActivity(resultIntent);
	}

	@Override
	protected void onCancelled()
	{
		DefaultSearchFragment.progressBar.setVisibility(View.GONE);
		super.onCancelled();
	}
}