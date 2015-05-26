package com.praisehim.koraildelay;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class StationSearchActivity extends Activity implements OnItemClickListener
{
	private ImageButton searchButton;
	private ListView optionListView;
	private ListView itemListView;
	private EditText searchView;
	private FilterableAdapter searchAdapter;
	int request;
	public static final String REQUEST_VALUE = "REQUEST_VALUE";
	public static final String START_STATION = "START_STATION";
	public static final String END_STATION = "END_STATION";
	XYInformationProcess xyip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_station_main);
		
		request = getIntent().getIntExtra("requestCode", 0);
		Log.e("리퀘스트", request +"");
		
		//모든 역 가져옴
		//ArrayList<StationInfo> si = new ArrayList<StationInfo>();
		//xyip = new XYInformationProcess(this);
		//si = xyip.getNearStationList(127, 36);
		//si = xyip.getStationList();
		
		searchView = (EditText) findViewById(R.id.editText);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		itemListView = (ListView) findViewById(R.id.itemListView);
		
		//totalItems.addAll(ContactList.getContacts(this));
		
		searchAdapter = new FilterableAdapter(this, Data.STATIONLIST);
		
		//자주가는 역 가져오기
		//Set<String> fs = null; 
		//pref = getSharedPreferences("FavoriteStation", 0);
		//fs = pref.getStringSet("Favorite", fs);
		
		itemListView.setAdapter(searchAdapter);
		itemListView.setTextFilterEnabled(true);
		itemListView.setOnItemClickListener(this);

 
		searchView.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable query)
			{
				Log.d("텍스트변경후", query.toString());
				searchAdapter.getFilter().filter(query.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence query, int start, int before, int count){}
			@Override
			public void onTextChanged(CharSequence query, int start, int before, int count){}
		});
		
		searchButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}
	
	private void callFinish(String stationName)
	{
		
		Intent intent = getIntent();
		intent.putExtra("STATION_NAME", stationName);
		
		setResult(RESULT_OK, intent);
		Log.e("리퀘스트함수", "들어오나?");
		if(request == 1)
		{
			Log.e("출발역설정", stationName);
			Data.setUserInputDep(stationName);
		}
		else if(request == 2)
		{
			Log.e("도착역설정", stationName);
			Data.setUserInputArr(stationName);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me)
	{
		optionListView.setVisibility(View.GONE);
		
	  	return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
	{
		StationInfo item = (StationInfo) adapterView.getItemAtPosition(position);
		Log.e("역이름", item.getName());
		callFinish(item.getName());
		finish();
		//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/" + item.getItemID()));
		//startActivity(browserIntent);
	}
}