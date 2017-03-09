package com.shenkar.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SearchResult {
	
 public  ArrayList<String> 				   queryArray ;
 public  String			   				   query	  ; 
 public  ArrayList<String> 			       Operator	  ;
 
	
	//Main logic for the search. it uses the index.txt and posting.txt files created earlier an searches them.
 	//it takes to consideration
 	SearchResult(String q)
 	{
    Operator=new ArrayList<String>();
 	query=q;
 	queryArray =new ArrayList<String>();
 	Breakquery();

 	}
 	
	public void Breakquery(){
 		
 		Scanner input = null ;
 		String w;
 		input =  new Scanner(query);
 		while(input.hasNext())
 		{
 			w=input.next();
 			if(w.equals("AND") || w.equals("OR") || (w.equals("NOT"))){
 				Operator.add(w);
 			}
 			else{
 			queryArray.add(w.toLowerCase());
 			}
 		}
 	 
 		input.close();
 	}
	
 	public  ArrayList<String> RemoveNot( )
 	{
 		ArrayList<String> Que = new ArrayList<String>();	
 		Scanner input = null ;
 	 
 		input =  new Scanner(query);
 		while(input.hasNext())
 		{
 			Que.add(input.next().replaceAll("[^a-zA-Z0-9]",""));
 		}
 		input.close();
 		ArrayList<String> temp = new ArrayList<String>();	
 		ArrayList<String> not_words = new ArrayList<String>();	
 		ArrayList<Integer> count_not_words = new ArrayList<Integer>();	
 	 
 		 
 		for(int i=0;i<Que.size();i++)
 		{
 			if(Que.get(i).equals("AND"))
 			{
 				temp.add(Que.get(i+1).toLowerCase());
 				temp.add(Que.get(i-1).toLowerCase());
 				
 			}
 			if(Que.get(i).equals("NOT"))
 			{
 				temp.add(Que.get(i-1).toLowerCase());
 				not_words.add(Que.get(i+1).toLowerCase());
 			}
 			
 			if(Que.get(i).equals("OR"))
 			{
 				temp.add(Que.get(i+1).toLowerCase());
 				temp.add(Que.get(i-1).toLowerCase());
 			}
 			
 		}
 	 
 		
 		for(int i=0;i<temp.size();i++)
 		{
 			for(int j=0;j<not_words.size();j++){
 				
 				if(temp.get(i).equals(not_words.get(j))){
 				 
 					count_not_words.add(i);
 				}
 			}
 		}
 		
  
 		for(int i=0;i<count_not_words.size();i++)
 		{
 			
 			int num =count_not_words.get(i);
 		
 			temp.remove(num);
 		}
 		
 		 
 		 Set<String> hashset = new HashSet<String>(temp);
 		 temp = new ArrayList<String>(hashset);
 		
 		
 		System.out.println("temp: "+temp);
 		return temp;
 	}
 	

 	public  ArrayList<Integer> GetResultForSingle(String a ,indexFile in )
 	{
 		ArrayList<Integer> temp = new ArrayList<Integer>();	
 		for(int i=0;i<in.DictionaryArray.size();i++){
 			
 			if( in.DictionaryArray.get(i).name.equals(a) )
 			{
 				for(int j=0;j<in.DictionaryArray.get(i).get_post_hits() ;j++){
 					
 					temp.add(in.DictionaryArray.get(i).offsetArray.get(j).postid );
 				}
 				
 			}
 		}
 		
 		return temp;
 	}
 	
 	public ArrayList<Integer> GiveMeResult(indexFile in){
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		int counter=2;
 		boolean flag=false;
 		
 		for(int i=0;i<Operator.size();i++)
 		{
 			
 			if(Operator.get(i).equals("AND"))
 			{
 				if(Result.isEmpty() && flag==false ){
 					
 					Result = And(GetResultForSingle(queryArray.get(0),in) ,
 									 GetResultForSingle(queryArray.get(1),in) 
 									 , in);
 					flag=true;
 					
 					}else{
 					
 					Result = And(Result,GetResultForSingle(queryArray.get(counter),in),in);
 					counter++;
 					}
 				
 			}
 			if(Operator.get(i).equals("NOT") )
 			{
 				if(Result.isEmpty()&& flag==false  )
 				{
 					Result = Not(GetResultForSingle(queryArray.get(0),in) ,
						 GetResultForSingle(queryArray.get(1),in) 
						 , in);
	
 					
 					flag=true;
 				 
 				}else{
 					Result = Not(Result,GetResultForSingle(queryArray.get(counter),in),in);
 					counter++;
 					
 				}
 				
 			}

 			
 			if(Operator.get(i).equals("OR") ){
 				
 				if(Result.isEmpty() &&  flag==false)
 				{
 					Result = Or(GetResultForSingle(queryArray.get(0),in) ,
							 GetResultForSingle(queryArray.get(1),in) 
							 , in);
 					
 					flag=true;
 				}else{
 					 
 					Result =Or(Result,GetResultForSingle(queryArray.get(2),in),in);
 				 
 					counter++;
 					
 				}
 			}
 		}
 		
 		return Result;
 	}
 	
 	
 	
 	//NOT
 	public ArrayList<Integer> Not (ArrayList<Integer> NOT_a , ArrayList<Integer> NOT_b , indexFile in )
 	{
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		
 		if(NOT_a.size()>NOT_b.size()){
 			for(int i=0;i<NOT_a.size();i++)
 			{
 	 			
 	 			for(int j = 0; j < NOT_b.size();j++)
 	 			{
 	 				
 	 				if(NOT_a.get(i)== NOT_b.get(j))
 	 				{
 	 					NOT_a.remove(i);
 	 				}
 	 				 
 	 			}
 	 			}
 			}else{
 				for(int i=0;i<NOT_b.size();i++)
 	 			{
 	 	 			
 	 	 			for(int j = 0; j < NOT_a.size();j++)
 	 	 			{
 	 	 				
 	 	 				if(NOT_a.get(i)== NOT_b.get(j))
 	 	 				{
 	 	 					NOT_a.remove(i);
 	 	 				}
 	 	 				 
 	 	 			}
 	 	 			}
 			}
 			for(int i=0;i<NOT_a.size();i++){
 				
 				Result.add(NOT_a.get(i));
 			}
 		
 		
 		return Result;
 		
 	}
 	
 	
 	//AND
 	public ArrayList<Integer> And (ArrayList<Integer> AND_a , ArrayList<Integer> AND_b , indexFile in )
 	{
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 			for(int i=0;i<AND_a.size();i++){
 			
 			for(int j = 0; j < AND_b.size();j++){
 				
 				if(AND_a.get(i) == AND_b.get(j)){
 					Result.add(AND_a.get(i));
 				}
 			}
 			}

 		return Result;
 	}
 	
 	
 	//Or
 	public ArrayList<Integer> Or (ArrayList<Integer> OR_a , ArrayList<Integer> OR_b , indexFile in )
 	{
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		System.out.println(OR_a);
 		for(int i=0;i<OR_a.size();i++){
				if(Result.contains(OR_a.get(i))){}
				else{
					Result.add(OR_a.get(i));
				}
			}
			for(int i=0;i<OR_b.size();i++){
				if(Result.contains(OR_b.get(i))){}
				else{
					Result.add(OR_b.get(i));
				}
			}
			
 		return Result;
 	}
 	
 	
	//()
 	public ArrayList<Integer> Brackets( indexFile in)
 	{
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		
 		
 		
 		for(int i=0;i<queryArray.size();i++)
 		{
 			if(queryArray.get(i).contains("("))
 			{
 				String a = queryArray.get(i).replaceAll("[^a-zA-Z0-9]","");
 				queryArray.remove(i);
 				queryArray.add(i, a);
 			}
 		
 			if(queryArray.get(i).contains(")"))
 			{
 				String a = queryArray.get(i).replaceAll("[^a-zA-Z0-9]","");
 				queryArray.remove(i);
 				queryArray.add(i, a);
 			}
 		}
 		
 		Result = GiveMeResult(in);
  
 		return Result;	
 	}
 
 	//גרשיים
 	public ArrayList<Integer>  Quotes(indexFile in)  
 	{
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		ArrayList<String> file_array = new ArrayList<String>();
 		
 		for(int i=0;i<queryArray.size();i++)
 		{
 			
 			if(queryArray.get(i).contains("\""))
 			{
 				String a = queryArray.get(i).replaceAll("[^a-zA-Z0-9]","");
 				queryArray.remove(i);
 				queryArray.add(i, a);
 			}
 		}
 		
 		  int count=0;
 		  Scanner input = null ;
 		  
		  for (int i = 0; i < in.listofFiles.size(); i++) 
		  {
			  count=0;
			  File file = new File(in.listofFiles.get(i).getPath());

			  try {
				input =  new Scanner(file);
				while(input.hasNext() ) 
		    	{
					file_array.add(input.next().replaceAll("[^a-zA-Z0-9]",""));
		    	}

			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			  
			  for(int j=0;j<file_array.size();j++)
			  {
				 
				if(file_array.get(j).equals(queryArray.get(0)))
				 {
					count++;
					for(int h=1;h<queryArray.size();h++)
					{
						int num=j+h;
						if( (num)< file_array.size() )
						{
							if(file_array.get(num).equals(queryArray.get(h)))
							{
								count++;
							}
						}
					}
				 }
				 if(count == queryArray.size())
				 {
					  
					 Result.add(i); 
				 }
				 
				 count=0;
			  }
			  file_array.clear();
		  }
 		input.close();
 	
 		Set<Integer> hashset = new HashSet<Integer>(Result);
 		Result = new ArrayList<Integer>(hashset);
 		System.out.println("Result גרשיים : "+Result);
 		return Result;
 	}
 	
 	//Star
 	public ArrayList<Integer>  Star(indexFile in)
 	{
 		 
 		ArrayList<Integer> Result = new ArrayList<Integer>();
 		String a = null;
 		
 		for(int i=0;i<queryArray.size();i++)
 		{
 			
 			if(queryArray.get(i).contains("*"))
 			{
 				a = queryArray.get(i).replaceAll("[^a-zA-Z0-9]","");	
 			}
 		}
 	 
 		int count=0;
 		for(int i=0;i<in.DictionaryArray.size();i++)
 		{
 			int length_word = in.DictionaryArray.get(i).name.length();
 			
 			for(int j=0;j<a.length();j++)
 			{
 				if(j<length_word)
 				{
 					char a1 =in.DictionaryArray.get(i).name.charAt(j);
 					char b1=a.charAt(j);
 					if(a1==b1)
 					{
 						count++;
 					}
 				}
 			}
 			if(count == a.length())
 			{
 				queryArray.add(in.DictionaryArray.get(i).name);
 				
 				System.out.println(in.DictionaryArray.get(i).id);
 				for(int k=0;k<in.DictionaryArray.get(i).offsetArray.size();k++){
 				Result.add(in.DictionaryArray.get(i).offsetArray.get(k).postid);
 				}
 			}
 			count=0;
 		}
 		
 		Set<Integer> hashset = new HashSet<Integer>(Result);
 		Result = new ArrayList<Integer>(hashset);
 		System.out.println(queryArray);
 		
 		return Result;
 	}
}
