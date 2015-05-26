package com.praisehim.koraildelay.web;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import com.praisehim.koraildelay.Data;
import com.praisehim.koraildelay.DefaultSearchFragment;
import com.praisehim.koraildelay.MainActivity;
import com.praisehim.koraildelay.ResultActivity;
import com.praisehim.koraildelay.TrainInfo;

public class ServerCrawler extends AsyncTask<Void, Void, Void>
{
	String fullURL = null;
	boolean isNull = false;

	ArrayList<TrainInfo> trainList;
	
	public ServerCrawler(String depDate, String depTime, String depCode,
			String arrCode, String type, String serverURL)
	{
		StringBuilder fullURL = new StringBuilder(serverURL);
		fullURL.append("depDate=");
		fullURL.append(depDate);
		fullURL.append("&depTime=");
		fullURL.append(depTime);
		fullURL.append("&depCode=");
		fullURL.append(depCode);
		fullURL.append("&arrCode=");
		fullURL.append(arrCode);
		fullURL.append("&type=");
		fullURL.append(type);

		this.fullURL = fullURL.toString();
	}

	@Override
	protected Void doInBackground(Void... params)
	{
		trainList = new ArrayList<TrainInfo>();
		JSONParserHelper jsonParserHelper = new JSONParserHelper();
		JSONArray jsonArray = jsonParserHelper.getJSONFromURL(fullURL);
		if (jsonArray == null)
		{
			isNull = true;
			return null;
		}

		try
		{
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject train = jsonArray.getJSONObject(i);

				String arrName = train.getString("arrName");
				String arrTime = train.getString("arrTime");
				String delayTime = train.getString("delayTime");
				String depName = train.getString("depName");
				String depTime = train.getString("depTime");
				String spendTime = train.getString("spendTime");
				String status = train.getString("status");
				String trainNo = train.getString("trainNo");
				String type = train.getString("type");
				String way = train.getString("way");
				String depDate = train.getString("depDate");
				String depCode = train.getString("depCode");
				String arrDate = train.getString("arrDate");
				String arrCode = train.getString("arrCode");
				String ticketStatus = train.getString("ticketStatus");
				String ticketFreeStatus = train.getString("ticketFreeStatus");
				String estimateDepTime = train.getString("estimateDepTime");
				String estimateArrTime = train.getString("estimateArrTime");
				String remainTime = train.getString("remainTime");

				TrainInfo info = new TrainInfo();
				info.setArrName(arrName);
				info.setArrTime(arrTime);
				info.setDelayTime(delayTime);
				info.setDepName(depName);
				info.setDepTime(depTime);
				info.setSpendTime(spendTime);
				info.setStatus(status);
				info.setTrainNo(trainNo);
				info.setType(type);
				info.setWay(way);
				info.setDepDate(depDate);
				info.setDepCode(depCode);
				info.setArrDate(arrDate);
				info.setArrCode(arrCode);
				info.setTicketStatus(ticketStatus);
				info.setTicketFreeStatus(ticketFreeStatus);
				info.setEstimateDepTime(estimateDepTime);
				info.setEstimateArrTime(estimateArrTime);
				info.setRemainTime(remainTime);
				info.setIcon(type);
				
				trainList.add(info);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		Data.TRAINLIST = trainList;
		
		return null;
	}

	@Override
	protected void onPreExecute()
	{
		DefaultSearchFragment.progressBar.setVisibility(View.VISIBLE);

		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Void result)
	{
		DefaultSearchFragment.progressBar.setVisibility(View.GONE);
		
		Intent resultIntent = new Intent(MainActivity.mainContext, ResultActivity.class);

		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MainActivity.mainContext.startActivity(resultIntent);
	}
}
