package com.praisehim.koraildelay;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
	Context mContext;
	//static Fragment searchFragment;

	public SectionsPagerAdapter(Context context, FragmentManager fm)
	{
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position)
	{
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		switch (position)
		{
		case 0:
			//searchFragment = new DefaultSearchFragment(mContext);
			return new SearchHostFragment(mContext);
		case 1:
			return new UsersaveFragment(mContext);
		case 2:
			return null;
			//return new WebViewMainFragmnet(mContext);
		}
		return null;
	}

	@Override
	public int getCount()
	{
		// Show 3 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		// TODO Auto-generated method stub
		switch (position)
		{
		case 0:
			return "검색";
		case 1:
			return "사용자 목록";
		case 2:
			return null;
			//return mContext.getString(R.string.title_section3).toUpperCase();
		}
		return null;
	}

}
