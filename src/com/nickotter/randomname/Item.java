package com.nickotter.randomname;

public class Item {
	
	private long _id;
	private int _listID;
	private String name;
	
	public Item(long id, int listID, String text){
		this._id = id;
		this._listID = listID;
		this.name = text;
	}
	
	public long getID(){
		return this._id;
	}
	
	public int getListId(){
		return this._listID;
	}
	
	public String getName(){
		return this.name;
	}
}
