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
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.Group;
import com.nickotter.randomname.R;

public class EditGroup  extends SherlockFragmentActivity {
	
	final String LOGTAG = "EditGroupActivity";
	
	protected CRUD databaseCRUD = null;
	
	protected Group group = null;
	protected EditText groupElement = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(LOGTAG, "onCreate start");
		setContentView(R.layout.edit_group);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        Log.v(LOGTAG, "\taction bar has home enabled");
        
        this.groupElement = (EditText)findViewById(R.id.groupName);
        
        //setting the groupId from the provided by intent
        int groupId = getIntent().getIntExtra("groupId",-1);
        
        this.group = databaseCRUD.get_group(groupId);
        this.groupElement.setText(this.group.getName());
        
        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();
        
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
