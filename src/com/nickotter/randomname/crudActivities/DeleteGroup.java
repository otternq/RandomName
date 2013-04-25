package com.nickotter.randomname.crudActivities;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.nickotter.randomname.CRUD;
import com.nickotter.randomname.Group;


public class DeleteGroup extends SherlockActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String LOGTAG = "DeleteGroup Activity";
		
		CRUD databaseCRUD = new CRUD(this);
		databaseCRUD.open();
		
		Log.v(LOGTAG, "openning database connection");
		databaseCRUD.open();
		
		int groupID = getIntent().getIntExtra("groupId", -1);
        Log.v(LOGTAG, "groupId after getIntent " + groupID);
        
        if (groupID == -1) {
        	finish();
        }
        
        Group group = databaseCRUD.get_group(groupID);
		
		Log.v(LOGTAG, "deleting group");
		databaseCRUD.delete_group(group);
		
		databaseCRUD.close();
		
		finish();
	}
	
	

}
