package com.nickotter.randomname;

import java.io.Serializable;

public class Item implements Serializable {
	
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
	
	public void setId(int Id) {
		this._id = Id;
	}
	
	public void setListId(int listId)
	{
		this._listID = listId;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
