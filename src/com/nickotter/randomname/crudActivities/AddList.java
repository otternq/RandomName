package com.nickotter.randomname.crudActivities;

import java.util.HashMap;
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
import com.nickotter.randomname.MyList;
import com.nickotter.randomname.R;
import com.nickotter.randomname.R.layout;
import com.nickotter.randomname.R.menu;

public class AddList extends SherlockFragmentActivity {

	final String LOGTAG = "DrawerTestMainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_list);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
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
        	  CRUD databaseCRUD = new CRUD(this);
        	  databaseCRUD.open();
        	  
        	  //list check
        	  EditText listField = (EditText)findViewById(R.id.listName);
        	  if(listField == null)
        	  {
        		  Log.v(LOGTAG, "Error: list must not be blank");    	
        		  finish();
        	  }
        	  
        	  //group check + return
        	  EditText groupField = (EditText)findViewById(R.id.groupName);
        	  Group group = databaseCRUD.get_group(groupField.getText().toString());
        	  
        	  if(group == null)
        	  {
        		  Group gdefault = databaseCRUD.get_group("default");
        		  MyList l1 = new MyList(0, gdefault.getID(), listField.getText().toString());
        	  }
        	  else
        		  MyList l1 = new MyList(0, group.getID(), listField.getText().toString());
        		  
        		  
        		  
    		  //Log.v(LOGTAG, "Fetching Groups for comparison");
      
      		  //MyList l1 = new MyList(0, group.getID(), "List 1");
        	  
        	  //databaseCRUD.add_group(g1);
        	  databaseCRUD.close();
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}