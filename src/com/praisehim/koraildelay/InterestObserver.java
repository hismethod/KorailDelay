package com.praisehim.koraildelay;

import java.util.Observable;
import java.util.Observer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.praisehim.koraildelay.InterestObservableArray.ObserverData;
import com.praisehim.koraildelay.web.WebExtract;

public class InterestObserver implements Observer
{
	NotificationManager nm;
	NotificationCompat.Builder mCompatBuilder;
	PendingIntent pendingIntent;
	Context context;
	
	public InterestObserver(Context context)
	{
		Log.e("옵저버", "옵저버 생성!!!!!");
		this.context = context;
		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
	}
	
	private void removeUpdate(InterestTrain data)
	{
		Log.e("옵저버", "삭제 통지받는중!!!!!!");
		int no = Integer.valueOf(data.getTrainNo());
		mCompatBuilder = new NotificationCompat.Builder(context);
		mCompatBuilder.setTicker("등록해제알림");
		mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		mCompatBuilder.setAutoCancel(true);
		mCompatBuilder.setWhen(System.currentTimeMillis());
		mCompatBuilder.setContentText(String.format("%s >> %s %s %s출발열차 알림해제", data.getDepStation(), data.getArrStation(), data.getTrainType(), data.getTime()));
		mCompatBuilder.setContentTitle("알림이 해제되었습니다");
		mCompatBuilder.setSmallIcon(TrainInfo.getIconId(data.getTrainType()));
		
		nm.notify(no, mCompatBuilder.build());
	}
	
	private void addUpdate(InterestTrain data)
	{
		Log.e("옵저버", "추가 통지받는중!!!!!!");
		int no = Integer.valueOf(data.getTrainNo());
		String[] estimateDepInfo = WebExtract.calEstimateTime(data.getDate(), data.getTime(), data.getDelayTime());
		
		pendingIntent = PendingIntent.getActivity(context, no, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		mCompatBuilder = new NotificationCompat.Builder(context);
		mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
		mCompatBuilder.setContentIntent(pendingIntent);
		
		mCompatBuilder.setTicker("열차등록알림");
		mCompatBuilder.setWhen(System.currentTimeMillis());
		mCompatBuilder.setContentTitle(String.format("%d분지연, 도착예정 %s", data.getDelayTime(), estimateDepInfo[0]));
		mCompatBuilder.setContentText(String.format("%s >> %s %s %s출발열차", data.getDepStation(), data.getArrStation(), data.getTrainType(), data.getTime()));
		mCompatBuilder.setSmallIcon(TrainInfo.getIconId(data.getTrainType()));
		
		nm.notify(no, mCompatBuilder.build());
	}

	@Override
	public void update(Observable o, Object d)
	{
		Log.e("옵저버", "업데이트 통지받는중!!!!!!");
		if (o instanceof InterestObservableArray)
		{
			ObserverData data = (ObserverData) d;
			if(data.typeData == ObserverData.ADD)
			{
				addUpdate(data.trainData);
			}
			else if(data.typeData == ObserverData.REMOVE)
			{
				removeUpdate(data.trainData);
			}
		}
	}
}
