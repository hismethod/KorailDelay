package com.praisehim.koraildelay;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class InterestTrain implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2983993473810516931L;
	private String date;
	private String time;
	private String depStation;
	private String arrStation;
	private String trainType;
	private String trainNo;
	private String arrTime;
	private int delayTime;
	private String estimateDepTime;
	private String estimateArrTime;
	private String remainTime;
	
	public InterestTrain(){}
	public InterestTrain(String date, String time, String arrTime, String depStation,
			String arrStation, String trainType, String trainNo)
	{
		this.date = date;
		this.time = time;
		this.arrTime = arrTime;
		this.depStation = depStation;
		this.arrStation = arrStation;
		this.trainType = trainType;
		this.trainNo = trainNo;
	}
	
	
	/** 첫 액티비티가 시작될때 반드시 호출되어야 함*/
	public static void loadInterstContext()
	{
		Gson gson = new Gson();
		PreferenceData data = new PreferenceData(MainActivity.mainContext);
		Type type = new TypeToken<InterestObservableArray>(){}.getType();
		
		String interestContext = data.getValue(PreferenceData.SAVED_USER_INTEREST, "null");
		
		if(Data.INTEREST_LIST == null)
			Data.INTEREST_LIST = new InterestObservableArray();
		
		Data.INTEREST_LIST = gson.fromJson(interestContext, type);
		Data.INTEREST_LIST.addObserver(MainActivity.observer);
	}
	
	/** 액티비티가 종료될때 반드시 호출되어야 함*/
	public static void saveInterestContext()
	{
		Gson gson = new Gson();
		PreferenceData data = new PreferenceData(MainActivity.mainContext);
		Data.INTEREST_LIST.deleteObservers();
		String interestContext = gson.toJson(Data.INTEREST_LIST);
		data.save(PreferenceData.SAVED_USER_INTEREST, interestContext);
		if(MainActivity.observer == null)
			MainActivity.observer = new InterestObserver(MainActivity.mainContext);
		Data.INTEREST_LIST.addObserver(MainActivity.observer);
	}
	
	public static boolean isExist(TrainInfo targetTrain)
	{
		if(Data.INTEREST_LIST == null)
		{
			Data.INTEREST_LIST = new InterestObservableArray();
			return false;
		}
		
		InterestTrain interest = new InterestTrain();
		
		interest.date = targetTrain.getDepDate();
		interest.trainNo = targetTrain.getTrainNo();
		
		return Data.INTEREST_LIST.contains(interest);
	}
	
	public static void addInterest(TrainInfo targetTrain)
	{
		InterestTrain interest = new InterestTrain();
		
		interest.date = targetTrain.getDepDate();
		interest.time = targetTrain.getDepTime();
		interest.arrTime = targetTrain.getArrTime();
		interest.depStation = targetTrain.getDepName();
		interest.arrStation = targetTrain.getArrName();
		interest.trainType = targetTrain.getType();
		interest.trainNo = targetTrain.getTrainNo();
		interest.delayTime = targetTrain.getDelayTime();
		interest.remainTime = targetTrain.getRemainTime();
		interest.estimateDepTime = targetTrain.getEstimateDepTime();
		interest.estimateArrTime = targetTrain.getEstimateArrTime();
		
		Data.INTEREST_LIST.add(interest);
	}
	
	public static void removeInterest(InterestTrain targetTrain)
	{
		Data.INTEREST_LIST.remove(targetTrain);
	}
	
	public static void removeInterest(TrainInfo targetTrain)
	{
		InterestTrain interest = new InterestTrain();
		
		interest.date = targetTrain.getDepDate();
		interest.time = targetTrain.getDepTime();
		interest.arrTime =targetTrain.getArrTime();
		interest.depStation = targetTrain.getDepName();
		interest.arrStation = targetTrain.getArrName();
		interest.trainType = targetTrain.getType();
		interest.trainNo = targetTrain.getTrainNo();
		interest.delayTime = targetTrain.getDelayTime();
		interest.remainTime = targetTrain.getRemainTime();
		interest.estimateDepTime = targetTrain.getEstimateDepTime();
		interest.estimateArrTime = targetTrain.getEstimateArrTime();
		
		Data.INTEREST_LIST.remove(interest);
	}
	
	public static boolean canInterest(TrainInfo candidateTrain)
	{
		String estimateTime = candidateTrain.getEstimateDepTime();
		Data.PresentDate now = Data.PresentDate.getInstance();
		
		int targetDate = Integer.parseInt(candidateTrain.getDepDate());
		int targetTime = Integer.parseInt(estimateTime.replace(":", ""));
		int nowDate = Integer.parseInt(now.eightDate);
		int nowTime = Integer.parseInt(now.fourTime);
		
		Log.e("canInterest", candidateTrain.getDepDate() + estimateTime + now.eightDate + now.fourTime);
		
		if(targetDate > nowDate)
			return true;
		else if(targetDate == nowDate && (targetTime > nowTime))
			return true;
		else
			return false;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		InterestTrain target = (InterestTrain) obj;
		
		if(!this.date.equals(target.date)) return false;
//		if(!this.time.equals(target.time)) return false;
//		if(!this.depStation.equals(target.depStation)) return false;
//		if(!this.arrStation.equals(target.arrStation)) return false;
//		if(!this.trainType.equals(target.trainType)) return false;
		if(!this.trainNo.equals(target.trainNo)) return false;
		
		return true;
	}
	
	public String getDate(){return date;}
	public String getRemainTime()
	{
		return remainTime;
	}
	public void setRemainTime(String remainTime)
	{
		this.remainTime = remainTime;
	}
	public String getTime(){return time;}
	public String getArrTime(){return arrTime;}
	public String getDepStation(){return depStation;}
	public String getArrStation(){return arrStation;}
	public String getTrainType(){return trainType;}
	public String getTrainNo(){return trainNo;}
	public int getDelayTime()
	{
		return delayTime;
	}
	public String getEstimateDepTime()
	{
		return estimateDepTime;
	}
	public String getEstimateArrTime()
	{
		return estimateArrTime;
	}
	public void setDelayTime(int delayTime)
	{
		this.delayTime = delayTime;
	}
	public void setEstimateDepTime(String estimateDepTime)
	{
		this.estimateDepTime = estimateDepTime;
	}
	public void setEstimateArrTime(String estimateArrTime)
	{
		this.estimateArrTime = estimateArrTime;
	}
	public void setDate(String date){this.date = date;}
	public void setTime(String time){this.time = time;}
	public void setDepStation(String depStation){this.depStation = depStation;}
	public void setArrStation(String arrStation){this.arrStation = arrStation;}
	public void setTrainType(String trainType){this.trainType = trainType;}
	public void setTrainNo(String trainNo){this.trainNo = trainNo;}
	public void setArrTime(String arrTime){this.arrTime = arrTime;}
}
