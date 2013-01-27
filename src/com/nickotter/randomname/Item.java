package com.nickotter.randomname;

public class Item {
	
	private int _id;
	private int _listID;
	private String text;
	
	public Item(int id, int listID, String text){
		this._id = id;
		this._listID = listID;
		this.text = text;
	}
	
	public int getID(){
		return this._id;
	}
	
	public int getListId(){
		return this._listID;
	}
	
	public String getText(){
		return this.text;
	}
}
