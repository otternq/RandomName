//Long Nguyen
//CS480
//Sqlite

package com.nickotter.randomname;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
	
	//Database name for items
	
	private static final int DATABASE_ITEM_VERSION = 1;
	
	//Database name for groups
	private static final String DATABASE_ITEM = "itemManager";
	
	private static final String DATABASE_GROUP = "groupManager";
	
	private static final String DATABASE_LIST = "listManager";
	
	private static final String ITEM_ID = "id";
	private static final String ITEM_NAME = "name";

	private static final String	GROUP_ID = "id";
	private static final String GROUP_NAME = "name";
	
	private static final String	LIST_ID = "id";
	private static final String LIST_NAME = "name";
	
	public Sqlite(Context context){
		super(context, DATABASE_ITEM, null, DATABASE_ITEM_VERSION);
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + DATABASE_ITEM + "("
                + ITEM_ID + " INTEGER PRIMARY KEY," + ITEM_NAME + " TEXT," + ")";
        
        String CREATE_GROUP_TABLE = "CREATE TABLE " + DATABASE_GROUP + "("
        		+ GROUP_ID + " INTEGER PRIMARY KEY," + GROUP_NAME + " TEXT," + ")";
        
        String CREATE_LIST_TABLE = "CREATE TABLE " + DATABASE_LIST + "("
        		+ LIST_ID + " INTEGER PRIMARY KEY," + LIST_NAME + " TEXT," + ")";
        
        db.execSQL(CREATE_ITEM_TABLE);
        
        db.execSQL(CREATE_GROUP_TABLE);
        
        db.execSQL(CREATE_LIST_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ITEM);
 
        // Create tables again
        onCreate(db);
		
	}
	
	
	/**
	���* All CRUD(Create, Read, Update, Delete) Operations
	*/
	
	public void addItem(){}
	
	public void updateItem(){}
	
	public void deleteItem(){}
	
	public void addGroup(){}
	
	public void updateGroup(){}
	
	public void deleteGroup(){}
	
	public void addList(){}
	
	public void updateList(){}
	
	
	
}
