package com.nickotter.randomname;

public class Group {
	
	private int _id = 0;
	private String name = null;
	
	public Group(int id, String text){
		this._id = id;
		this.name = text;
	}
	
	public int getID(){
		return this._id;
	}
	
	public String getName(){
		return this.name;
	}
}
