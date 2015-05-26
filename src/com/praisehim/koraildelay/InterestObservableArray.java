package com.praisehim.koraildelay;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class InterestObservableArray extends Observable
{
	ArrayList<InterestTrain> interests;
	
	public InterestObservableArray()
	{	
		Log.e("옵저버블어레이", "나생성됬어");
		interests = new ArrayList<InterestTrain>();
		addObserver(new InterestObserver(MainActivity.mainContext));
	}

	public void add(InterestTrain target)
	{
		Log.e("옵저버블어레이", "나아이템 추가됬어 옵저버에게 통지할게");
		ObserverData observerData = new ObserverData(target, ObserverData.ADD);
		setChanged();
		notifyObservers(observerData);
		interests.add(target);
	}
	
	public void remove(InterestTrain target)
	{
		Log.e("옵저버블어레이", "나아이템 삭제됬어 옵저버에게 통지할게");
		ObserverData observerData = new ObserverData(target, ObserverData.REMOVE);
		setChanged();
		notifyObservers(observerData);
		interests.remove(target);
	}
	
	public void remove(int index)
	{
		Log.e("옵저버블어레이", "나아이템 삭제됬어 옵저버에게 통지할게");
		ObserverData observerData = new ObserverData(interests.get(index), ObserverData.REMOVE);
		setChanged();
		notifyObservers(observerData);
		interests.remove(index);
	}
		
	public boolean contains(Object object)
	{
		Log.e("옵저버블어레이", "누가 나를 뒤져본다!!!");
		Log.e("옵저버블어레이", "현재 나를 지켜보고있는 자 : " + countObservers());
		return interests.contains(object);
	}
		
	public InterestTrain get(int index)
	{
		return interests.get(index);
	}
	
	public int size()
	{
		return interests.size();
	}
		
	public String toString()
	{
		return interests.toString();
	}
	
	class ObserverData
	{
		public static final int ADD = 1;
		public static final int REMOVE = 2;
		InterestTrain trainData;
		int typeData;
		
		ObserverData(InterestTrain data, int type)
		{
			Log.e("옵저버블어레이", "옵저버에게 줄 데이터를 생성중");
			trainData = data;
			typeData = type;
		}
	}

}