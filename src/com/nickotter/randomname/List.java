package com.nickotter.randomname;

public class List {
	private int _id;
	private String name;
	private int _groupID;
	
	public List(int id, int groupID, String text){
		this._id = id;
		this.name = text;
		this._groupID = groupID;
	}
	
	public int getID(){
		return this._id;
	}
	
	public int getGroupID(){
		return this._groupID;
	}
	
	public String getName(){
		return this.name;
	}
}
