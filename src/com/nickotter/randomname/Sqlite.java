//Long Nguyen
//CS480
//Sqlite

package com.nickotter.randomname;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
	
	//Database version
	
	private static final int DATABASE_ITEM_VERSION = 1;
	
	private static final String DATABASE_NAME = "data.db";
	
	//Database name for groups, lists, items
	private static final String DATABASE_ITEM = "itemManager";
	
	private static final String DATABASE_GROUP = "groupManager";
	
	private static final String DATABASE_LIST = "listManager";
	
	private static final String ITEM_ID = "id";
	private static final String ITEM_NAME = "name";

	private static final String	GROUP_ID = "id";
	private static final String GROUP_NAME = "name";
	
	private static final String	LIST_ID = "id";
	private static final String LIST_NAME = "name";
	
	
	String CREATE_ITEM_TABLE = "CREATE TABLE " + DATABASE_ITEM + "("
            + ITEM_ID + " INTEGER PRIMARY KEY," + ITEM_NAME + " TEXT," + ")";
    
    String CREATE_GROUP_TABLE = "CREATE TABLE " + DATABASE_GROUP + "("
    		+ GROUP_ID + " INTEGER PRIMARY KEY," + GROUP_NAME + " TEXT," + ")";
    
    String CREATE_LIST_TABLE = "CREATE TABLE " + DATABASE_LIST + "("
    		+ LIST_ID + " INTEGER PRIMARY KEY," + LIST_NAME + " TEXT," + ")";
	
	public Sqlite(Context context){
		super(context, DATABASE_NAME, null, DATABASE_ITEM_VERSION);
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(CREATE_ITEM_TABLE);
        
      //  db.execSQL(CREATE_GROUP_TABLE);
        
       // db.execSQL(CREATE_LIST_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ITEM);
 
        // Create tables again
        onCreate(db);
		
	}
	
	
	public void addItem(int id, String text){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues setupValues = createContentValue(id, text);
		db.insert(DATABASE_ITEM, null, setupValues);
		db.close();
	}
	
	public ContentValues createContentValue(int id, String text){
		ContentValues values = new ContentValues();
		values.put(ITEM_ID, id);
		values.put(ITEM_NAME, text);
		return values;
	}
		
	
	
	
}
