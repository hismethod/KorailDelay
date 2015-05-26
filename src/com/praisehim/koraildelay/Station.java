package com.praisehim.koraildelay;

public class Station
{
	String name;
	int code;
	double longitude = 0;
	double latitude	 = 0;
	int major = 100;
	boolean isKTX = false;
	
	public Station(String name, int code, double lon, double lat, int major, boolean ktx)
	{
		this.name = name;
		this.code = code;
		this.longitude = lon;
		this.latitude  = lat;
		this.major = major;
		isKTX = ktx;
	}
}