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
	
	
	//technically all these are no longer needed (lines 22-49 which are the columns in strings)
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
	
	private static final String[] SHAKE_COLUMN = {
		Sqlite.SHAKE_SHIFT
	};
	
	private static final String[] VERBAL_COLUMN = {
		Sqlite.VERBAL_SHIFT
	};
	
	private static final String[] EXCLUSION_COLUMN = {
		Sqlite.EXCLUSION_SHIFT
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
		int lastId = (int) database.insert(Sqlite.DATABASE_ITEM, null, values);
		item.setId(lastId);
	}
	
	public void add_group(Group group){
		Log.v(LOGTAG, "add_group function start");
		
		Log.v(LOGTAG, "\tInitializing values");
		ContentValues values = new ContentValues();
		
		Log.v(LOGTAG, "\tAdding provided group name: " + group.getName() + " to values");
		values.put(Sqlite.GROUP_NAME, group.getName());
		
		Log.v(LOGTAG, "\tinserting values into database table=" + Sqlite.DATABASE_GROUP);
		int lastId = (int) database.insert(Sqlite.DATABASE_GROUP, null, values);
		group.setId(lastId);
		
		Log.v(LOGTAG, "\tlast insert id=" + lastId);
		
		Log.v(LOGTAG, "add_group function end");
	}
	
	public void add_list(Group group, MyList list){
		Log.v(LOGTAG, "add_list function start");
		ContentValues values = new ContentValues();
		Log.v(LOGTAG, "\tList name added: " + list.getName() + "\n\tIncluded ID: " + list.getID());
		values.put(Sqlite.LIST_NAME, list.getName());
		
		values.put(Sqlite.LIST_GROUP_ID, group.getID());
		
		Log.v(LOGTAG, "inserting with group_id=" + group.getID());
		int lastId = (int) database.insert(Sqlite.DATABASE_LIST, null, values);
		
		list.setId(lastId);
		
		Log.v(LOGTAG, "add_list function end");
	}
	
	public List<Item> query_item(MyList list){
		Log.v(LOGTAG, "query_item start");
		
		Log.v(LOGTAG, "initializing List<item>");
		List<Item> items = new ArrayList<Item>();
		
		Log.v(LOGTAG, "creating cursor from database.query()");
		//Cursor cursor = database.query(Sqlite.DATABASE_ITEM, ITEM_COLUMNS, "listID = " + list.getID(), null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT itemName, id, listID FROM itemManager WHERE listID = ?; ", new String[] { String.valueOf(list.getID()) });
		
		Log.v(LOGTAG, "checking that the cursor is not empty");
		if(cursor.getCount() > 0 ){
			
			Log.v(LOGTAG, "iterating through cursor");
			while(cursor.moveToNext()){
				
				Log.v(LOGTAG, "initializing item to be added to list. name=" + cursor.getString(cursor.getColumnIndex(Sqlite.ITEM_NAME)));
				Item item = new Item(
					cursor.getInt(cursor.getColumnIndex(Sqlite.ITEM_ID)),
					cursor.getInt(cursor.getColumnIndex(Sqlite.ITEM_LIST_ID)),
					cursor.getString(cursor.getColumnIndex(Sqlite.ITEM_NAME))
						);
				
				Log.v(LOGTAG, "Adding item to list");
				items.add(item);
				
			}
		} else {
			Log.v(LOGTAG, "\t query_item cursor is empty");
		}
		
		Log.v(LOGTAG, "query_item finish");
		return items;
		
	}
	
	public List<MyList> query_list(Group group){
		Log.v(LOGTAG, "query_list start");
		
		List<MyList> lists = new ArrayList<MyList>();
		
		//Cursor cursor = database.query(Sqlite.DATABASE_LIST, LIST_COLUMNS, "groupID = " + group.getID(), null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT listName, id, groupID FROM listManager WHERE groupID = ?; ", new String[] { String.valueOf(group.getID()) });
		
		if(cursor.getCount() > 0 ){
			Log.v(LOGTAG, "\t The cursor retrieved items");
			while(cursor.moveToNext()){
				
				MyList list = new MyList(
						cursor.getInt(cursor.getColumnIndex(Sqlite.LIST_ID)),
						cursor.getInt(cursor.getColumnIndex(Sqlite.LIST_GROUP_ID)),
						cursor.getString(cursor.getColumnIndex(Sqlite.LIST_NAME))
						);
				Log.v(LOGTAG, "\tLIST_NAME=" + list.getName());
				
				lists.add(list);
				
			}
		} else {
			Log.v(LOGTAG, "\t The cursor did not retrieve items");
		}
		
		Log.v(LOGTAG, "query_list finish");
		
		return lists;
		
	}
	
	public List<Group> query_group(){
		List<Group> groups = new ArrayList<Group>();
		
		Log.v(LOGTAG, "query_group function start");
		//Cursor cursor = database.query(Sqlite.DATABASE_GROUP, GROUP_COLUMNS, null, null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT groupName, id FROM groupManager; ", null);

		
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()){
				Log.v(LOGTAG, "Group name and id respectively: " + cursor.getString(cursor.getColumnIndex(Sqlite.GROUP_NAME)) + " && " + cursor.getInt(cursor.getColumnIndex(Sqlite.GROUP_ID)));
				Group g = new Group(cursor.getString(cursor.getColumnIndex(Sqlite.GROUP_NAME)));
				g.setId(cursor.getInt(cursor.getColumnIndex(Sqlite.GROUP_ID)));
				
				groups.add(g);
			}
		}
		
		Log.v(LOGTAG, "query_group function end");
		return groups;
	}
	
	public Group get_group(int name){
		
		Log.v(LOGTAG, "get_group start");
		
		Cursor cursor = database.rawQuery("SELECT groupName, id FROM groupManager WHERE id = ?; ", new String[] { String.valueOf(name+1)});
		cursor.moveToNext();
		
		Group g = new Group(cursor.getString(cursor.getColumnIndex(Sqlite.GROUP_NAME)));
		
		//The id isn't returned unless this next line is added
		g.setId(cursor.getInt(cursor.getColumnIndex(Sqlite.GROUP_ID)));
		
		Log.v(LOGTAG, "Fetched group with name and id: " + g.getName() + " " + g.getID());
		Log.v(LOGTAG, "get_group end");
		
		return g;
	}
	
	public MyList get_list(int listId){
		
		Log.v(LOGTAG, "get_list start");
		Cursor cursor = database.rawQuery("SELECT listName, id, groupID FROM listManager WHERE id = ?; ", new String[] { String.valueOf(listId) });
		cursor.moveToNext();
				
		MyList l = new MyList(cursor.getInt(cursor.getColumnIndex(Sqlite.LIST_ID)), cursor.getInt(cursor.getColumnIndex(Sqlite.LIST_GROUP_ID)), cursor.getString(cursor.getColumnIndex(Sqlite.LIST_NAME)));
		Log.v(LOGTAG, "Fetched List with name, group id, and id: " + l.getName() + " " + l.getGroupID() + " " + l.getID());
		Log.v(LOGTAG, "get_list end");	
		
		return l;
	}
	
	public Item get_item(int itemId)
	{
		Log.v(LOGTAG, "get_item start");
		Cursor cursor = database.rawQuery("SELECT itemName, id, listID FROM itemManager WHERE id = ?; ", new String[] { String.valueOf(itemId) });
		cursor.moveToNext();
		
		int id = cursor.getInt(cursor.getColumnIndex(Sqlite.ITEM_ID));
		Log.v(LOGTAG, "id: " + id);
		int listid = cursor.getInt(cursor.getColumnIndex(Sqlite.ITEM_LIST_ID));
		Log.v(LOGTAG, "listid: " + listid);
		String name = cursor.getString(cursor.getColumnIndex(Sqlite.ITEM_NAME));
		Log.v(LOGTAG, "Name: " + name);
		Item i = new Item(id, listid, name);		
		Log.v(LOGTAG, "Fetched Item with name, list id, and id: " + i.getName() + " " + i.getListId() + " " + i.getID());
		Log.v(LOGTAG, "get_item end");
		
		return i;
	}
	public void update_item(Item item){
		ContentValues values = new ContentValues();
		values.put(Sqlite.ITEM_NAME, item.getName());
		
		database.update(Sqlite.DATABASE_ITEM, values, Sqlite.ITEM_ID + "= ?", new String[]{
				String.valueOf(item.getID())
		});
	}
	
	public void update_group(Group group){
		ContentValues values = new ContentValues();
		values.put(Sqlite.GROUP_NAME, group.getName());
		
		database.update(Sqlite.DATABASE_GROUP, values, Sqlite.GROUP_ID + "= ?", new String[]{
				String.valueOf(group.getID())
		});
	}
	
	public void update_list(MyList list){
		ContentValues values = new ContentValues();
		values.put(Sqlite.LIST_NAME, list.getName());
		values.put(Sqlite.LIST_GROUP_ID, list.getGroupID());
		
		database.update(Sqlite.DATABASE_LIST, values, Sqlite.LIST_ID + "= ?", new String[]{
				String.valueOf(list.getID())
		});
	}
	
	public void delete_item(Item item){
		database.delete(Sqlite.DATABASE_ITEM, Sqlite.ITEM_ID + "= ? ", new String[]{
				String.valueOf(item.getID())
		});
	}
	
	public void delete_list(MyList list){
		database.delete(Sqlite.DATABASE_LIST, Sqlite.LIST_ID + "= ? ", new String[]{
				String.valueOf(list.getID())
		});
	}
	
	public void delete_group(Group group){
		database.delete(Sqlite.DATABASE_GROUP, Sqlite.GROUP_ID + "= ? ", new String[]{
				String.valueOf(group.getID())
		});
	}
	
	
	public boolean query_Shake(){
		//Cursor cursor = database.query(Sqlite.DATABASE_SHIFT, SHAKE_COLUMN, null, null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT shakeShift FROM shiftManager; ", null);
		cursor.moveToNext();
		int x = cursor.getInt(cursor.getColumnIndex(Sqlite.SHAKE_SHIFT));
		if (x == 0)
			return false;
		else
			return true;
		
		//cursor.moveToNext();
		//return cursor.getInt(cursor.getColumnIndex(Sqlite.SHAKE_SHIFT));
		
	}
	
	public boolean query_Verbal(){
		//Cursor cursor = database.query(Sqlite.DATABASE_SHIFT, VERBAL_COLUMN, null, null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT verbalShift FROM shiftManager; ", null);
		cursor.moveToNext();
		int x = cursor.getInt(cursor.getColumnIndex(Sqlite.VERBAL_SHIFT));
		if (x == 0)
			return false;
		else
			return true;
		
		//cursor.moveToNext();
		//return cursor.getInt(cursor.getColumnIndex(Sqlite.VERBAL_SHIFT));
		
	}
	
	public boolean query_Exclusion(){
		//Cursor cursor = database.query(Sqlite.DATABASE_SHIFT, EXCLUSION_COLUMN, null, null, null, null, null);
		Cursor cursor = database.rawQuery("SELECT exclusionShift FROM shiftManager; ", null);
		cursor.moveToNext();
		int x = cursor.getInt(cursor.getColumnIndex(Sqlite.EXCLUSION_SHIFT));
		if (x == 0)
			return false;
		else
			return true;
		
		//cursor.moveToNext();
		//return cursor.getInt(cursor.getColumnIndex(Sqlite.EXCLUSION_SHIFT));
		
	}
	
	public boolean toggle_Shake(){
		ContentValues values = new ContentValues();
		boolean x = query_Shake();
		
		if (x == false)
		{
			values.put(Sqlite.SHAKE_SHIFT, 1);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return false;
		}
		else
		{
			values.put(Sqlite.SHAKE_SHIFT, 0);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return true;
		}
		
	}
	
	public void toggle_Verbal(){
		ContentValues values = new ContentValues();
		boolean x = query_Verbal();
		
		if (x == false)
		{
			values.put(Sqlite.VERBAL_SHIFT, 1);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return;
		}
		else
		{
			values.put(Sqlite.VERBAL_SHIFT, 0);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return;
		}
			
	}
	
	public void toggle_Exculison(){
		ContentValues values = new ContentValues();
		boolean x = query_Exclusion();
		
		if (x == false)
		{
			values.put(Sqlite.EXCLUSION_SHIFT, 1);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return;
		}
		else //true
		{
			values.put(Sqlite.EXCLUSION_SHIFT, 0);
			database.update(Sqlite.DATABASE_SHIFT, values, Sqlite.SHIFT_ID + "= ? ", new String[]{ String.valueOf(1) });
			return;
		}
		
	}
	
	
	public void initExtraFunctions(){
		Log.v(LOGTAG, "Settings init");
		ContentValues values = new ContentValues();
		values.put(Sqlite.SHIFT_ID, 1);
		values.put(Sqlite.EXCLUSION_SHIFT, 0);
		values.put(Sqlite.SHAKE_SHIFT, 0);
		values.put(Sqlite.VERBAL_SHIFT, 0);
		
		database.insert(Sqlite.DATABASE_SHIFT, null, values);
		Log.v(LOGTAG, "Settings sucesfullly initiated");
	}

}
