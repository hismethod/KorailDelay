package com.praisehim.koraildelay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class SearchHostFragment extends Fragment
{
	private FragmentTabHost mTabHost;
	private Class<?> fragments[] = { DefaultSearchFragment.class, NumberSearchFragment.class, PhotoSearchFragment.class };
	private String tabLabels[] = { "기본검색", "열차번호로 검색", "티켓사진으로 검색" };
	private Context context;
	
	public SearchHostFragment(Context context)
	{
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.fragment_search_host);

		mTabHost.addTab(mTabHost.newTabSpec(tabLabels[0]).setIndicator(tabLabels[0]), fragments[0], new Bundle());
		mTabHost.addTab(mTabHost.newTabSpec(tabLabels[1]).setIndicator(tabLabels[1]), fragments[1], new Bundle());
		mTabHost.addTab(mTabHost.newTabSpec(tabLabels[2]).setIndicator(tabLabels[2]), fragments[2], new Bundle());
		
		return mTabHost;
	}
}