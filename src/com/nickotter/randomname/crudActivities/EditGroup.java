package com.nickotter.randomname.crudActivities;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.EasyTracker;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.Group;
import com.nickotter.randomname.R;

public class EditGroup  extends SherlockFragmentActivity {
	
	final String LOGTAG = "EditGroupActivity";
	
	protected CRUD databaseCRUD = null;
	
	protected Group group = null;
	protected EditText groupElement = null;
	String old = null;
	
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
		setContentView(R.layout.edit_group);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        Log.v(LOGTAG, "\taction bar has home enabled");
        
        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();
        
        this.groupElement = (EditText)findViewById(R.id.editGroupName);
        
        //setting the groupId from the provided by intent
        int groupId = getIntent().getIntExtra("groupId",-1);
        
        //If no group is found (error default to first group)
        if(groupId == -1) 
        	groupId = 0;
        
        this.group = databaseCRUD.get_group(groupId);
        Log.v(LOGTAG, "Found group with name and setting text field to: " + group.getName());
        this.groupElement.setText(this.group.getName());
        old = group.getName();
        
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
        	  
        	  
        	  if(this.groupElement == null)
        	  {
        		  Log.v(LOGTAG, "Error: Group must not be blank");
        		  finish();
        	  }
        	  
        	  else
        	  {
        		  this.group.setName(this.groupElement.getText().toString());
        		  
        		  Log.v(LOGTAG, "Adding new group with name and ID: " + this.group.getName() + " " + this.group.getID());
        	  
        		  databaseCRUD.update_group(this.group);
        	  
        		  finish();
        	  }
        	  
        	  
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}
