package com.nickotter.randomname;

import java.util.Locale;
import java.util.Random;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

//import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class DBList extends SherlockListFragment implements
TextToSpeech.OnInitListener {

	final String LOGTAG = "DBList";
	
	private TextToSpeech tts;
	
	
	String[][] groupMembers = new String[][] {
			{
				"Grant Boomer",
				"Alex cochrane",
				"Tanis Lopez",
				"Santiago Pina Ros"
			},
			{
				"Everett Bloch", 
				"Ryan Sacksteder", 
				"Gresham Schlect", 
				"Pierce Trey"
			},
			{
				"Lyle Johnson", 
				"Jonathon Lamb"
			},
			{
				"Sean Heagerty", 
				"Long Nguyen", 
				"Nick Otter", 
				"Brett Papineau", 
				"Colby Rush"
			}
    };
	
	private int position = 0;
	
	DBList(int position) {
		this.position = position;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tts = new TextToSpeech(getActivity(), this);
		
		setHasOptionsMenu(true);
	}//END void onCreate
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    /** Creating an array adapter to store the list of countries **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,groupMembers[position]);
 
        /** Setting the list adapter for the ListFragment */
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
        Log.v(LOGTAG, "The selected item is: " + this.groupMembers[position][itemPosition]);
        speakOut(this.groupMembers[position][itemPosition]);
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
    			
    			Random r = new Random();
    			int i1 = r.nextInt(this.groupMembers[position].length);
    			
    			Log.v(LOGTAG, Integer.toString(i1));
    			
    			speakOut(this.groupMembers[position][i1]);
    			
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
