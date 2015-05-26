package com.praisehim.koraildelay;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.praisehim.koraildelay.SwipeLibrary.DismissSwipeListViewTouchListener;
import com.praisehim.koraildelay.SwipeLibrary.SwipeRefreshLayout;
import com.praisehim.koraildelay.web.WebExtract;

@SuppressLint("ValidFragment")
public class UsersaveFragment extends Fragment
{
	private Context context;
	private ListView userTrainListView;
	private InterestTrainAdapter interestTrainAdapter;
	
	@SuppressLint("ValidFragment")
	public UsersaveFragment(Context mContext)
	{
		context = mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_usersaved, null);
		userTrainListView = (ListView) view.findViewById(R.id.interestTrainListView);
		
		if(Data.INTEREST_LIST != null)
			Log.i("있나없나", Data.INTEREST_LIST.size() + "");
		else
			Data.INTEREST_LIST = new InterestObservableArray();
		
		interestTrainAdapter = new InterestTrainAdapter(context, Data.INTEREST_LIST, userTrainListView);
		Log.i("유저 프래그먼트 생성자!!!", "냉무");
		userTrainListView.setAdapter(interestTrainAdapter);
		userTrainListView.setOnItemClickListener(new TrainItemClickListner());

//		DismissSwipeListViewTouchListener touchListener = new DismissSwipeListViewTouchListener(
//				userTrainListView, new OnSwipeCallback(), true, true);
//		userTrainListView.setOnTouchListener(touchListener);
//		userTrainListView.setOnScrollListener(touchListener.makeScrollListener());
		
		return view;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		InterestTrain.saveInterestContext();
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		InterestTrain.saveInterestContext();
	}
	
	public void onResume()
	{
		super.onResume();
		InterestTrain.loadInterstContext();
		updateList();
	}

	private void updateList()
	{
		interestTrainAdapter.setData(Data.INTEREST_LIST);
		interestTrainAdapter.notifyDataSetChanged();
		userTrainListView.setAdapter(interestTrainAdapter);
	}

	
	private class TrainItemClickListner implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			InterestTrain clickedItem = Data.INTEREST_LIST.get(position);

			new InterestTrainCrawler(clickedItem).execute();
		}
	}
	
	private class InterestTrainCrawler extends AsyncTask<Void, Void, TrainInfo>
	{
		ProgressDialog progress;
		InterestTrain userTrain;
		
		public InterestTrainCrawler(InterestTrain userTrain)
		{
			this.userTrain = userTrain;
			
		}

		@Override
		protected void onPreExecute()
		{
			progress = new ProgressDialog(MainActivity.mainContext);
//			progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			progress.setTitle( "Updating" );
//			progress.setMessage("잠시만 기다려주세요");
//			progress.show();

			super.onPreExecute();
		}

		@Override
		protected TrainInfo doInBackground(Void... strData)
		{
			//상태, 지연시간
			String[] delayInfo = WebExtract.getDelayInfo(WebExtract.getDelayHTML(userTrain.getDate(), userTrain.getTrainNo()));
			int delay = Integer.valueOf(delayInfo[1].replace("분", ""));
			String[] estimateDepInfo = WebExtract.calEstimateTime(userTrain.getDate(), userTrain.getTime(), delay);
			String[] estimateArrInfo = WebExtract.calEstimateTime(userTrain.getDate(), userTrain.getArrTime(), delay);
			
			TrainInfo sendTrain = new TrainInfo();
			
			sendTrain.setDepDate(userTrain.getDate());
			sendTrain.setDepTime(userTrain.getTime());
			sendTrain.setArrTime(userTrain.getArrTime());
			sendTrain.setDepName(userTrain.getDepStation());
			sendTrain.setArrName(userTrain.getArrStation());
			sendTrain.setEstimateDepTime(estimateDepInfo[0]);
			sendTrain.setEstimateArrTime(estimateArrInfo[0]);
			sendTrain.setDelayTime(delayInfo[1]);
			sendTrain.setRemainTime(estimateDepInfo[1]);
			sendTrain.setIcon(userTrain.getTrainType());
			sendTrain.setTrainNo(userTrain.getTrainNo());
			sendTrain.setType(userTrain.getTrainType());
			sendTrain.setStatus(delayInfo[0]);
			
			return sendTrain;
		}

		@Override
		protected void onPostExecute(TrainInfo thisTrain)
		{	
			Bundle bundle = new Bundle();
			bundle.putSerializable("clickedItem", thisTrain);
			
			InterestTrainDialog popup = new InterestTrainDialog();
			popup.setArguments(bundle);
			popup.show(getActivity().getFragmentManager(), "열차상세정보");
			
			//progress.dismiss();
		}
	}
	
	private class OnSwipeCallback implements DismissSwipeListViewTouchListener.OnSwipeCallback
	{
		@Override
		public void onSwipeLeft(ListView listView, int[] reverseSortedPositions)
		{
			InterestTrain selectedTrain = (InterestTrain) listView.getItemAtPosition(reverseSortedPositions[0]);
			Log.i("왼쪽어디누름?", selectedTrain.toString());
			Log.i("현재개수?", listView.getCount()+"");
		}
	
		@Override
		public void onSwipeRight(ListView listView, int[] reverseSortedPositions)
		{
			Log.i("오른쪽어디누름?", listView.getItemAtPosition(reverseSortedPositions[0]).toString());
			Log.i("현재개수?", listView.getCount()+"");
		}
	}
}
