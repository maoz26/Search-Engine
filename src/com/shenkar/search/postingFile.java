package com.shenkar.search;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

//scanning a folder and responsible for printing the list of files to posting.txt
public class postingFile 
{
	public Map<Integer,String> Fileob = new Hashtable<Integer,String>();
	
	public List<File> listofFiles;

	public int counter;
	
	postingFile(){}

	postingFile(File f)
	{	
		File[] ArrayFiles = f.listFiles(); 
		listofFiles=new ArrayList<File>();
		for(int i=0;i<ArrayFiles.length;i++){
			listofFiles.add(ArrayFiles[i]);
		}
		 
		counter= listofFiles.size();
		NumbringFiles();
		WriteToPostFile();
	}
	
	public void AddFile(File f)
	{
		listofFiles.add(f);
		counter++;
		Fileob.clear();
		NumbringFiles();
		WriteToPostFile();
	}
	
	
	public void RemoveFile(File f)
	{
		int id=-99;
		for(int i=0;i<listofFiles.size();i++){
			
			if(listofFiles.get(i).getName().equals(f.getName()))
			{
				id=i;
			}
		}
		if(id!=-99)
		{
		System.out.println(id);	
		listofFiles.remove(id);
		
		
		counter--;
		Fileob.clear();
		NumbringFiles();
		WriteToPostFile();
		}
		else{
			return;
		}
		 
		
	}
	
	

	private void WriteToPostFile(){
		 String currentdir = System.getProperty("user.dir");
		 File dir = new File(currentdir);
		 try {
			BufferedWriter  br = new BufferedWriter (new FileWriter(dir.toString()+"\\Data\\posting.txt"));
			for(int i=0;i<Fileob.size();i++){
				br.write(i+","+Fileob.get(i));
				br.newLine();
			}
			 
			br.close();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
	}

	private void NumbringFiles() 
	{
		for (int i = 0; i < listofFiles.size(); i++) {
		      if (listofFiles.get(i).isFile()) 
		      {
		    	  Fileob.put(new Integer(i),listofFiles.get(i).getPath());
		    	  
		      } else 
		    	  if (listofFiles.get(i).isDirectory()) 
		    	  {
		    		  System.out.println("Directory " + listofFiles.get(i).getName());
		    	  }
		}
	}
}
	
	
	
	


