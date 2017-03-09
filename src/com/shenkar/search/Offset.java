package com.shenkar.search;

//creating a structure for the id and the number of times a word exist in a file
public	class Offset{
	
	public int postid;
	public int hit_number;
	
	public Offset(int _id, int _hit){
		
		postid=_id;
		hit_number = _hit;
		
	}
	public String toString(){
		return postid+" , "+ hit_number;
	}
	
}
