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

public class EditList extends SherlockFragmentActivity {
	
	final String LOGTAG = "EditListActivity";
	
	protected CRUD databaseCRUD = null;
	
	protected int currentGroupIndex = -1;
	
	protected List<Group> groups = null;
	protected MyList list = null;
	
	protected EditText listField = null;
	
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
		setContentView(R.layout.edit_list);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        
        Log.v(LOGTAG, "\taction bar has home enabled");
        
        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();
        
        Log.v(LOGTAG, "\tquery for groups");
        int listId = getIntent().getIntExtra("listId",-1);
        Log.v(LOGTAG, "listId after getIntent" + listId);
        if(listId == -1)
        	listId = 1;
        
        this.groups = this.databaseCRUD.query_group();
        //failing on the line below index 0 requested with size of zero
        this.list = this.databaseCRUD.get_list(listId);
        
        this.listField = (EditText)findViewById(R.id.editListName);
        this.listField.setText(this.list.getName());
        
        //this needs to be changed to a custom adapter
        Log.v(LOGTAG, "\tinitalizing a group list");
        List<String> tempGroup = new ArrayList<String>();
        
        int i = 0;
        for (Group group : this.groups)
        {
        	if (group.getID() == getIntent().getIntExtra("groupId", -1)) {
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
	    inflater.inflate(R.menu.edit_general, menu);
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
        	  
        	  if(this.listField == null)
        	  {
        		  Log.v(LOGTAG, "Error: list was blank");    	
        		  finish();
        	  }
        	  
        	  //group check + return
        	  
        	  Spinner spinner = (Spinner) findViewById(R.id.CRUDgroupSpinner);
        	  int selectedGroupIndex = spinner.getSelectedItemPosition();
        	  
        	  
        	  Group group = this.groups.get(selectedGroupIndex);
        	  Log.v(LOGTAG, "A group was selected with name and ID: " + group.getName() + " " + group.getID());
    		  
        	  
        	  this.list.setName(this.listField.getText().toString());
        	  this.list.setGroupId(group.getID());
    		  
        	  databaseCRUD.update_list(this.list);
        	  finish();
        	  
        	  
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}
