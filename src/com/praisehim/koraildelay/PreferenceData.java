package com.praisehim.koraildelay;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceData
{
	private final String PREF_NAME = "com.praisehim.koraildelay";

	public final static String CURRENT_TRANSACTION = "CURRENT_TRANSACTION";
	public final static String SAVED_USER_STATION = "SAVED_USER_STATION";
	public final static String SAVED_USER_INTEREST = "SAVED_USER_INTEREST";
	public final static String DEFAULT_DEP_STSTION = "DEFAULT_DEP_STSTION";
	public final static String DEFAULT_ARR_STSTION = "DEFAULT_ARR_STSTION";
	private Context mContext;

	public PreferenceData(){}
	public PreferenceData(Context c)
	{
		mContext = c;
	}

	public void save(String key, String value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putString(key, value);
		editor.commit();
	}

	public void save(String key, boolean value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean(key, value);
		editor.commit();
	}

	public void save(String key, int value)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putInt(key, value);
		editor.commit();
	}

	public String getValue(String key, String dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

		try
		{
			return pref.getString(key, dftValue);
		} catch (Exception e)
		{
			return dftValue;
		}

	}

	public int getValue(String key, int dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

		try
		{
			return pref.getInt(key, dftValue);
		} catch (Exception e)
		{
			return dftValue;
		}
	}

	public boolean getValue(String key, boolean dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);

		try
		{
			return pref.getBoolean(key, dftValue);
		} catch (Exception e)
		{
			return dftValue;
		}
	}
	
	public void removeValue(String key, boolean dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.remove(key);
		editor.commit();
	}
	
	public void removeValue(String key, String dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.remove(key);
		editor.commit();
	}
	
	public void removeValue(String key, int dftValue)
	{
		SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.remove(key);
		editor.commit();
	}
}
