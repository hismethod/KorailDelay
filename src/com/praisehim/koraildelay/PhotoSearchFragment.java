package com.praisehim.koraildelay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class PhotoSearchFragment extends Fragment
{
	private Context context;
	
	@SuppressLint("ValidFragment")
	public PhotoSearchFragment(){}
	public PhotoSearchFragment(Context mContext)
	{
		context = mContext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_search_photo, null);

		return view;
	}
}
