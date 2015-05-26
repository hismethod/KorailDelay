package com.praisehim.koraildelay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends FragmentActivity
{
	public static Context mainContext;
	public static boolean serverIsAlive = true;
	public static InterestObserver observer;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		startActivity(new Intent(this, SplashActivity.class));
		setContentView(R.layout.activity_main);
		mainContext = getApplicationContext();
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(mainContext, getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
////		getMenuInflater().inflate(R.menu.main, menu);		
//		menu.add(0, 1, Menu.NONE, "기본역 설정");
//		menu.add(0, 2, Menu.NONE, "지연시간 적용하여 표기");
//		return true;
//	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		String stationName = data.getStringExtra("STATION_NAME");
//		mSectionsPagerAdapter.searchFragment.onActivityResult(requestCode, resultCode, data);
//		Log.e("결과통지받음", requestCode+stationName);
////		if(requestCode == 1)
////			DefaultSearchFragment.depStationEdit.setText(stationName);
////		else if(requestCode == 2)
////			DefaultSearchFragment.arrStationEdit.setText(stationName);
//	}
}