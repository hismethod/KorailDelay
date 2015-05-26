package jcsla.korail;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class StationSearchActivity extends ActionBarActivity
{
	private static final String TYPEFACE_NAME = "kopubDotum.ttf";
	private Typeface typeface = null;
	private ArrayList<String> stationList;
	private ArrayAdapter<String> stationListAdapter;
	SearchView mSearchView;
	Bundle b;
	Intent i;
	
	private String title;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		loadTypeface();
		setContentView(R.layout.activity_station_search);
		
		// 타이틀 custom :
		// http://stackoverflow.com/questions/8607707/how-to-set-a-custom-font-in-the-actionbar-title
		
		title = getIntent().getStringExtra("title");
		//인텐트에서 title이라는 이름으로 액션바 이름넘겨받음.
		
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		setTitle(title);
		getActionBar().setDisplayShowHomeEnabled(false);

		stationList = new ArrayList<String>();
		Iterator<String> i = Stations.t_name_number_stations.keySet().iterator();
		while (i.hasNext())
		{
			String key = (String) i.next();
			stationList.add(key);
		}
		stationListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stationList);
		ListView stationListView = (ListView) findViewById(R.id.station_list);
		stationListView.setAdapter(stationListAdapter);
		stationListView.setOnItemClickListener(mItemClickListener);
	}

	private void loadTypeface()
	{
		if (typeface == null)
			typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);
	}

	@Override
	public void setContentView(int viewId)
	{
		View view = LayoutInflater.from(this).inflate(viewId, null);
		ViewGroup group = (ViewGroup) view;
		int childCnt = group.getChildCount();
		for (int i = 0; i < childCnt; i++)
		{
			View v = group.getChildAt(i);
			if (v instanceof TextView)
			{
				((TextView) v).setTypeface(typeface);
			}
		}
		super.setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		mSearchView.setQueryHint(title);
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{

			@Override
			public boolean onQueryTextSubmit(String arg0)
			{
				return false;
			}
			
			// 자음 검색
			@Override
			public boolean onQueryTextChange(String arg0)
			{
				stationListAdapter.getFilter().filter(arg0);
				return false;
			}
		});

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		if (id == R.id.action_search)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			b = new Bundle();
			i = new Intent();

			b.putString("station", ((TextView) view).getText().toString());
			i.putExtras(b);
			setResult(RESULT_OK, i);
			finish();
		}
	};
}
