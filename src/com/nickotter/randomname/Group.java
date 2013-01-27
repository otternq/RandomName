package com.nickotter.randomname;

public class Group {
	
	private int _id;
	private String text;
	
	public Group(int id, String text){
		this._id = id;
		this.text = text;
	}
	
	public int getID(){
		return this._id;
	}
	
	public String getText(){
		return this.text;
	}
}
