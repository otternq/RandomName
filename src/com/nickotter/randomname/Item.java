package com.nickotter.randomname;

public class Item {
	
	private int _id;
	private int _listID;
	private String name;
	
	public Item(int id, int listID, String text){
		this._id = id;
		this._listID = listID;
		this.name = text;
	}
	
	public int getID(){
		return this._id;
	}
	
	public int getListId(){
		return this._listID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setId(int id) {
		this._id = id;
	}
}
