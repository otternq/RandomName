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
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.Group;
import com.nickotter.randomname.Item;
import com.nickotter.randomname.MyList;
import com.nickotter.randomname.R;

public class AddItem extends SherlockFragmentActivity {

	final String LOGTAG = "AddListActivity";
	
	protected CRUD databaseCRUD = null;
	protected List<MyList> lists = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOGTAG, "onCreate start");
        
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		
		ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        
        Log.v(LOGTAG, "\taction bar has home enabled");

        Log.v(LOGTAG, "\topening database connection");
        this.databaseCRUD = new CRUD(this);
        this.databaseCRUD.open();     
        
        Log.v(LOGTAG, "\tquery for lists");
        String groupName = getIntent().getStringExtra("group");
        Group g = this.databaseCRUD.get_group(groupName);
        this.lists = this.databaseCRUD.query_list(g);
        
        Log.v(LOGTAG, "\tinitalizing a list list");
        List<String> tempList = new ArrayList<String>();
        
        for (MyList list : this.lists)
        {
        	tempList.add(list.getName());
        }
        
        Log.v(LOGTAG, "\tinitalizing spinner objet from layout");
        Spinner spinner = (Spinner) findViewById(R.id.CRUDlistSpinner);
        
        Log.v(LOGTAG, "\tarray adapter for temp group list");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tempList);
        
        Log.v(LOGTAG, "\tsetting array adapter");
        spinner.setAdapter(spinnerArrayAdapter);
        
        Log.v(LOGTAG, "onCreate end");
        
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
        	  
        	  Log.v(LOGTAG, "Submit start");
        	  //Item check
        	  EditText itemField = (EditText)findViewById(R.id.itemName);
        	  if(itemField == null)
        	  {
        		  Log.v(LOGTAG, "Error: item was blank");    	
        		  finish();
        	  }
        	  
        	  //list check + return
        	  Spinner spinner = (Spinner)findViewById(R.id.CRUDlistSpinner);
        	  int selectedList = spinner.getSelectedItemPosition();
        	  
        	  /*Old method (non-spinner)
        	  EditText listField = (EditText)findViewById(R.id.listName);
        	  Log.v(LOGTAG, "Retreived list string == " + listField.getText().toString());
        	  MyList list = databaseCRUD.get_list(listField.getText().toString());
        	  
        	  if(list == null)
        	  {
        		  Log.v(LOGTAG, "No list found throwing error and finishing");
        		  finish();
        	  }
        	  else 
        	  {
        		  Log.v(LOGTAG, "A list was selected with name and ID: " + list.getName() + " " + list.getID());
        		  Item i1 = new Item(0, list.getID(), itemField.getText().toString());
        		  databaseCRUD.add_item(list, i1);
        	  }
        	  */
        	  
        	  MyList list = this.lists.get(selectedList);
        	  Log.v(LOGTAG, "A list was selected with name and ID: " + list.getName() + " " + list.getID());
    		  Item i1 = new Item(0, list.getID(), itemField.getText().toString());
    		  databaseCRUD.add_item(list, i1);
        	  
        	  
        	  //finish
        	  Log.v(LOGTAG, "New list entry has id=" + i1.getID());
        	  
        	  finish();
        	  return true;
        	  
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }

}