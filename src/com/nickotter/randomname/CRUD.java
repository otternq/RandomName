package com.nickotter.randomname;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CRUD {
	
	private String LOGTAG = "CRUD";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] GROUP_COLUMNS = {
		Sqlite.GROUP_ID,
		Sqlite.GROUP_NAME
	};
	
	private static final String[] ITEM_COLUMNS = {
		Sqlite.ITEM_ID,
		Sqlite.ITEM_LIST_ID,
		Sqlite.ITEM_NAME
	};
	
	private static final String[] LIST_COLUMNS = {
		Sqlite.LIST_GROUP_ID,
		Sqlite.LIST_ID,
		Sqlite.LIST_NAME
	};

	public CRUD(Context context){
		dbhelper = new Sqlite(context);
	}
	
	public void open(){
		database = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		dbhelper.close();
	}
	
	public void add_item(MyList list, Item item){
		ContentValues values = new ContentValues();
		values.put(Sqlite.ITEM_NAME, item.getName());
		values.put(Sqlite.ITEM_LIST_ID, list.getID());
		database.insert(Sqlite.DATABASE_ITEM, null, values);
	}
	
	public void add_group(Group group){
		Log.v(LOGTAG, "add_group e");
		
		Log.v(LOGTAG, "\tinitializing values");
		ContentValues values = new ContentValues();
		
		Log.v(LOGTAG, "\tadding provided group name to values");
		values.put(Sqlite.GROUP_NAME, group.getName());
		
		Log.v(LOGTAG, "\tinserting values into database table=" + Sqlite.DATABASE_GROUP);
		database.insert(Sqlite.DATABASE_GROUP, null, values);
		
		Log.v(LOGTAG, "add_group x");
	}
	
	public void add_list(Group group, MyList list){
		ContentValues values = new ContentValues();
		values.put(Sqlite.LIST_NAME, list.getName());
		values.put(Sqlite.LIST_GROUP_ID, group.getID());
		database.insert(Sqlite.DATABASE_LIST, null, values);
		
	}
	
	public List<Item> query_item(MyList list){
		List<Item> items = new ArrayList<Item>();
		
		Cursor cursor = database.query(Sqlite.DATABASE_ITEM, ITEM_COLUMNS, null, null, null, null, null);
		
		if(cursor.getCount() > 0 ){
			while(cursor.moveToNext()){
				if(cursor.getColumnIndex(Sqlite.ITEM_LIST_ID) == list.getID()){
					Item item = new Item(
						cursor.getColumnIndex(Sqlite.ITEM_ID),
						cursor.getColumnIndex(Sqlite.ITEM_LIST_ID),
						cursor.getString(cursor.getColumnIndex(Sqlite.ITEM_NAME))
							);
					
					items.add(item);
				}
			}
		}
		
		return items;
		
	}
	
	public List<MyList> query_list(Group group){
		List<MyList> lists = new ArrayList<MyList>();
		
		Cursor cursor = database.query(Sqlite.DATABASE_LIST, LIST_COLUMNS, null, null, null, null, null);
		
		if(cursor.getCount() > 0 ){
			while(cursor.moveToNext()){
				if(cursor.getColumnIndex(Sqlite.LIST_GROUP_ID) == group.getID()){
					MyList list = new MyList(
							cursor.getColumnIndex(Sqlite.LIST_ID),
							cursor.getColumnIndex(Sqlite.LIST_GROUP_ID),
							cursor.getString(cursor.getColumnIndex(Sqlite.LIST_NAME))
							);
					
					lists.add(list);
				}
			}
		}
		
		return lists;
	}
	
	public List<Group> query_group(){
		List<Group> groups = new ArrayList<Group>();
		
		Cursor cursor = database.query(Sqlite.DATABASE_GROUP, GROUP_COLUMNS, null, null, null, null, null);
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				Group g = new Group(cursor.getColumnIndex(Sqlite.GROUP_ID), 
									cursor.getString(cursor.getColumnIndex(Sqlite.GROUP_ID)));
				
				groups.add(g);
			}
		}
		return groups;
	}
	
	public void update_item(){
		
	}
	
	public void update_group(){
		
	}
	
	public void update_list(){
		
	}
	
	public void delete_item(){
		
	}
	
	public void delete_list(){
		
	}
	
	public void delete_group(){
		
	}

}
