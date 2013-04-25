package com.nickotter.randomname.crudActivities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.Group;
import com.nickotter.randomname.MyList;
import com.nickotter.randomname.R;

public class AddList extends SherlockFragmentActivity {

	final String LOGTAG = "AddListActivity";
	
	protected CRUD databaseCRUD = null;
	protected List<Group> groups = null;
	
	protected int currentGroupIndex = 0;
	
	protected int currentGroupDBIndex = 0;
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this); // Add this method.
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this); // Add this method.
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(LOGTAG, "onCreate start");
		setContentView(R.layout.add_list);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        
        Log.v(LOGTAG, "\taction bar has home enabled");
        
        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();
        
        Log.v(LOGTAG, "\tquery for groups");
        this.currentGroupDBIndex = getIntent().getIntExtra("groupId", -1);
        Log.v(LOGTAG, "groupId after getIntent" + this.currentGroupDBIndex);
        
        this.groups = this.databaseCRUD.query_group();
        
        if (this.currentGroupDBIndex == -1) {
        	finish();
        }
        
        
        //this needs to be changed to a custom adapter
        Log.v(LOGTAG, "\tinitalizing a group list");
        List<String> tempGroup = new ArrayList<String>();
        
        int i = 0;
        for (Group group : this.groups){
        	//tempGroup.add(group.getName());
			if (group.getID() == this.currentGroupDBIndex) {
				this.currentGroupIndex = i;
			}
			tempGroup.add(group.getName());
			i++;
        }
        
        Log.v(LOGTAG, "\tinitalizing spinner objet from layout");
        Spinner spinner = (Spinner) findViewById(R.id.CRUDgroupSpinner);
        
        Log.v(LOGTAG, "\tarray adapter for temp group list");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempGroup);
        
        Log.v(LOGTAG, "\tsetting array adapter");
        spinner.setAdapter(spinnerArrayAdapter);
        
        
        spinner.setSelection(this.currentGroupIndex);
        Log.v(LOGTAG, "onCreate end");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.databaseCRUD.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.add_general, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
    {    
       switch (item.getItemId()) 
       {        
          case android.R.id.home:
          case R.id.cancelButton:
        	  finish();
              return true;  
              
          case R.id.doneButton:
        	  
        	  //list check
        	  EditText listField = (EditText)findViewById(R.id.listName);
        	  if(listField == null)
        	  {
        		  Log.v(LOGTAG, "Error: list was blank");    	
        		  finish();
        	  }
        	  
        	  //group check + return
        	  Spinner spinner = (Spinner) findViewById(R.id.CRUDgroupSpinner);
        	  int selectedGroup = spinner.getSelectedItemPosition();
        	  
        	  Group group = this.groups.get(selectedGroup);
        	  Log.v(LOGTAG, "A group was selected with name and ID: " + group.getName() + " " + group.getID());
    		  MyList l1 = new MyList(0, group.getID(), listField.getText().toString());
    		  databaseCRUD.add_list(group, l1);

        	  
        	  //finish
        	  Log.v(LOGTAG, "New list entry has id=" + l1.getID());
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}