package com.nickotter.randomname;

public class Group {
	
	private int _id;
	private String name;
	
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
