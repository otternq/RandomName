package com.nickotter.randomname.crudActivities;

import java.util.List;

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
import com.nickotter.randomname.Item;
import com.nickotter.randomname.MyList;
import com.nickotter.randomname.R;

public class EditGroup  extends SherlockFragmentActivity {
	
	final String LOGTAG = "EditGroupActivity";
	
	protected CRUD databaseCRUD = null;
	
	protected Group group = null;
	protected List<MyList> lists = null;
	protected List<Item> tempItems = null;
	protected EditText groupElement = null;
	
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
        		  
        		  Log.v(LOGTAG, "Updating group with name and ID: " + this.group.getName() + " " + this.group.getID());
        	  
        		  databaseCRUD.update_group(this.group);
        	  
        		  finish();
        	  }      	  
        	  
        	  return true;
        	  
          case R.id.discardButton:
        	  
        	  this.lists = this.databaseCRUD.query_list(group);
        	  if(lists != null)
        	  {
        		  for(MyList l: this.lists)
        		  {
        			  this.tempItems = this.databaseCRUD.query_item(l);
        			  if(tempItems != null)
        			  {
        				  for(Item i: this.tempItems)
        				  {
        					  Log.v(LOGTAG, "Deleting item with name and id: " + i.getName() + " " + i.getID());
        					  this.databaseCRUD.delete_item(i);
        				  }
        			  }
        			  else
        				  Log.v(LOGTAG, "No items detected");
        		  
        			  Log.v(LOGTAG, "Deleting list with name and id: " + l.getName() + " " + l.getID());
        		  }
        	  }
        	  else
        		  Log.v(LOGTAG, "No lists detected");
        	  
        	  Log.v(LOGTAG, "Deleting group with name and id: " + this.group.getName() + " " + this.group.getID());
        	  this.databaseCRUD.delete_group(this.group);
        	  Log.v(LOGTAG, "Deletion of group complete");
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}
