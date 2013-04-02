package com.nickotter.randomname;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

import com.nickotter.randomname.DummySectionFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	
	String LOGTAG = "SectionsPagerAdapter";

	private Context context = null;
	
	public List<MyList> lists = null;
	
	public SectionsPagerAdapter(FragmentManager fm, Context c, List<MyList> tempList) {
		super(fm);
		this.context = c;
		
		this.lists = tempList;
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
		Log.v(LOGTAG, "using position: " + position);
		DBList fragment = new DBList();
		
		return fragment;
	}//END Fragment getItem

	@Override
	public int getCount() {
		// Show 3 total pages.
		return this.lists.size();
	}//END int getCount
	
}//END class SectionsPagerAdapter
