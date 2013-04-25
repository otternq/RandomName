package com.nickotter.randomname;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.google.analytics.tracking.android.EasyTracker;

import android.content.Context;
//import android.support.v4.app.ListFragment;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import com.nickotter.randomname.MyList;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.ItemListAdapter;
import com.nickotter.randomname.crudActivities.AddItem;
import com.nickotter.randomname.crudActivities.AddList;
import com.nickotter.randomname.crudActivities.EditItem;
import com.nickotter.randomname.crudActivities.EditList;

public class DBList extends SherlockListFragment implements
TextToSpeech.OnInitListener {


	final String LOGTAG = "DBList";
	
	//Shake variables
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
	
	private TextToSpeech tts;
	
	private int currentGroup;
	private int selectedItem;
	
	private int exclusionList;
	private int exclusionItem;
	
	private ItemListAdapter adapter = null;
	
	CRUD databaseCRUD = null;
	
	List<Item> items = null;
	
	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);
		
		Log.v(LOGTAG, "Context Menu registartion complete");
		registerForContextMenu(getListView());
		
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() 
		{
	        public boolean onItemLongClick(AdapterView<?> l, View v, int itemPosition, long id) 
	        {
	    		Log.v(LOGTAG, "List Long Selection: launching context menu");
	        	selectedItem = (int) id;
	    		l.showContextMenu();
	            return true;
	        }
	    });
	}
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
        Log.v(LOGTAG, "\topening database connection");
        //database needs to open lol...
        this.databaseCRUD = new CRUD(getActivity());
        this.databaseCRUD.open();
		
		tts = new TextToSpeech(getActivity(), this);
		
		setHasOptionsMenu(true);
		
		//ListView l = (ListView)findViewbyId(R.id.listView1);
		
		//Shake acceleration listeners (value of 0 is not shaking and greater than 2 is shaking device)
        Log.v(LOGTAG, "Initilization of Shake detection");
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();   

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() 
        {
        	public void onShake() 
        	{    		
        		Log.v(LOGTAG, "Shake Recieved");
        	    if(databaseCRUD.query_Shake() == true)
        	    	randomItem();       
        	}
        });
        
	  //Resume Shake
	  mSensorManager.registerListener(mSensorListener,
	        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_UI);
		
		
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
		adapter = new ItemListAdapter(inflater.getContext(), this.items);

		
		
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
        
        //Pause Shake
	    mSensorManager.unregisterListener(mSensorListener);
        
        super.onDestroy();
    }//END void onDestroy
	
	
    public void onListItemClick(ListView l, View v, int itemPosition, long id) 
	{
    	if(this.databaseCRUD.query_Verbal() == true)
    	{
    		Log.v(LOGTAG, "Speak out for: " + this.items.get(itemPosition).getName());
    		speakOut(this.items.get(itemPosition).getName());
    	}
    	
    	else
    		Log.v(LOGTAG, "Failed to speak out, disabled option");
    }//END void onListItemClick
	
	
	@Override        
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) 
	{
		super.onCreateContextMenu(menu, v, menuInfo);
			Log.v(LOGTAG, "Creating Context Menu");
	        //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	        menu.setHeaderTitle("Item Context Menu");
	        //menu adds work like comment below
	        //add(int groupId, int itemId, int order, CharSequence title/or title resource)
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Edit Item");
	        menu.add(android.view.Menu.NONE, v.getId(), 0, "Delete Item");
	        
	        Log.v(LOGTAG, "Context Menu created");
	};
	
    @Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		Log.v(LOGTAG, "Entered Context Item selection");		
		//Not implemented do not use
		if(item.getTitle()=="Edit Item")
		{
			Log.v(LOGTAG, "Context Menu: Edit Item context selected");
			Intent ieitem = new Intent(getActivity(), EditItem.class);
			ieitem.putExtra("groupId", currentGroup);
			ieitem.putExtra("listId", this.items.get(0).getListId());
			Log.v(LOGTAG, "selectedItem before launch: " + selectedItem);
			//ieitem.putExtra("itemId", 1);
			ieitem.putExtra("itemId", selectedItem);
			startActivity(ieitem);
		}
		
		else if(item.getTitle()=="Delete Item")
		{
			Log.v(LOGTAG, "Context Menu: Delete Item context selected");
			Item di = this.databaseCRUD.get_item(selectedItem);
         	Log.v(LOGTAG, "Deleting item with name and id: " + di.getName() + " " + di.getID());
			this.databaseCRUD.delete_item(di);
			
			Log.v(LOGTAG, "selectedItem before launch: " + selectedItem);
			
			MainActivity m = (MainActivity) getActivity();
			m.loadLists();
			
		}

		else
			return false;

		return true;
	}
	
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main, menu);
        
        super.onCreateOptionsMenu(menu, inflater);
    }
	
	public void randomItem() 
	{
		Random r = new Random();
		int i1 = 0;

		if(this.items.size() != 0)
		{
			//exclusion on
			if(databaseCRUD.query_Exclusion() == true)
			{
				//protection of singular item
				if(this.items.size() != 1)
				{
					//loop until non-similar is found
					i1 = r.nextInt(this.items.size());
					while(exclusionItem == i1)
					{
						Log.v(LOGTAG, "exclusion detected: " + exclusionItem + " : " + i1);
						i1 = r.nextInt(this.items.size());
					}
				}
				//singular item
				else
				{
					i1 = r.nextInt(this.items.size());
					Log.v(LOGTAG, "Single item detected with id: " + i1);
				}
			
				//new exclusion
				exclusionItem = i1;
			}
			//exclusion off
			else
			{
				i1 = r.nextInt(this.items.size());
				Log.v(LOGTAG, "Exclusion turned off");
			}
		
			Log.v(LOGTAG, "Randomed Item id: " + Integer.toString(i1));		
			if(databaseCRUD.query_Verbal() == true) {
				this.speakOut(this.items.get(i1).getName());
				
				Toast toast = Toast.makeText(getActivity(), this.items.get(i1).getName(), Toast.LENGTH_SHORT);
				toast.show();
				
			} else {
			
			}
			//possibly flash the chosen item
		}
		
		//no items found
		else
		{
			Log.v(LOGTAG, "No Items found");
			if(databaseCRUD.query_Verbal() == true)
				this.speakOut("Random selection failed, no items detected");
	
				Toast toast = Toast.makeText(getActivity(), "Random selection failed, no items detected", Toast.LENGTH_SHORT);
				toast.show();
		}
	}
	
	public void randomList()
	{
		/*
		Random r = new Random();
		int l1 = 0;

		if(this.tabs.size() != 0)
		{
			//exclusion on
			if(databaseCRUD.query_Exclusion() == true)
			{
				//protection of singular list
				if(this.tabs.size() != 1)
				{
					//loop until non-similar is found
					l1 = r.nextInt(this.tabs.size());
					while(exclusionItem == l1)
					{
						Log.v(LOGTAG, "exclusion detected: " + exclusionList + " : " + l1);
						l1 = r.nextInt(this.tabs.size());
					}
				}
				//singular list
				else
				{
					l1 = r.nextInt(this.tabs.size());
					Log.v(LOGTAG, "Single item detected with id: " + l1);
				}
			
				//new exclusion
				exclusionList = l1;
			}
			//exclusion off
			else
			{
				l1 = r.nextInt(this.tabs.size());
				Log.v(LOGTAG, "Exclusion turned off");
			}
		
			Log.v(LOGTAG, "Randomed List id: " + Integer.toString(l1));		
			if(databaseCRUD.query_Verbal() == true)
				this.speakOut(this.tabs.get(l1).getName());
				>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>switch tabs goes here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			else
				;
				//possibly flash the chosen item
				>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>switch tabs goes here<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		}
		
		//no items found
		else
		{
			Log.v(LOGTAG, "No Lists found");
			if(databaseCRUD.query_Verbal() == true)
				this.speakOut("Random selection failed, no lists detected");
		}
		*/
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
    			
    			this.randomItem();
    			
    			break;
	   
	    	case R.id.menu_add_list:
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add List");
	    		Intent ilist = new Intent(getActivity(), AddList.class);
	    		ilist.putExtra("groupId", this.currentGroup);
	    		getActivity().startActivity(ilist);
	    		
	    		break;
	    		
	    	case R.id.menu_add_item:
	    		
	    		Log.v(LOGTAG, "Main menu selection: Clicked Add item");    		
	    		Intent iitem = new Intent(getActivity(), AddItem.class);
	    		iitem.putExtra("groupId", this.currentGroup);
	    		startActivity(iitem);
	    		
	    		break;

	    	case R.id.menu_edit:
	    		Log.v(LOGTAG, "Main menu selection: Clicked on Edit List");
	    		
	    		Log.v(LOGTAG, "Context Menu: Edit List context selected");
				Intent ielist = new Intent(getActivity(), EditList.class);
				ielist.putExtra("groupId", currentGroup);
				ielist.putExtra("listId", this.items.get(0).getListId());
				startActivity(ielist);
				
				break;
    			
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
