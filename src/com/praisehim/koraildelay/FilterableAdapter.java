package com.praisehim.koraildelay;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class FilterableAdapter extends BaseAdapter implements Filterable
{
	private Activity context;
	private ArrayList<StationInfo> mDataShown;
	private ArrayList<StationInfo> mAllData;
	private Filter mFilter;
	private LayoutInflater inflater;

	public FilterableAdapter(Activity context, ArrayList<StationInfo> data)
	{
		this.context = context;
		this.mAllData = data;
		this.mDataShown = data;
	}

	public void add(StationInfo object)
	{
		mAllData.add(object);
		this.notifyDataSetChanged();
	}

	public int getCount(){	return mDataShown.size(); }
	public StationInfo getItem(int position){	return mDataShown.get(position);}
	public long getItemId(int position){return position;}
	public Filter getFilter()
	{
		if (mFilter == null)
		{
			mFilter = new CustomFilter();
		}
		return mFilter;
	}

	@Override
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}
	
    /**
     * 핵심 메소드
     */
    private ArrayList<StationInfo> autocomplete(String input)
    {
        ArrayList<StationInfo> result = null;

        result = new ArrayList<StationInfo>();
        if(input.equals("")) return result;
        
        for(StationInfo x : mAllData)
        {
        	if(SoundSearcher.matchString(x.getName(), input))
        	{
        		result.add(x);
        	}
        }
        return result;
    }
	
    private class ViewHolder
    {
    	public ImageView itemImage;
    	public TextView itemName;
    	public TextView itemDescription;
    }

	private class CustomFilter extends Filter
	{

		@Override
		protected FilterResults performFiltering(CharSequence constraint)
		{
			FilterResults results = new FilterResults();

			if (constraint == null || constraint.length() == 0)
			{
				ArrayList<StationInfo> list = new ArrayList<StationInfo>(mAllData);
				results.values = list;
				results.count = list.size();
			}
			else
			{
				ArrayList<StationInfo> newValues = new ArrayList<StationInfo>();
				newValues = autocomplete(constraint.toString());
				
				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results)
		{
			mDataShown = (ArrayList<StationInfo>) results.values;
			Log.d("CustomArrayAdapter", String.valueOf(results.count));
			notifyDataSetChanged();
		}

	}

    @Override
	public View getView(int position, View rowView, ViewGroup parent)
    {
		// reuse the rowView if possible - for efficiency and less memory consumption
		if (rowView == null)
		{	
	    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	rowView = inflater.inflate(R.layout.data_item, null);
	    	
	    	// configure view holder
	    	ViewHolder viewHolder = new ViewHolder();
	    	viewHolder.itemImage = (ImageView) rowView.findViewById(R.id.itemImage);
	    	viewHolder.itemName = (TextView) rowView.findViewById(R.id.itemName);
	    	viewHolder.itemDescription = (TextView) rowView.findViewById(R.id.itemDescription);
	    	rowView.setTag(viewHolder);
		}
		
		// set the view
	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    StationInfo item = null;
	    
	    try
	    {
	    	item = mDataShown.get(position);
	    } catch(IndexOutOfBoundsException e){
	    	Log.e("에러통과", "배열길이초과 무시");
	    	return rowView;
	    }

	    //setItemImage(holder.itemImage, item.getItemPhotoUri());
	    
	    if(item != null)
	    	holder.itemName.setText(item.getName());
	    //holder.itemDescription.setText(item.getItemDescription());
		
		return rowView;
	}
}