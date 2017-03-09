package com.shenkar.search;
/*
 * The structure of the search results
 */
public class Summary {
	
	public int 	  id;
	public String author;
	public String date;
	public String summary;
	
	Summary(){}
	public String toString(){
		//"<HTML>Ram<BR>Sita"
		return "<HTML>Id: "+id+"<BR>Author: "+author+"<BR>Date: "+date+"<BR>"+summary+"<BR>";
	}
	
}
