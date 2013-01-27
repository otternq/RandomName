package com.nickotter.randomname;

import java.util.Locale;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class DBList extends ListFragment implements
TextToSpeech.OnInitListener {

	final String LOGTAG = "DBList";
	
	private TextToSpeech tts;
	
	
	String[] countries = new String[] {
        "India",
        "Pakistan",
        "Sri Lanka",
        "China",
        "Bangladesh",
        "Nepal",
        "Afghanistan",
        "North Korea",
        "South Korea",
        "Japan"
    };
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tts = new TextToSpeech(getActivity(), this);
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    /** Creating an array adapter to store the list of countries **/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1,countries);
 
        /** Setting the list adapter for the ListFragment */
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
    }
	
	@Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.v(LOGTAG, "You clicked on item number " + position);
        Log.v(LOGTAG, "The selected item is: " + this.countries[position]);
        speakOut(this.countries[position]);
    }

	@Override
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut("Hello");
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }
	
	
	private void speakOut(String text) {
 
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
	

}
