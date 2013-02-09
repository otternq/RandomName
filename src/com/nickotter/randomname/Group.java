package com.nickotter.randomname;

public class Group {
	
	private int _id = 0;
	private String name = null;
	
	public Group(String text) {
		this.name = text;
	}
	
	public int getID(){
		return this._id;
	}
	
	public String getName(){
		return this.name;
	}

	public void setId(int columnIndex) {
		this._id = columnIndex;
		
	}
}
