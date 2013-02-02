package com.nickotter.randomname;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseController {
	
	private static final String ITEM_ID = "id";
	private static final String ITEM_NAME = "name";
	private static final String DATABASE_ITEM = "itemManager";
	
	private Context context;
	private SQLiteDatabase database;
	private Sqlite sqLiteHelper;
	
	
	public DatabaseController(Context context){
		this.context = context;
	}
	
	public DatabaseController open() throws SQLiteException{
		sqLiteHelper = new Sqlite(context);
		database = sqLiteHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		sqLiteHelper.close();
	}
	
	public ContentValues createContentValue(int id, String text){
		ContentValues values = new ContentValues();
		values.put(ITEM_ID, id);
		values.put(ITEM_NAME, text);
		return values;
	}
	
	public void addItem(int id, String text){
		ContentValues setupValues = createContentValue(id, text);
		database.insert(DATABASE_ITEM, null, setupValues);
	}
	
	public void updateItem(int id, String text){
		ContentValues updateValues = createContentValue(id, text);
		database.update(DATABASE_ITEM, updateValues, ITEM_ID + "=" + id, null);
	}
	
	public void deleteItem(){}
	
	public void addGroup(){}
	
	public void updateGroup(){}
	
	public void deleteGroup(){}
	
	public void addList(){}
	
	public void updateList(){}
	
}
