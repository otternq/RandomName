
package com.nickotter.randomname;


import com.nickotter.randomname.SectionsPagerAdapter;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {
	
	final String LOGTAG = "MainActivity";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	CRUD databaseCRUD = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.v(LOGTAG, "Deleting DATABASE_NAME="+ Sqlite.DATABASE_NAME);
		this.deleteDatabase(Sqlite.DATABASE_NAME);

		Log.v(LOGTAG, "Initializing CRUB object");
		databaseCRUD = new CRUD(this);
		
		Log.v(LOGTAG, "opening database connection in CRUD object");
		databaseCRUD.open();
		
		Log.v(LOGTAG, "Calling createGroups from onCreate");
		createGroups();
		
		Log.v(LOGTAG, "Loading actionbar");
		
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		//INSERT RANDOM COMMENT
		//Sean INSERT RANDOM COMMENT!!!
        
		actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), getApplicationContext());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}//END void onCreate
	
	/*public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    int itemId = item.getItemId();
	    switch (itemId) {
	    	case android.R.id.home:

	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;
	        
	    	case R.id.menu_random:
    			Log.v(LOGTAG, "onOptionsItemSelected: Clicked Random");
    			break;
    			
	    	case R.id.menu_add_item:
	    		Log.v(LOGTAG, "onOptionsItemSelected: Clicked Add item");
	    		
	    		break;
	    		
	    	case R.id.menu_add_list:
	    		Log.v(LOGTAG, "onOptionsItemSelected: Clicked Add List");
	    		
	    		break;
	    		
	    	case R.id.menu_settings:
	    		Log.v(LOGTAG, "onOptionsItemSelected: Clicked Settings");
	    		
	    		break;
    			
	        default:
	        	Log.v(LOGTAG, "onOptionsItemSelected: failed to identify what was clicked");
	        break;

	    }

	    return true;
	}//END boolean onMenuItemSelected*/

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}//END boolean onCreateOptionsMenu*/

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}//END void onTabSelected

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}//END void onTabUnselected

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}//END void onTabReselected
	
	@Override
	protected void onResume() {
		Log.v(LOGTAG, "onResuem e");
		
		super.onResume();
		databaseCRUD.open();
		
		Log.v(LOGTAG, "onResume x");
	}
	
	@Override
	protected void onPause() {
		Log.v(LOGTAG, "onPause e");
		
		super.onPause();
		
		if (databaseCRUD != null) {
			databaseCRUD.close();
		} else {
			Log.v(LOGTAG, "\tdatabaseCRUD is null");
		}
		
		Log.v(LOGTAG, "onPause x");
	}
	
	public void createGroups() {
		Log.v(LOGTAG, "createGroups e");
		
//		Log.v(LOGTAG, "deleting data for " + Sqlite.DATABASE_ITEM);
//		databaseCRUD.destroyTable(Sqlite.DATABASE_ITEM);
		
		
		
		Log.v(LOGTAG, "\tcreating groups start");
		Group g1 = new Group("CS480");
		Group g2 = new Group("CS481");
		Log.v(LOGTAG, "\tcreating groups end");
		
		MyList l1 = new MyList(1, g1.getID(), "List 1");
		MyList l2 = new MyList(2, g1.getID(), "List 2");
		MyList l3 = new MyList(3, g1.getID(), "List 3");
		MyList l4 = new MyList(4, g1.getID(), "List 4");
		MyList l5 = new MyList(5, g1.getID(), "List 5");
		
		Log.v(LOGTAG, "Adding Groups");
		databaseCRUD.add_group(g1);
		Log.v(LOGTAG, "Group 1 now has id="+ g1.getID());
		
		databaseCRUD.add_group(g2);
		Log.v(LOGTAG, "Group 2 now has id="+ g2.getID());
		
		Log.v(LOGTAG, "Adding Lists");
		databaseCRUD.add_list(g1, l1);
		databaseCRUD.add_list(g1, l2);
		databaseCRUD.add_list(g1, l3);
		databaseCRUD.add_list(g1, l4);
		databaseCRUD.add_list(g1, l5);
		
		Log.v(LOGTAG, "Adding Items");
		Item i1 = new Item(1, l1.getID(), "Item 1");
		Item i2 = new Item(2, l1.getID(), "Item 2");
		Item i3 = new Item(3, l1.getID(), "Item 3");
		Item i4 = new Item(4, l1.getID(), "Item 4");
		Item i5 = new Item(5, l1.getID(), "Item 5");
		
		databaseCRUD.add_item(l1, i1);
		databaseCRUD.add_item(l1, i2);
		databaseCRUD.add_item(l1, i3);
		databaseCRUD.add_item(l1, i4);
		databaseCRUD.add_item(l1, i5);
		
		
		
		
		
		Log.v(LOGTAG, "Query Group");
		List<MyList> tempList = databaseCRUD.query_list(g1);
		
		Log.v(LOGTAG, "\t There are "+ tempList.size() + " items in temp");
		
		for(MyList t : tempList) {
			Log.v(LOGTAG, t.getName() + " is a part of group " + t.getGroupID());
			
			Log.v(LOGTAG, "\t\t querying for list items");
			List<Item> tempItem = databaseCRUD.query_item(t);
			
			Log.v(LOGTAG, "\t\t There are " + tempItem.size() + " items in the list");
			for(Item tI : tempItem) {
				Log.v(LOGTAG, "\t\t\t" + "item name="+ tI.getName());
			}
		}
		
		
		Log.v(LOGTAG, "createGroups x");
		
		
	}

}//END class MainActivity

