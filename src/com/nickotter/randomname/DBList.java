package com.nickotter.randomname;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

//import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.nickotter.randomname.MyList;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.ItemListAdapter;
import com.nickotter.randomname.crudActivities.AddGroup;
import com.nickotter.randomname.crudActivities.AddItem;
import com.nickotter.randomname.crudActivities.AddList;

public class DBList extends SherlockListFragment implements
TextToSpeech.OnInitListener {

	final String LOGTAG = "DBList";
	
	private TextToSpeech tts;
	
	private CRUD databaseCRUD;
	
	private int position = 0;
	
	List<MyList> lists;
	
	DBList(int position) {
		Log.v(LOGTAG, "DBList e");
		
		this.position = position;
		
		Log.v(LOGTAG, "DBList x");
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tts = new TextToSpeech(getActivity(), this);
		
		setHasOptionsMenu(true);
		
		Log.v(LOGTAG, "\t initializing CRUD");
		this.databaseCRUD = new CRUD(getActivity());
		this.databaseCRUD.open();

		//MyList tempList = new MyList(this.position + 1, 1, "who cares");
		
		List<Group> groupList = databaseCRUD.query_group();
		Group group = groupList.get(0);
		
		this.lists = databaseCRUD.query_list(group);
		
        this.databaseCRUD.close();
		
	}//END void onCreate
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(LOGTAG, "onCreateView e");

		Log.v(LOGTAG, "\tusing position: " + position + 1);

		Log.v(LOGTAG, "\t initializing CRUD");
		this.databaseCRUD = new CRUD(getActivity());
		this.databaseCRUD.open();

		MyList tempList = new MyList(this.position + 1, 1, "who cares");

		List<Item> items = databaseCRUD.query_item(tempList);

		Log.v(LOGTAG, "\tusing ItemListAdapter to bring List<items> to listview");
		ListAdapter adapter = new ItemListAdapter(inflater.getContext(), items);

        //Setting the list adapter for the ListFragment
		Log.v(LOGTAG, "\tsetting list adapter");
        setListAdapter(adapter);
 
        this.databaseCRUD.close();
        
        Log.v(LOGTAG, "onCreateView x, returning super");
        
        //Setting the list adapter for the ListFragment
        setListAdapter(adapter);

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
    public void onListItemClick(ListView l, View v, int itemPosition, long id) {
        Log.v(LOGTAG, "You clicked on item number " + position);
        //Log.v(LOGTAG, "The selected item is: " + this.groupMembers[position][itemPosition]);
        //speakOut(this.groupMembers[position][itemPosition]);
    }//END void onListItemClick
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

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
    		
	    	case R.id.menu_add_group:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add Group");
	    		Intent igroup = new Intent(getActivity(), AddGroup.class);
	    		startActivity(igroup); 
	    		
	    		break;
	   
	    	case R.id.menu_add_list:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add List");
	    		Intent ilist = new Intent(getActivity(), AddList.class);
	    		startActivity(ilist);
	    		
	    		break;   	
	    		
	    	case R.id.menu_add_item:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add item");
	    		Intent iitem = new Intent(getActivity(), AddItem.class);
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
