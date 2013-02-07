package com.nickotter.randomname;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CRUD {
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public CRUD(Context context){
		dbhelper = new Sqlite(context);
	}
	
	public void open(){
		database = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		dbhelper.close();
	}
	
	public void add_item(Item item){
		ContentValues values = new ContentValues();
		values.put(Sqlite.ITEM_NAME, item.getText());
		database.insert(Sqlite.DATABASE_ITEM, null, values);
	}
	
	public void add_group(Group group){
		ContentValues values = new ContentValues();
		values.put(Sqlite.GROUP_NAME, group.getName());
		database.insert(Sqlite.DATABASE_GROUP, null, values);
		
	}
	
	public void add_list(List list){
		
		
	}

}
