package jcsla.korail;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends Activity
{
	private static final String TYPEFACE_NAME = "kopubDotum.ttf";
	private Typeface typeface = null;
	private ListView trainListView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		loadTypeface();
		setContentView(R.layout.activity_result);

		android.app.ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		setTitle("조회 결과");
		getActionBar().setDisplayShowHomeEnabled(false);

		TrainAdapter trainAdapter = new TrainAdapter(this, R.layout.row, TrainList.trainList);
		trainListView = (ListView) findViewById(R.id.trainListView);
		trainListView.setAdapter(trainAdapter);
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
}
