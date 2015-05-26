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
		Log.e("����������", "���������");
		interests = new ArrayList<InterestTrain>();
		addObserver(new InterestObserver(MainActivity.mainContext));
	}

	public void add(InterestTrain target)
	{
		Log.e("����������", "�������� �߰���� ���������� �����Ұ�");
		ObserverData observerData = new ObserverData(target, ObserverData.ADD);
		setChanged();
		notifyObservers(observerData);
		interests.add(target);
	}
	
	public void remove(InterestTrain target)
	{
		Log.e("����������", "�������� ������� ���������� �����Ұ�");
		ObserverData observerData = new ObserverData(target, ObserverData.REMOVE);
		setChanged();
		notifyObservers(observerData);
		interests.remove(target);
	}
	
	public void remove(int index)
	{
		Log.e("����������", "�������� ������� ���������� �����Ұ�");
		ObserverData observerData = new ObserverData(interests.get(index), ObserverData.REMOVE);
		setChanged();
		notifyObservers(observerData);
		interests.remove(index);
	}
		
	public boolean contains(Object object)
	{
		Log.e("����������", "���� ���� ��������!!!");
		Log.e("����������", "���� ���� ���Ѻ����ִ� �� : " + countObservers());
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
			Log.e("����������", "���������� �� �����͸� ������");
			trainData = data;
			typeData = type;
		}
	}

}