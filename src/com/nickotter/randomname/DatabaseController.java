package com.nickotter.randomname;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseController {
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
	
	public void addItem(){}
	
	public void updateItem(){}
	
	public void deleteItem(){}
	
	public void addGroup(){}
	
	public void updateGroup(){}
	
	public void deleteGroup(){}
	
	public void addList(){}
	
	public void updateList(){}
	
}
