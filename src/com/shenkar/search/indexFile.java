package com.shenkar.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
 

// this class is in charge of indexing the data files.
// after the indexing it saves in the index.txt file in the following formula:
//"word","total file appearences",["ID","number of found in ID", ...]

public class indexFile 
{
	public List<File> 		    	  listofFiles;
	public ArrayList<indexObejct>     ioArray;
	public ArrayList<indexObejct>     DictionaryArray;
	public List<String>               words;
	public List<String>               words_temp ;
	public Stopwords				  sw;
	public Scanner 					  scanner;
	public FileOutputStream 		  fop ;
	public File 					  file	;
	public String 					  word;
	public ArrayList<Summary>     	  summaryArray;
	
	
	//constructor
	public indexFile(File f) throws FileNotFoundException  
	{
		Init(f);
		ScanFiles();
		Dictionary();
		Summary();
		WriteToIndexFile();
	}
	
	
	//removes file from all lists
	public void RemoveFile(File f ) throws FileNotFoundException
	{
		int id=-99;
		for(int i=0;i<listofFiles.size();i++)
		{
			
			if(listofFiles.get(i).getName().equals(f.getName()))
			{
				id=i;
			}
		}
		
		if(id!=-99)
		{
			System.out.println("id: "+id);
			listofFiles.remove(id);
			ioArray.clear();
			DictionaryArray.clear();
			words.clear();
			words_temp.clear();
			summaryArray.clear();
		    ScanFiles();
			Dictionary();
			Summary();
			WriteToIndexFile();
			
		}else{
			System.out.println("the file was removed");
			return;
		}
		
		
	}
	
	//adds file to all lists
	public void AddFile(File f ) throws FileNotFoundException
	{
		listofFiles.add(f);
		ioArray.clear();
		DictionaryArray.clear();
		words.clear();
		words_temp.clear();
		summaryArray.clear();
	    ScanFiles();
		Dictionary();
		Summary();
		WriteToIndexFile();
		
		
	}
	
