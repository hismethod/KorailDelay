package com.praisehim.koraildelay;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.praisehim.koraildelay.web.DetailDialogCrawler;
import com.praisehim.koraildelay.web.WebExtract;

public class SeatDialogFragment extends DialogFragment
{
	boolean isNull = true;
	public static TextView trainStatusView;
	ImageView trainIconView;
	TextView trainNameView;
	TextView trainNoView;
	TextView delayTimeView;
	TextView startStnView;
	TextView endStnView;
	TextView estimateStartView;
	TextView estimateEndView;
	TextView remainTimeView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{	
		return inflater.inflate(R.layout.detailinfo_dialog, container, false);	
	}

	@Override
	public void onStart()
	{
		Bundle bundle = getArguments();
		
		if(isNull)
		{
			trainIconView = (ImageView) getView().findViewById(R.id.trainIcon);
			trainNameView = (TextView) getView().findViewById(R.id.trainName);
			trainNoView = (TextView) getView().findViewById(R.id.trainNo);
			delayTimeView = (TextView) getView().findViewById(R.id.delayTime);
			startStnView = (TextView) getView().findViewById(R.id.startStn);
			endStnView = (TextView) getView().findViewById(R.id.endStn);
			estimateStartView = (TextView) getView().findViewById(R.id.estimateStartTime);
			estimateEndView = (TextView) getView().findViewById(R.id.estimateEndTime);
			remainTimeView = (TextView) getView().findViewById(R.id.remainTimeInfo);
			isNull = false;
		}
		
		TrainInfo clickedItem = (TrainInfo) bundle.getSerializable("clickedItem");
		setDetails(clickedItem);
		super.onStart();
	}

	public void setDetails(TrainInfo info)
	{
		trainStatusView = (TextView) getView().findViewById(R.id.nowStn);
		
		new DetailDialogCrawler().execute(info.getDepDate(), info.getTrainNo());
		String[] estimateDepInfo = WebExtract.calEstimateTime(info.getDepDate(), info.getDepTime(), info.getDelayTime());
		String[] estimateArrInfo = WebExtract.calEstimateTime(info.getArrDate(), info.getArrTime(), info.getDelayTime());
		String delayTime;
		
		int remain = 1;
		int delay = info.getDelayTime();
		if(delay == 0)
			delayTime = "정시운행";
		else
			delayTime = delay + "분 지연";
		
		if(!estimateDepInfo[1].equals(WebExtract.SHES_GONE))
		{
			try{
				remain = Integer.valueOf(estimateDepInfo[1].split("분")[0]);
			} catch(NumberFormatException e){
				remain = 100;
			}
			StringBuilder remainInfo = new StringBuilder(info.getArrName());
			remainInfo.append("역행 출발 "); remainInfo.append(estimateDepInfo[1]);
			remainInfo.append(" 남았습니다");
			estimateDepInfo[1] = remainInfo.toString();
		}
		
		if(remain > 15)
			remainTimeView.setTextColor((getResources().getColor(R.color.green)));
		else if(10 < remain && remain <= 15)
			remainTimeView.setTextColor((getResources().getColor(R.color.orange)));
		else
			remainTimeView.setTextColor((getResources().getColor(R.color.color_red)));
		
		String date = info.getDepDate();
		getDialog().setTitle(String.format("%s년 %s월 %s일 %s 열차정보", date.substring(0,4),
											date.substring(4,6), date.substring(6,8), info.getTrainNo()));
		trainIconView.setImageDrawable((getResources().getDrawable(info.getIcon())));
		trainNameView.setText(info.getType());
		trainNoView.setText(info.getTrainNo());
		delayTimeView.setText(delayTime);
		if(delay == 0)
		{
			delayTimeView.setTextColor((getResources().getColor(R.color.green)));
			estimateStartView.setTextColor((getResources().getColor(R.color.green)));
			estimateEndView.setTextColor((getResources().getColor(R.color.green)));
		}
		else
		{
			delayTimeView.setTextColor((getResources().getColor(R.color.orange)));
			estimateStartView.setTextColor((getResources().getColor(R.color.orange)));
			estimateEndView.setTextColor((getResources().getColor(R.color.orange)));
		}
		startStnView.setText(info.getDepName());
		estimateStartView.setText(estimateDepInfo[0]);
		endStnView.setText(info.getArrName());
		estimateEndView.setText(estimateArrInfo[0]);
		remainTimeView.setText(estimateDepInfo[1]);
	}
}