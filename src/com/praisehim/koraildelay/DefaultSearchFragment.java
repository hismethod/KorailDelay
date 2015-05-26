package com.praisehim.koraildelay;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;
import com.praisehim.koraildelay.web.CheckServer;
import com.praisehim.koraildelay.web.ClientCrawler;
import com.praisehim.koraildelay.web.ServerCrawler;

@SuppressLint("ValidFragment")
public class DefaultSearchFragment extends Fragment
{
	public static ProgressBar progressBar;
	public static String mainServerURL = "http://" + CheckServer.hostList[0] + ":8080/ServerTest/InfoCenter?";
	
	private Context context;
	private FragmentManager fm;
	private CalendarDatePickerDialog calendar;
	private RadialTimePickerDialog clock;
	private Calendar now;
	
	TextView trainTypeEdit;
	TextView depDateEdit;
	TextView depTimeEdit;
	TextView depStationEdit;
	TextView arrStationEdit;
	Button searchButton;
	
	LinearLayout trainTypeContainer;
	LinearLayout depDateContainer;
	LinearLayout depTimeContainer;
	LinearLayout depStationContainer;
	LinearLayout arrStationContainer;
	
	public static final String REQUEST_VALUE = "REQUEST_VALUE";
	public static final int START_STATION = 1;
	public static final int END_STATION = 2;

	static String arrStn, depStn;
	private String date, trainType, time,  todayDate;
	private int year, month, day, hour, minute;
	
	public DefaultSearchFragment(){}
	public DefaultSearchFragment(Context mContext)
	{
		context = mContext;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_search_default, null);
		fm = getFragmentManager();
		trainTypeContainer = (LinearLayout) view.findViewById(R.id.trainTypeContainer);
		depDateContainer = (LinearLayout) view.findViewById(R.id.depDateContainer);
		depTimeContainer = (LinearLayout) view.findViewById(R.id.depTimeContainer);
		depStationContainer = (LinearLayout) view.findViewById(R.id.depStationContainer);
		arrStationContainer = (LinearLayout) view.findViewById(R.id.arrStaionContainer);

		depDateEdit = (TextView) view.findViewById(R.id.editDepDate);
		depTimeEdit = (TextView) view.findViewById(R.id.editDepTime);
		depStationEdit = (TextView) view.findViewById(R.id.editDepStation);
		arrStationEdit = (TextView) view.findViewById(R.id.editArrStation);
		trainTypeEdit = (TextView) view.findViewById(R.id.editTrainType);
		searchButton = (Button) view.findViewById(R.id.searchButton);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.INVISIBLE);
		
		initDateValues();
		
		calendar = CalendarDatePickerDialog.newInstance(calendarListener, year, month-1, now.get(Calendar.DAY_OF_MONTH));
		calendar.setYearRange(year, year+1);
		clock = RadialTimePickerDialog.newInstance(clockListener, hour, 0, false);
		
		trainTypeEdit.setText("전체열차");
		depDateEdit.setText(date);
		depTimeEdit.setText(time);
		depStationEdit.setText(Data.getUserInputDep());
		arrStationEdit.setText(Data.getUserInputArr());

		trainTypeContainer.setOnClickListener(layoutClickEye);
		depDateContainer.setOnClickListener(layoutClickEye);
		depTimeContainer.setOnClickListener(layoutClickEye);
		depStationContainer.setOnClickListener(layoutClickEye);
		arrStationContainer.setOnClickListener(layoutClickEye);
		
		searchButton.setOnClickListener(SearchClickEye);
		
		return view;
	}
	
