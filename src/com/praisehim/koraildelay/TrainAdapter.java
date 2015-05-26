package com.praisehim.koraildelay;

import java.util.ArrayList;

import com.praisehim.koraildelay.SwipeLibrary.SwipeListView;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TrainAdapter extends BaseAdapter
{
	private ListView parentListView;
	private Context context;
	private ArrayList<TrainInfo> listData = new ArrayList<TrainInfo>();
    private class ViewHolder
    {
    	public TextView itemTrain;
    	public TextView itemDepInfo;
    	public TextView itemArrInfo;
    	public TextView itemDelayInfo;
    	
    	public LinearLayout backContainer;
    }
    
	public TrainAdapter(Context context, ArrayList<TrainInfo> listData, ListView temp)
	{
		this.context = context;
		this.listData = listData;
		parentListView = temp;
	}
	
	public TrainAdapter(Context context, ArrayList<TrainInfo> listData)
	{
		this.context = context;
		this.listData = listData;
	}
	
	public void setData(ArrayList<TrainInfo> newListData)
	{
		listData = newListData;
	}

	public void remove(TrainInfo item)
	{
		listData.remove(item);
	}
	
	public void addItem(TrainInfo item)
	{
		listData.add(item);
	}

	@Override
	public int getCount()
	{
		return listData.size();
	}

	@Override
	public Object getItem(int position)
	{
		return listData.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public void applyDepOption(TextView depView, TrainInfo item)
	{
		String depInfo = item.getDepName() + "\n";
		if(ResultActivity.option == 1)
		{
			depView.setText(depInfo + item.getDepTime());
		}
		
		else if(ResultActivity.option == 2)
		{
			final SpannableStringBuilder depTime = new SpannableStringBuilder(item.getEstimateDepTime());
			if(item.getDelayTime() != 0)
			{
				depTime.setSpan(new ForegroundColorSpan(depView.getResources().getColor(R.color.orange)), 0, depTime.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			depView.setText(depInfo + depTime);
		}
	}
	
	public void applyArrOption(TextView arrView, TrainInfo item)
	{
		String arrInfo = item.getArrName() + "\n";
		if(ResultActivity.option == 1)
		{
			arrView.setText(arrInfo + item.getArrTime());
		}
		
		else if(ResultActivity.option == 2)
		{
			final SpannableStringBuilder arrTime = new SpannableStringBuilder(item.getEstimateArrTime());
			if(item.getDelayTime() != 0)
			{
				arrTime.setSpan(new ForegroundColorSpan(arrView.getResources().getColor(R.color.orange)), 0, arrTime.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			arrView.setText(arrInfo + arrTime);
		}
	}

	@Override
	public View getView(final int position, View rowView, ViewGroup parent)
    {
		ViewHolder holder;
		// reuse the rowView if possible - for efficiency and less memory consumption
		if (rowView == null)
		{	
	    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	rowView = inflater.inflate(R.layout.custum_row, parent, false);
	    	
	    	// configure view holder
	    	holder = new ViewHolder();
	    	holder.itemTrain = (TextView) rowView.findViewById(R.id.itemTrainType);
	    	holder.itemDepInfo = (TextView) rowView.findViewById(R.id.itemDepInfo);
	    	holder.itemArrInfo = (TextView) rowView.findViewById(R.id.itemArrInfo);
	    	holder.itemDelayInfo = (TextView) rowView.findViewById(R.id.itemDelayInfo);
	    	holder.backContainer = (LinearLayout) rowView.findViewById(R.id.backViewContainer);
	    	rowView.setTag(holder);
		}
		
		else // set the view
		{
			holder = (ViewHolder) rowView.getTag();
	    }
		
	    TrainInfo item = listData.get(position);

	    holder.itemTrain.setText(item.getType() + "\n" + item.getTrainNo());

	    applyDepOption(holder.itemDepInfo, item);
	    applyArrOption(holder.itemArrInfo, item);
	    int delayTime = item.getDelayTime();
	    if(delayTime == 0)
	    {
	    	holder.itemDelayInfo.setText("정시운행");
	    	holder.itemDelayInfo.setTextColor(rowView.getResources().getColor(R.color.green));
	    }
	    else
	    {
	    	holder.itemDelayInfo.setText(delayTime + "분");
	    	holder.itemDelayInfo.setTextColor(rowView.getResources().getColor(R.color.orange));
	    }
	    
	    holder.backContainer.setOnClickListener(new OnClickListener()
	    {
			@Override
			public void onClick(View v)
			{
				InterestTrain.removeInterest(Data.TRAINLIST.get(position));
				SwipeListView listView = (SwipeListView) parentListView;
				listView.closeAnimate(position);
			}
	    });

		return rowView;
	}

}
