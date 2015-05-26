package com.praisehim.koraildelay;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InterestTrainAdapter extends BaseAdapter
{
	private ListView listView;
	private Context context;
	private InterestObservableArray listData;
    private class ViewHolder
    {
    	public TextView itemTrain;
    	public TextView itemDepInfo;
    	public TextView itemArrInfo;
    	public ImageView itemIcon;
    	public TextView delView;
    }

	public InterestTrainAdapter(Context context, InterestObservableArray listData, ListView parent)
	{
		this.context = context;
		this.listData = listData;
		this.listView = parent;
	}
	
	public void setData(InterestObservableArray newListData)
	{
		listData = newListData;
	}

	public void remove(InterestTrain item)
	{
		listData.remove(item);
	}
	
	public void addItem(InterestTrain item)
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

	@Override
	public View getView(final int position, View rowView, final ViewGroup parent)
    {
		ViewHolder holder;
		// reuse the rowView if possible - for efficiency and less memory consumption
		if (rowView == null)
		{	
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	rowView = inflater.inflate(R.layout.interest_row, parent, false);
	    	
	    	// configure view holder
	    	holder = new ViewHolder();
	    	holder.itemTrain = (TextView) rowView.findViewById(R.id.interestItemTrainType);
	    	holder.itemDepInfo = (TextView) rowView.findViewById(R.id.interestItemDepInfo);
	    	holder.itemArrInfo = (TextView) rowView.findViewById(R.id.interestItemArrInfo);
	    	holder.itemIcon = (ImageView) rowView.findViewById(R.id.interestItemIcon);
	    	holder.delView = (TextView) rowView.findViewById(R.id.userDelView);
	    	rowView.setTag(holder);
		}
		
		else // set the view
		{
			holder = (ViewHolder) rowView.getTag();
	    }
		
	    InterestTrain item = listData.get(position);

	    holder.itemTrain.setText(item.getTrainType() + "\n" + item.getTrainNo());
	    holder.itemArrInfo.setText(item.getArrStation() + "\n" + item.getArrTime());
	    holder.itemDepInfo.setText(item.getDepStation() + "\n" + item.getTime());
	    holder.itemIcon.setImageResource(TrainInfo.getIconId(item.getTrainType()));
	    holder.delView.setOnClickListener(new OnClickListener()
	    {
			@Override
			public void onClick(View v)
			{
				v.animate().alpha(0).setDuration(300).setListener(new AnimatorListener()
				{
					@Override
					public void onAnimationCancel(Animator animation){}
					@Override
					public void onAnimationRepeat(Animator animation){}
					@Override
					public void onAnimationStart(Animator animation){}
					@Override
					public void onAnimationEnd(Animator animation)
					{
						Data.INTEREST_LIST.remove(position);
						
						InterestTrainAdapter.this.setData(Data.INTEREST_LIST);
						notifyDataSetChanged();
						listView.setAdapter(InterestTrainAdapter.this);
						
						InterestTrain.saveInterestContext();
					}
				});		
			}
	    });

		return rowView;
	}
}
