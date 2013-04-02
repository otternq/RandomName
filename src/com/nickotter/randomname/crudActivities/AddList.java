package com.nickotter.randomname.crudActivities;

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

public class AddList extends SherlockFragmentActivity {

	final String LOGTAG = "AddListActivity";
	
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
        		  Log.v(LOGTAG, "Error: list was blank");    	
        		  finish();
        	  }
        	  
        	  //group check + return
        	  EditText groupField = (EditText)findViewById(R.id.groupName);
        	  Group group = databaseCRUD.get_group(groupField.getText().toString());
        	  
        	  if(group == null)
        	  {
        		  Log.v(LOGTAG, "No group entered setting to default");
        		  Group gdefault = databaseCRUD.get_group("default");
        		  MyList l1 = new MyList(0, gdefault.getID(), listField.getText().toString());
        		  databaseCRUD.add_list(gdefault, l1);
        	  }
        	  else 
        	  {
        		  Log.v(LOGTAG, "A group was selected with name and ID: " + group.getName() + " " + group.getID());
        		  MyList l1 = new MyList(0, group.getID(), listField.getText().toString());
        		  databaseCRUD.add_list(group, l1);
        	  }

        	  
        	  //finish
        	  Log.v(LOGTAG, "New list entry sucessful");
        	  databaseCRUD.close();
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}