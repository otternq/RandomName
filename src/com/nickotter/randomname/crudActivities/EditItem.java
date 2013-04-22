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
import com.nickotter.randomname.Item;
import com.nickotter.randomname.MyList;
import com.nickotter.randomname.R;

public class EditItem extends SherlockFragmentActivity {
	
	final String LOGTAG = "EditItemActivity";
	
	protected CRUD databaseCRUD = null;
	
	protected int currentListIndex = -1;
	
	protected List<MyList> lists = null;
	protected MyList list = null;
	protected Item item = null;
	
	protected EditText itemField = null;
	
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
		setContentView(R.layout.edit_item);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        Log.v(LOGTAG, "\taction bar has home enabled");
        
        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();
        
        //get Extras from intent        
        int groupId = getIntent().getIntExtra("groupId",-1);
        Log.v(LOGTAG, "groupId after getIntent" + groupId);
        if(groupId == -1)
        	groupId = 0;
        
        int listId = getIntent().getIntExtra("listId",-1);
        Log.v(LOGTAG, "listId after getIntent" + listId);
        if(listId == -1)
        	listId = 1;
        
        int itemId = getIntent().getIntExtra("itemId", -1);
        Log.v(LOGTAG, "itemId after getIntent" + itemId);
        if(itemId == -1)
        	itemId = 1;        
        
        
        
        Group g = this.databaseCRUD.get_group(groupId);
        //List spinner setup
        Log.v(LOGTAG, "\tquery for lists");
        this.lists = this.databaseCRUD.query_list(g);
        
        //this needs to be changed to a custom adapter
        Log.v(LOGTAG, "\tinitalizing a lists list");
        List<String> tempList = new ArrayList<String>();
        
        int i = 0;
        for (MyList list : this.lists)
        {
        	if (list.getID() == getIntent().getIntExtra("listId", -1)) 
        	{
        		this.currentListIndex = i;
        	}
        	tempList.add(list.getName());
        	i++;
        }
        
        Log.v(LOGTAG, "\tinitalizing spinner objet from layout");
        Spinner spinner = (Spinner) findViewById(R.id.CRUDlistSpinner);
        Log.v(LOGTAG, "\tarray adapter for temp list list");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempList);
        Log.v(LOGTAG, "\tsetting array adapter");
        spinner.setAdapter(spinnerArrayAdapter); 
        //I think this is going to fail as list id's are accumulated over groups
        spinner.setSelection(currentListIndex);
      
        //List edit text field
        this.item = this.databaseCRUD.get_item(itemId);
        
        this.itemField = (EditText)findViewById(R.id.editItemName);
        this.itemField.setText(this.item.getName());
            
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
        	  if(this.itemField == null)
        	  {
        		  Log.v(LOGTAG, "Error: list was blank");    	
        		  finish();
        	  }
        	  
        	  //list check + return
        	  Spinner spinner = (Spinner) findViewById(R.id.CRUDlistSpinner);
        	  int selectedListIndex = spinner.getSelectedItemPosition();
        	  
        	  
        	  MyList list = this.lists.get(selectedListIndex);
        	  Log.v(LOGTAG, "A list was selected with name and ID: " + list.getName() + " " + list.getID());
        	  
        	  this.item.setListId(list.getID());
        	  this.item.setName(this.itemField.getText().toString());
    		  
        	  this.databaseCRUD.update_item(this.item);
        	  finish();     	  
        	  
        	  return true;
        	  
          case R.id.discardButton:
        	  Log.v(LOGTAG, "Deleting item with name and id: " + this.item.getName() + " " + this.item.getID());
        	  this.databaseCRUD.delete_item(this.item);
        	  Log.v(LOGTAG, "Deletion of item complete");
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}
