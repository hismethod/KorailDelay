package com.praisehim.koraildelay.web;

import com.praisehim.koraildelay.DetailDialogFragment;

import android.os.AsyncTask;

public class DetailDialogCrawler extends AsyncTask<String, Integer, Void>
{
	String delayStatus;
	
	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... strData)
	{
		delayStatus = WebExtract.getDelayStatus(WebExtract.getDelayHTML(strData[0], strData[1]));
		
		return null;
	}

	@Override
	protected void onPostExecute(Void param)
	{
		DetailDialogFragment.trainStatusView.setText(delayStatus);
	}

	@Override
	protected void onCancelled()
	{
		super.onCancelled();
	}
}