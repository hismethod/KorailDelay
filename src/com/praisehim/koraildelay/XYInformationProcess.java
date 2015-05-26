package com.praisehim.koraildelay;

import java.util.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

public class XYInformationProcess
{
	MySQLiteOpenHelper helper;
	SQLiteDatabase db;

	public XYInformationProcess(Context context)
	{
		helper = new MySQLiteOpenHelper(context);
	}

	public ArrayList<StationInfo> getNearStationList(double longitude,
			double latitude)
	{
		ArrayList<StationInfo> si = getStationList();

		StationInfo[] temp = findNearStation(longitude, latitude, si);
		ArrayList<StationInfo> t = new ArrayList<StationInfo>();
		for (int i = 0; i < 5; i++)
			if (temp[i] != null)
				t.add(temp[i]);
		return t;
	}

	public ArrayList<StationInfo> getStationList()
	{
		ArrayList<StationInfo> si = new ArrayList<StationInfo>();
		db = helper.getReadableDatabase();
		Cursor c = db.query("station", null, null, null, null, null, "name");
		while (c.moveToNext())
		{
			StationInfo s = new StationInfo(c.getInt(c.getColumnIndex("code")),
					c.getString(c.getColumnIndex("name")), c.getDouble(c
							.getColumnIndex("longitude")), c.getDouble(c
							.getColumnIndex("latitude")), c.getInt(c
							.getColumnIndex("major")), c.getInt(c
							.getColumnIndex("group")));
			si.add(s);
		}
		return si;
	}

	private StationInfo[] findNearStation(double longitude, double latitude,
			ArrayList<StationInfo> si)
	{
		StationInfo[] NearStationList = new StationInfo[5];
		double[] distance = new double[5];

		for (int i = 0; i < 5; i++)
			distance[i] = 999999;

		for (int i = 0; i < si.size(); i++)
		{
			double d = Calc(longitude, latitude, si.get(i).getLongitude(), si
					.get(i).getLatitude());
			if (d <= 10)
			{
				for (int j = 0; j < 5; j++)
					if (d < distance[j])
					{
						NearStationList[j] = si.get(i);
						break;
					}
			}
		}
		return NearStationList;
	}

	private double Calc(double long1, double lat1, double long2, double lat2)
	{
		// http://blog.naver.com/musasin84/60189887329¿¡¼­ ÆÛ¿È
		double dDistance = 0;
		double dLat1lnRad = lat1 * (Math.PI / 180.0);
		double dLong1lnRad = long1 * (Math.PI / 180.0);
		double dLat2lnRad = lat2 * (Math.PI / 180.0);
		double dLong2lnRad = long2 * (Math.PI / 180.0);
		double dLongitude = dLong2lnRad - dLong1lnRad;
		double dLatitude = dLat2lnRad - dLat1lnRad;
		double a = Math.pow(Math.sin(dLatitude / 2.0), 2.0)
				+ Math.cos(dLat1lnRad) * Math.cos(dLat2lnRad)
				* Math.pow(Math.sin(dLongitude / 2.0), 2.0);
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
		double kEarth = 6376.5;
		dDistance = kEarth * c;
		return dDistance;
	}
}
