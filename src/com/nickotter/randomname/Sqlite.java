package com.nickotter.randomname;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlite extends SQLiteOpenHelper {
	
	//Database name for items
	private static final String DATABASE_ITEM = "itemManager";
	private static final int DATABASE_ITEM_VERSION = 1;
	
	//Database name for groups
	private static final String DATABASE_GROUP = "groupManager";
	
	private static final String ITEM_ID = "id";
	private static final String ITEM_NAME = "name";

	private static final String	GROUP_ID = "id";
	private static final String GROUP_NAME = "name";
	
	public Sqlite(Context context){
		super(context, DATABASE_ITEM, null, DATABASE_ITEM_VERSION);
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + DATABASE_ITEM + "("
                + ITEM_ID + " INTEGER PRIMARY KEY," + ITEM_NAME + " TEXT," + ")";
        db.execSQL(CREATE_ITEM_TABLE);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ITEM);
 
        // Create tables again
        onCreate(db);
		
	}
	
}
