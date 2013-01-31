package com.nickotter.randomname;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nickotter.randomname.DummySectionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Context context = null;
	
	public SectionsPagerAdapter(FragmentManager fm, Context c) {
		super(fm);
		this.context = c;
	}//END Constructor

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		
		/*Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);*/
		
		DBList fragment = new DBList(position);
		
		return fragment;
	}//END Fragment getItem

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 4;
	}//END int getCount

	@Override
	public CharSequence getPageTitle(int position) {
		
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase();
		case 1:
			return context.getString(R.string.title_section2).toUpperCase();
		case 2:
			return context.getString(R.string.title_section3).toUpperCase();
		case 3:
			return context.getString(R.string.title_section4).toUpperCase();
		}
		return null;
	}//END CharSequence getPageTitle
	
}//END class SectionsPagerAdapter
