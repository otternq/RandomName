//Long Nguyen
//CS480
//Sqlite

package com.nickotter.randomname;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
	
	//Database version
	
	private static final int DATABASE_ITEM_VERSION = 1;
	
	private static final String DATABASE_NAME = "group.db";
	
	//Database name for groups, lists, items
	public static final String DATABASE_ITEM = "itemManager";
	
	public static final String DATABASE_GROUP = "groupManager";
	
	public static final String DATABASE_LIST = "listManager";
	
	public static final String ITEM_ID = "id";
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_LIST_ID = "listID";

	public static final String	GROUP_ID = "id";
	public static final String GROUP_NAME = "groupName";
	
	public static final String	LIST_ID = "id";
	public static final String LIST_NAME = "listName";
	public static final String LIST_GROUP_ID = "groupID";
	
	
	
	
	public Sqlite(Context context){
		super(context, DATABASE_NAME, null, DATABASE_ITEM_VERSION);
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
		
		String CREATE_ITEM_TABLE = "CREATE TABLE " + DATABASE_ITEM + "("
	            + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEM_NAME + " TEXT" + ")";
	    
	    String CREATE_GROUP_TABLE = "CREATE TABLE " + DATABASE_GROUP + "("
	    		+ GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + GROUP_NAME + " TEXT" + ")";
	    
	    String CREATE_LIST_TABLE = "CREATE TABLE " + DATABASE_LIST + "("
	    		+ LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
	    		+ LIST_GROUP_ID + " INTEGER," 
	    		+ LIST_NAME + " TEXT" + ")";
        
        db.execSQL(CREATE_ITEM_TABLE);
        
        db.execSQL(CREATE_GROUP_TABLE);
        
        db.execSQL(CREATE_LIST_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ITEM);
        onCreate(db);
	}
	
	
}