	//uses dictionary to write to index.txt 
	public void WriteToIndexFile() 
	{
		 String currentdir = System.getProperty("user.dir");
		 File dir = new File(currentdir);
		 try {
			BufferedWriter  br = new BufferedWriter (new FileWriter(dir.toString()+"\\Data\\index.txt"));
		 
			for(int i=0;i<DictionaryArray.size();i++){
			br.write(DictionaryArray.get(i).name+","+DictionaryArray.get(i).post_hits+","+DictionaryArray.get(i)
					.offsetArray.toString());
			br.newLine();
			}
			br.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		
	}
	
	
	//initialize file
	public void Init(File f)
	{
		sw 			    = 	 new Stopwords()			   ;
		
		File[] ArrayFiles = f.listFiles(); 
		listofFiles=new ArrayList<File>();
		for(int i=0;i<ArrayFiles.length;i++){
			listofFiles.add(ArrayFiles[i]);
		}
		ioArray		    = 	 new  ArrayList<indexObejct>() ;
		DictionaryArray =    new  ArrayList<indexObejct>() ;
		words 		    =    new  ArrayList<String>()	   ;
		words_temp	    = 	 new  ArrayList<String>()	   ;
		summaryArray	=	 new  ArrayList<Summary>()	   ;	
	}
 
	
	//check string in dictionary to see if exists
	public Boolean checkDictionary(String s)
	{
		for(int i=0;i<DictionaryArray.size();i++){
			if(DictionaryArray.get(i).name.equals(s)){
				return false;
			}
		}
		
		return true;
	}
	
	
	//creates dictionary (array of lists)
	public void Dictionary()
	{
		 
		for (int i=0;i<ioArray.size();i++)
		{
			if(checkDictionary(ioArray.get(i).name)){
			indexObejct iobject = new indexObejct();
			iobject.name=ioArray.get(i).name; ;
			iobject.post_hits=1;
			Offset of = new Offset(ioArray.get(i).id ,ioArray.get(i).hits);
			
			iobject.offsetArray.add(of);
 
			for(int j=0;j<ioArray.size();j++)
			{
				
				if(ioArray.get(i).name.equals(ioArray.get(j).name) && ioArray.get(i).id != ioArray.get(j).id)
				{
					 
					iobject.post_hits = iobject.post_hits+1;
					
					Offset of2 = new Offset(ioArray.get(j).id ,ioArray.get(j).hits);
					
					iobject.offsetArray.add(of2);
				 }}
			
			DictionaryArray.add(iobject);
			}
		}
	}
	
	
	public int checkHits(String s)
	{
		int count=0;
		for(int i=0;i<words_temp.size();i++){
			if(words_temp.get(i).equals(s)){
				count++;
			}
		}
		return count;
	}
	
	//checks if a word is a save word
	public void SaveWords(List<String> s ,int id)
	{
		for(int i=0;i<s.size();i++)
		{
			indexObejct iobject = new indexObejct();
			iobject.id=id;
			iobject.hits = checkHits(s.get(i));
			iobject.name = s.get(i);
			ioArray.add(iobject);
		}
	//	System.out.println(ioArray);
	}
	
	
	public void Summary () throws FileNotFoundException
	{
		  Scanner input = null ;
		  Scanner input2 = null ;
		  List<String>	data =new ArrayList<String>(); 
		  int count=0;
		  boolean isFirstLine=false;
		  String first_line="";
		  String line="";
		  String g="";
		  for (int i = 0; i < listofFiles.size(); i++) 
		  {
			  file = new File(listofFiles.get(i).getPath());
			  count=0;
			  isFirstLine=false;
			  line="";
			  first_line="";
			  g="";
			  Summary s = new Summary();
			  s.summary=" ";
			  s.date="";
			  
			  input =  new Scanner(file);
			  while(input.hasNextLine())
			  {
				  line = input.nextLine();
				  
				  if(isFirstLine==false)
				  {
					 first_line=line;
				//	 System.out.println("first_line: "+first_line);
					 isFirstLine=true;
				  }
				  else{
				  if(count<3)
				  {
					 if(line.equals("")) {}
					 else{
					 
						 s.summary+= line;
						 s.summary+="<BR>";
						 count++;
					 }
					 
				  }
				  }
			  }

			  input.close();
			  input2 =  new Scanner(first_line);
			  input2.useDelimiter(",");
			  while(input2.hasNext()) {
				  g=input2.next();
				 // System.out.println(g);
				  data.add(g);
			  }
			  input2.close();
			  s.id=i;
			  s.author=data.get(0);
			  
			  if(data.get(1).equals("no data.")){
				  s.date="no data";
			  }else{
				s.date=data.get(1)+"/"+data.get(2)+"/"+data.get(3);  
			  }

			 //System.out.println(s.summary);
			 data.clear();
			// System.out.println(s);
			 summaryArray.add(s);
		  }
	}
	
	//check if the word exist in the stop words list
	public void ScanFiles() throws FileNotFoundException  
	{
		  Scanner input = null ;
		  for (int i = 0; i < listofFiles.size(); i++) {
		      if (listofFiles.get(i).isFile()) 
		      {
		       // System.out.println("File " + listofFiles[i].getName());
		    	file = new File(listofFiles.get(i).getPath());
		    	 
		    	input =  new Scanner(file);
		     
		    	while(input.hasNext() ) 
		    	{
		    		word = input.next().replaceAll("[^a-zA-Z0-9]","");

		    		if(word.equals("")){}
					else{
						  if(sw.isStopWord(word)){}	//if the word exist in the stopword list, skip it.
						  else{
							words.add(word.toLowerCase());
							words_temp.add(word.toLowerCase());
							}
					}
		    	}
		    	
		       Set<String> hashset = new HashSet<String>(words);
		       words = new ArrayList<String>(hashset);
		       Collections.sort(words);
		       SaveWords(words,i);
		       words_temp.clear();
		       words.clear();
		      } else 
		    	  if (listofFiles.get(i).isDirectory()) 
		    	  {
		    		//  System.out.println("Directory " + listofFiles[i].getName());
		    	  }
		  }
		  input.close();
	}

}