package com.nickotter.randomname;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;

//import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.speech.tts.TextToSpeech;

import com.nickotter.randomname.MyList;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.ItemListAdapter;
import com.nickotter.randomname.crudActivities.AddGroup;
import com.nickotter.randomname.crudActivities.AddItem;
import com.nickotter.randomname.crudActivities.AddList;
import com.nickotter.randomname.crudActivities.EditGroup;
import com.nickotter.randomname.crudActivities.EditList;

public class DBList extends SherlockListFragment implements
TextToSpeech.OnInitListener {


	final String LOGTAG = "DBList";
	
	private TextToSpeech tts;
	
	private int currentGroup;
	
	private CRUD databaseCRUD;
	
	List<Item> items = null;
	
	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);
		
		Log.v(LOGTAG, "Context Menu registartion complete");
		registerForContextMenu(getListView());
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		tts = new TextToSpeech(getActivity(), this);
		
		setHasOptionsMenu(true);
		
		//ListView l = (ListView)findViewbyId(R.id.listView1);
		
		
	}//END void onCreate
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(LOGTAG, "onCreateView e");
		
		Bundle argument = getArguments();
		this.items = (List<Item>) argument.getSerializable("items");
		this.currentGroup = argument.getInt("currentGroup");
		
		for (Item tempItem : this.items) {
			Log.v(LOGTAG, "found item with name=" + tempItem.getName());
		}

		Log.v(LOGTAG, "\tusing ItemListAdapter to bring List<items> to listview");
		ListAdapter adapter = new ItemListAdapter(inflater.getContext(), this.items);

		
		
        //Setting the list adapter for the ListFragment
		Log.v(LOGTAG, "\tsetting list adapter");
        setListAdapter(adapter);
        
        Log.v(LOGTAG, "onCreateView x, returning super");

        return super.onCreateView(inflater, container, savedInstanceState);
    }//END View onCreateView
	
	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        
        super.onDestroy();
    }//END void onDestroy
	
	
	@Override 
    public void onListItemClick(ListView l, View v, int itemPosition, long id) 
	{
		//Log.v(LOGTAG, "List Selection: launching context menu");
		//l.showContextMenu();
		
        //Log.v(LOGTAG, "The selected item is: " + this.groupMembers[position][itemPosition]);
        //speakOut(this.groupMembers[position][itemPosition]);
    }//END void onListItemClick
    
	
	public void onListLongItemClick(ListView l, View v, long id)
	{
		Log.v(LOGTAG, "List Long Selection: launching context menu");
		l.showContextMenu();
	}
	
	//Context Menu stuff
	    //Context menu listener test

	
	@Override        
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		//if (v.getId()==R.id.groupListView) 
	    //{
			Log.v(LOGTAG, "Creating Context Menu");
	        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	        menu.setHeaderTitle("Activity Selection Context Menu");
	        //menu adds work like comment below
	        //add(int groupId, int itemId, int order, CharSequence title/or title resource)
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Add Group");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Add List");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Add Item");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Edit Group");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Edit List");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Edit Item");
	        
	        Log.v(LOGTAG, "Context Menu created");
	    //}
	};
	
    @Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		Log.v(LOGTAG, "Entered Context Item selection");
		if(item.getTitle()=="Add Group")
		{
			Log.v(LOGTAG, "Context Menu: Add Group context selected");
			Intent igroup = new Intent(getActivity(), AddGroup.class);
			igroup.putExtra("groupId", currentGroup);
			startActivity(igroup);
		}

		else if(item.getTitle()=="Add List")
		{
			Log.v(LOGTAG, "Context Menu: Add List context selected");
			Intent ilist = new Intent(getActivity(), AddList.class);
			ilist.putExtra("groupId", currentGroup);
			Log.v(LOGTAG, "Value of current group: " + currentGroup);
			startActivity(ilist);
		}

		else if(item.getTitle()=="Add Item")
		{
			Log.v(LOGTAG, "Context Menu: Add Item context selected");
			Intent iitem = new Intent(getActivity(), AddItem.class);
			iitem.putExtra("groupId", currentGroup);
			startActivity(iitem);
		}
		
		else if(item.getTitle()=="Edit Group")
		{
			Log.v(LOGTAG, "Context Menu: Edit Group context selected");
			Intent iegroup = new Intent(getActivity(), EditGroup.class);
			iegroup.putExtra("groupId", currentGroup);
			startActivity(iegroup);
		}
		
		else if(item.getTitle()=="Edit List")
		{
			Log.v(LOGTAG, "Context Menu: Edit List context selected");
			Intent ielist = new Intent(getActivity(), EditList.class);
			ielist.putExtra("groupId", currentGroup);
			ielist.putExtra("listId", 1);
			startActivity(ielist);
		}
		
		//Not implemented do not use
		else if(item.getTitle()=="Edit Item")
		{
			Log.v(LOGTAG, "Context Menu: Edit Item context selected");
			//Intent ieitem = new Intent(getActivity(), EditGroup.class);
			//ieitem.putExtra("group", currentGroup);
			//startActivity(ieitem);
		}

		else
			return false;
		
		//getActivity().closeContextMenu();

		return true;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	
    	EasyTracker.getTracker().sendEvent("ui_action", "button_press", item.getTitle().toString(), (long)item.getItemId());

	    int itemId = item.getItemId();
	    switch (itemId) {
	    	case android.R.id.home:

	        // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
	        break;
	        
	    	case R.id.menu_random:
    			Log.v(LOGTAG, "onOptionsItemSelected: Clicked Random");
    			
    			/*Random r = new Random();
    			int i1 = r.nextInt(this.groupMembers[position].length);
    			
    			Log.v(LOGTAG, Integer.toString(i1));
    			
    			speakOut(this.groupMembers[position][i1]);*/
    			
    			break;
    		
	    	/*case R.id.menu_add_group:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add Group");
	    		Intent igroup = new Intent(getActivity(), AddGroup.class);
	    		startActivity(igroup); 
	    		
	    		break;*/
	   
	    	case R.id.menu_add_list:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add List");
	    		Intent ilist = new Intent(getActivity(), AddList.class);
	    		ilist.putExtra("groupId", currentGroup);
	    		startActivity(ilist);
	    		
	    		break;   	
	    		
	    	case R.id.menu_add_item:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add item");
	    		
	    		Intent iitem = new Intent(getActivity(), AddItem.class);
	    		iitem.putExtra("groupId", currentGroup);
	    		startActivity(iitem);
	    		
	    		break;
	    		
	    		/*
	    	case R.id.menu_settings:
	    		Log.v(LOGTAG, "onOptionsItemSelected: Clicked Settings");
	    		
	    		break;*/
    			
	        default:
	        	Log.v(LOGTAG, "onOptionsItemSelected: failed to identify what was clicked");
	        	break;

	    }

	    return true;
	}//END boolean onMenuItemSelected

	@Override
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }//END void onInit
	
	
	private void speakOut(String text) {
 
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }//END void speakOut
	

}//END class DBList
