package com.praisehim.koraildelay;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.praisehim.koraildelay.SwipeLibrary.BaseSwipeListViewListener;
import com.praisehim.koraildelay.SwipeLibrary.SwipeListView;
import com.praisehim.koraildelay.SwipeLibrary.SwipeRefreshLayout;
import com.praisehim.koraildelay.web.CheckServer;
import com.praisehim.koraildelay.web.JSONParserHelper;
import com.praisehim.koraildelay.web.WebExtract;

public class ResultActivity extends Activity
{
	SwipeRefreshLayout refresher;
	SwipeListView trainListView;
	TrainAdapter trainAdapter;
	static int option = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		InterestTrain.loadInterstContext();
		trainListView = (SwipeListView) findViewById(R.id.trainListView);
		refresher = (SwipeRefreshLayout) findViewById(R.id.refresher);
		
		trainAdapter = new TrainAdapter(getApplicationContext(), Data.TRAINLIST, trainListView);
		trainListView.setAdapter(trainAdapter);
		trainListView.setAnimationTime(100);
		trainListView.setSwipeListViewListener(new ItemSwipeListener());

		refresher.setOnRefreshListener(new OnRefreshListner());
		refresher.setColorScheme(android.R.color.holo_blue_bright, R.color.green, 
	            R.color.orange, android.R.color.holo_red_light);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
//		getMenuInflater().inflate(R.menu.main, menu);		
		menu.add(0, 1, Menu.NONE, "기본시간으로 표기");
		menu.add(0, 2, Menu.NONE, "지연시간 적용하여 표기");
		return true;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Log.e("리절트 온포즈 호출!!!", "상태저장");
        InterestTrain.saveInterestContext();
	}
	
	@Override
	protected void onResume()
	{
		super.onRestart();
		InterestTrain.loadInterstContext();
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
        {
        case 2:
            Toast.makeText(ResultActivity.this, "지연시간 적용하여 표기", Toast.LENGTH_SHORT).show();
            option = 2;
            updateList();
            break;
            
        case 1:
        	Toast.makeText(ResultActivity.this, "기본시간으로 표기", Toast.LENGTH_SHORT).show();
        	option = 1;
        	updateList();
            break;
     
        default:
            break;
        }
 
        return super.onOptionsItemSelected(item);
    }

	private class ItemSwipeListener extends BaseSwipeListViewListener
	{
		@Override
		public void onClickFrontView(int position)
		{
			TrainInfo clickedItem = Data.TRAINLIST.get(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("clickedItem", clickedItem);
			
			DetailDialogFragment popup = new DetailDialogFragment();
			popup.setArguments(bundle);
			popup.show(getFragmentManager(), "열차상세정보");
		}
		@Override
		public void onClickBackView(int position)
		{
			trainListView.closeAnimate(position);//when you touch back view it will close
		}
		
		@Override
		public void onLongClickFrontView(int position)
		{
			Log.i("롱클릭", "잡았다!");
		}
	}

	
	private class OnRefreshListner implements SwipeRefreshLayout.OnRefreshListener
	{
		@Override
		public void onRefresh()
		{
			if(CheckServer.SERVER_IS_ALIVE)
				new RefreshTaskerForServer().execute();
			else if (!CheckServer.SERVER_IS_ALIVE)
				new RefreshTasker().execute();
		}
	}

	private class RefreshTaskerForServer extends AsyncTask<Void, Void, Void>
	{
		String fullURL = null;
		boolean isNull = false;

		ArrayList<TrainInfo> trainList;
		
		public RefreshTaskerForServer()
		{

			StringBuilder fullURL = new StringBuilder(DefaultSearchFragment.mainServerURL);
			fullURL.append("depDate=");
			fullURL.append(Data.getUserInputDate());
			fullURL.append("&depTime=");
			fullURL.append(Data.getUserInputTime());
			fullURL.append("&depCode=");
			fullURL.append(Data.getUserInputDep());
			fullURL.append("&arrCode=");
			fullURL.append(Data.getUserInputArr());
			fullURL.append("&type=");
			fullURL.append(Data.getUserInputType());

			this.fullURL = fullURL.toString();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			trainList = new ArrayList<TrainInfo>();
			JSONParserHelper jsonParserHelper = new JSONParserHelper();
			JSONArray jsonArray = jsonParserHelper.getJSONFromURL(fullURL);
			if (jsonArray == null)
			{
				isNull = true;
				return null;
			}

			try
			{
				for (int i = 0; i < jsonArray.length(); i++)
				{
					JSONObject train = jsonArray.getJSONObject(i);

					String arrName = train.getString("arrName");
					String arrTime = train.getString("arrTime");
					String delayTime = train.getString("delayTime");
					String depName = train.getString("depName");
					String depTime = train.getString("depTime");
					String spendTime = train.getString("spendTime");
					String status = train.getString("status");
					String trainNo = train.getString("trainNo");
					String type = train.getString("type");
					String way = train.getString("way");
					String depDate = train.getString("depDate");
					String depCode = train.getString("depCode");
					String arrDate = train.getString("arrDate");
					String arrCode = train.getString("arrCode");
					String ticketStatus = train.getString("ticketStatus");
					String ticketFreeStatus = train.getString("ticketFreeStatus");
					String estimateDepTime = train.getString("estimateDepTime");
					String estimateArrTime = train.getString("estimateArrTime");
					String remainTime = train.getString("remainTime");

					TrainInfo info = new TrainInfo();
					info.setArrName(arrName);
					info.setArrTime(arrTime);
					info.setDelayTime(delayTime);
					info.setDepName(depName);
					info.setDepTime(depTime);
					info.setSpendTime(spendTime);
					info.setStatus(status);
					info.setTrainNo(trainNo);
					info.setType(type);
					info.setWay(way);
					info.setDepDate(depDate);
					info.setDepCode(depCode);
					info.setArrDate(arrDate);
					info.setArrCode(arrCode);
					info.setTicketStatus(ticketStatus);
					info.setTicketFreeStatus(ticketFreeStatus);
					info.setEstimateDepTime(estimateDepTime);
					info.setEstimateArrTime(estimateArrTime);
					info.setRemainTime(remainTime);
					info.setIcon(type);
					
					trainList.add(info);
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}

			Data.TRAINLIST = trainList;
			
			return null;
		}


		@Override
		protected void onPostExecute(Void result)
		{
			if(Data.TRAINLIST == null)
			{
				Toast.makeText(getApplicationContext(), "조회결과가 없습니다", Toast.LENGTH_LONG).show();
				return;
			}
			
			updateList();
			
			refresher.setRefreshing(false);
			Toast.makeText(getApplicationContext(), "정보를 업데이트 하였습니다", Toast.LENGTH_SHORT).show();
		}
	}

	
	private class RefreshTasker extends AsyncTask<Void, Void, Void>
	{	
		@Override
		protected Void doInBackground(Void... params)
		{
			Data.TRAINLIST = WebExtract.searchTrainInfo(Data.getUserInputDate(),
								Data.getUserInputTime(), Data.getUserInputDep(),
								Data.getUserInputArr(), Data.getUserInputType());
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void param)
		{
			DefaultSearchFragment.progressBar.setVisibility(View.GONE);
			
			if(Data.TRAINLIST == null)
			{
				Toast.makeText(getApplicationContext(), "조회결과가 없습니다", Toast.LENGTH_LONG).show();
				return;
			}
			
			updateList();
			
			refresher.setRefreshing(false);
			Toast.makeText(getApplicationContext(), "정보를 업데이트 하였습니다", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void updateList()
	{
		trainAdapter.setData(Data.TRAINLIST);
		trainAdapter.notifyDataSetChanged();
		trainListView.setAdapter(trainAdapter);
	}
}


/////////////////////////////////////////////////////////////
//
//private class TrainItemClickListner implements OnItemClickListener
//{
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//	{
//		TrainInfo clickedItem = Data.TRAINLIST.get(position);
//		Bundle bundle = new Bundle();
//		bundle.putSerializable("clickedItem", clickedItem);
//		
//		DetailDialogFragment popup = new DetailDialogFragment();
//		popup.setArguments(bundle);
//		popup.show(getFragmentManager(), "열차상세정보");
//	}
//}
//
//private class OnSwipeCallback implements DismissSwipeListViewTouchListener.OnSwipeCallback
//{
//	@Override
//	public void onSwipeLeft(ListView listView, int[] reverseSortedPositions)
//	{
//		TrainInfo selectedTrain = (TrainInfo) listView.getItemAtPosition(reverseSortedPositions[0]);
//		Log.i("왼쪽어디누름?", selectedTrain.toString());
//
//	}
//
//	@Override
//	public void onSwipeRight(ListView listView, int[] reverseSortedPositions)
//	{
//		Log.i("오른쪽어디누름?", listView.getItemAtPosition(reverseSortedPositions[0]).toString());
//
//	}
//}

