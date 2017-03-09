package com.shenkar.search;

import java.util.ArrayList;

//The structure of a line in the indexFile
public class indexObejct 
{
	
	public	String 		   		name ;
	public	int 		   		hits;
	public  int 				id;
	public  int 				post_hits ;
	public  ArrayList<Offset>   offsetArray ;


		indexObejct(){hits=0; offsetArray = new ArrayList<Offset>(); post_hits=1;id=0;}

 
		public String toString(){
			return "word: "+name+" hits: "+hits+" id: "+id +"\n";
		}
		public int get_post_hits(){
			return post_hits;
		}

}