//	@Override
//	public void onPrepareOptionsMenu(Menu menu)
//	{
//		super.onPrepareOptionsMenu(menu);
//
//		// Show or hide action item
//		menu.findItem(R.id.action_search).setVisible(showActionItems);
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case 1:
			DefaultStationDialog popup = new DefaultStationDialog();
			popup.show(getFragmentManager(), "기본역설정");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		menu.add(0, 1, Menu.NONE, "기본역 설정");
	}

	
	private OnClickListener SearchClickEye = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Log.e("접속시도주소", mainServerURL);

			String queryDate = String.format("%d%02d%02d", year, month, day);
			String qeuryTime = String.format("%02d%02d00", hour, minute);
			String qeuryDep = depStationEdit.getText().toString();
			String qeuryArr = arrStationEdit.getText().toString();
			String trainType = "05";
			Data.setUserInputDate(queryDate);
			Data.setUserInputTime(qeuryTime);
			Data.setUserInputDep(qeuryDep);
			Data.setUserInputArr(qeuryArr);
			Data.setUserInputType(trainType);
			
			if(CheckServer.SERVER_IS_ALIVE == true)
				new ServerCrawler(queryDate, qeuryTime, qeuryDep, qeuryArr, trainType, mainServerURL).execute();
			else
				new ClientCrawler(false).execute(queryDate, qeuryTime, qeuryDep, qeuryArr, trainType);
		}
	};
	
	private void initDateValues()
	{
		now		= Calendar.getInstance();
		year	= now.get(Calendar.YEAR);
		month	= now.get(Calendar.MONTH)+1;
		day 	= now.get(Calendar.DAY_OF_MONTH);
		hour	= now.get(Calendar.HOUR_OF_DAY);
		minute	= 0;
		
		date	= String.format("%s년 %s월 %s일 %s",year,month,day,getWeekOfDay(0, 0, 0, true, now.get(Calendar.DAY_OF_WEEK)));
		time	= String.format("%s   %s시   %d분",now.get(Calendar.AM_PM) == 0? "오전" : "오후" , hour <= 12? hour : hour - 12, 0);
		todayDate = date;
		depStn = "동대구";
		arrStn = "구미";
		
	}
	
	private OnClickListener layoutClickEye = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.trainTypeContainer:
				depStationEdit.setText(Data.getUserInputDep());
				arrStationEdit.setText(Data.getUserInputArr());
				Log.e("역확인", Data.getUserInputDep()+Data.getUserInputArr());
				break;
				
			case R.id.depDateContainer:
		        calendar.show(fm, "날짜를 선택하세요");
				break;
				
			case R.id.depTimeContainer:
				clock.show(fm, "시간을 선택하세요");
				break;
				
			case R.id.depStationContainer:
				Intent intent = new Intent(getActivity().getApplicationContext(), StationSearchActivity.class);
				intent.putExtra("requestCode", 1);
				startActivityForResult(intent, 1);
		        Log.e("인텐트보내나?", "보냄");
				break;
				
			case R.id.arrStaionContainer:
				Intent intentArr = new Intent(getActivity().getApplicationContext(), StationSearchActivity.class);
				intentArr.putExtra("requestCode", 2);
				startActivityForResult(intentArr, 2);
		        Log.e("인텐트보내나?", "보냄");
				break;
			}
		}
	};
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.e("코드는?", requestCode + data.getStringExtra("STATION_NAME"));
		if(requestCode == 1)
		{
			depStn = data.getStringExtra("STATION_NAME");
			depStationEdit.setText(depStn);
		}
		else if(requestCode == 2)
		{
			arrStn = data.getStringExtra("STATION_NAME");
			arrStationEdit.setText(arrStn);
		}
	}
	
	private CalendarDatePickerDialog.OnDateSetListener calendarListener = new CalendarDatePickerDialog.OnDateSetListener()
	{
		@SuppressLint("DefaultLocale")
		@Override
		public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth)
		{
			int thisMonth = now.get(Calendar.MONTH);
			int thisDay = now.get(Calendar.DAY_OF_MONTH);
			if(monthOfYear < thisMonth || (monthOfYear == thisMonth && dayOfMonth < thisDay))
			{
				Log.e("날짜선택오류!", "오늘 : " + thisMonth + thisDay +
									"\n선택 : " + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth));
				
				time = String.format("%s   %d시   %d분",now.get(Calendar.AM_PM) == 0? "오전" : "오후"
												  , now.get(Calendar.HOUR), 0);
				depTimeEdit.setText(time);
				DefaultSearchFragment.this.hour = now.get(Calendar.HOUR_OF_DAY);
				DefaultSearchFragment.this.minute = 0;
				return;
			}
			
			String weekOfDay = getWeekOfDay(year, monthOfYear, dayOfMonth, false, 0);
			date = String.format("%d년 %d월 %d일 %s", year, monthOfYear+1, dayOfMonth, weekOfDay);
			depDateEdit.setText(date);
			
			DefaultSearchFragment.this.year = year;
			DefaultSearchFragment.this.month = monthOfYear+1;
			DefaultSearchFragment.this.day = dayOfMonth;
		}
	};
	
	private RadialTimePickerDialog.OnTimeSetListener clockListener = new RadialTimePickerDialog.OnTimeSetListener()
	{
		@Override
		public void onTimeSet(RadialTimePickerDialog dialog, int hourOfDay, int minute)
		{
			Log.e("시간선택", String.valueOf(hourOfDay) + String.valueOf(minute));
			if(date.equals(todayDate))
			{
				if(hourOfDay < now.get(Calendar.HOUR_OF_DAY) - 1)
					return;
			}
			
			String noon = "오전";
			if(hourOfDay >= 12)
				noon = "오후";
			
			time = String.format("%s   %d시   %d분", noon , hourOfDay <= 12? hourOfDay : hourOfDay-12, minute);
			depTimeEdit.setText(time);
			DefaultSearchFragment.this.hour = hourOfDay;
			DefaultSearchFragment.this.minute = minute;
		}
	};
	
	private String getWeekOfDay(int year, int month, int day, boolean directCal, int directValue)
	{
		int value;
		
		if(directCal)
			value = directValue;
		else
		{
			Calendar cal = Calendar.getInstance();
	
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DATE, day);
			
			value = cal.get(Calendar.DAY_OF_WEEK);
		}

		switch (value)
		{
		case 1:
			return "일요일";
		case 2:
			return "월요일";
		case 3:
			return "화요일";
		case 4:
			return "수요일";
		case 5:
			return "목요일";
		case 6:
			return "금요일";
		default:
			return "토요일";
		}
	}
}

class DefaultStationDialog extends DialogFragment
{
	TextView editDepStation;
	TextView editArrStation;
	Button buttonOK;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{	
		return inflater.inflate(R.layout.dialog_default_station, container, false);	
	}

	@Override
	public void onStart()
	{
		super.onStart();
		
		editDepStation = (TextView) getView().findViewById(R.id.editDefaultDepStation);
		editArrStation = (TextView) getView().findViewById(R.id.editDefaultArrStation);
		buttonOK = (Button) getView().findViewById(R.id.buttonDefaultStationOK);
		setDetails();
	}

	public void setDetails()
	{
		buttonOK.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String depStation = editDepStation.getText().toString();
				String arrStation = editArrStation.getText().toString();
				
				PreferenceData data = new PreferenceData(MainActivity.mainContext);
				data.save(PreferenceData.DEFAULT_DEP_STSTION, depStation);
				data.save(PreferenceData.DEFAULT_ARR_STSTION, arrStation);
				DefaultStationDialog.this.dismiss();
			}
		});
	}
}




