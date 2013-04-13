package com.nickotter.randomname;

import java.io.Serializable;

public class MyList implements Serializable {
	private int _id;
	private String name;
	private int _groupID;
	
	public MyList(int id, int groupID, String text){
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

	public void setId(int id) {
		this._id = id;
		
	}

	public void setName(String tempName) {
		this.name = tempName;
	}
}
