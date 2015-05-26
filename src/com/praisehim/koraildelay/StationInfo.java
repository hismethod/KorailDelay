package com.praisehim.koraildelay;

public class StationInfo
{
	private int code;
	private String name;
	private double longitude;
	private double latitude;
	private int group;
	private int major;

	// private boolean KTX;

	public StationInfo(int code, String name, double longitude,
			double latitude, int major, int group)
	{
		this.code = code;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		// this.KTX = KTX;
		this.group = group;
		this.major = major;
	}

	public int getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public double getLatitude()
	{
		return latitude;
	}

	// public boolean getKTX(){ return KTX; }
	public int getGroup()
	{
		return group;
	}

	public int getMajor()
	{
		return major;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	// public void setKTX(boolean KTX){ this.KTX = KTX; }
	public void setGroup(int group)
	{
		this.group = group;
	}

	public void setMajor(int major)
	{
		this.major = major;
	}
}
