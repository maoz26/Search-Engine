package com.shenkar.search;
import java.awt.BorderLayout;

import java.awt.image.*;
import javax.imageio.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;


public class main extends JFrame
{
	private indexFile 		 in			 ;
	private postingFile 	 pf 		 ;
	private JPanel 			 contentPane ;
	private JTextField  	 textField	 ;
	private JList 			 list		 ;
	private DefaultListModel model	     ;
	private ArrayList<String> printArray ;
	private JScrollPane 	  sp		 ;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main frame = new main();
					//frame.checkFiles();
					 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public main() 
	{
		printArray =new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		setBounds(600, 100, 600, 600);
		setTitle("Search Engine - Maoz and Nir");
		
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.cyan);
		BufferedImage img = null;

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		textField = new JTextField();
		textField.setBounds(83, 108, 308, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		final JLabel NumberFileslabel = new JLabel("0");
		NumberFileslabel.setBounds(199,83, 46, 14);
		contentPane.add(NumberFileslabel);
		
		JButton fileItem1 = new JButton("Import Dictionary");
		fileItem1.setBounds(0, 0, 110, 30);
		contentPane.add(fileItem1);
		

		JButton fileItem2 = new JButton("Add File");
		fileItem2.setBounds(120, 0, 110, 30);
		fileItem2.setBackground(Color.GREEN);
		contentPane.add(fileItem2);

		JButton fileItem3 = new JButton("Remove File");
		fileItem3.setBounds(240, 0, 110, 30);
		fileItem3.setBackground(Color.RED);
		contentPane.add(fileItem3);

		JButton fileItem4 = new JButton("Exit");
		fileItem4.setBounds(480, 0, 110, 30);
		contentPane.add(fileItem4);
				
		
		
		//adding action listener to menu items
		fileItem2.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
						 
						  JFileChooser fileChooser = new JFileChooser();
					      fileChooser.setCurrentDirectory(new java.io.File("."));

					       fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					        int returnValue = fileChooser.showOpenDialog(null);
					        if (returnValue == JFileChooser.APPROVE_OPTION) 
					        {
					        	if(pf != null){
					        		pf.AddFile(fileChooser.getSelectedFile());
					        		NumberFileslabel.setText(Integer.toString(pf.counter));
					        		
					        		try {
										in.AddFile(fileChooser.getSelectedFile());
									} catch (FileNotFoundException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
					        	}
					        }
							
						}
					}
				);		
		
		
		//adding action listener to menu items
		fileItem3.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							JFileChooser fileChooser = new JFileChooser();
						      fileChooser.setCurrentDirectory(new java.io.File("."));

						       fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						        int returnValue = fileChooser.showOpenDialog(null);
						        if (returnValue == JFileChooser.APPROVE_OPTION) 
						        {
						        	if(pf != null)
						        	{
						        		pf.RemoveFile(fileChooser.getSelectedFile());
						        		System.out.println("Remove file: "+fileChooser.
						        				getSelectedFile().getName());
						        		NumberFileslabel.setText(Integer.toString(pf.counter));

						        		try {
											in.RemoveFile(fileChooser.getSelectedFile());
										} catch (FileNotFoundException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
						        	}
						        }
							
						}
					}
				);
		
		
		
		
		//adding action listener to menu items
		fileItem1.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
						     JFileChooser fileChooser = new JFileChooser();
						     fileChooser.setCurrentDirectory(new java.io.File("."));

						     fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						        int returnValue = fileChooser.showOpenDialog(null);
						        if (returnValue == JFileChooser.APPROVE_OPTION) 
						        {
						        	pf = new postingFile(fileChooser.getSelectedFile());
						        	NumberFileslabel.setText(Integer.toString(pf.counter));
						        		
									try {
										in = new indexFile(fileChooser.getSelectedFile());
										} catch (FileNotFoundException e1) {
											 
										e1.printStackTrace();
										}
						        }
						        else
						        {
						        	System.out.println("Import Was Canceled");
						        }
						}
					}
				);
		
		
		//adding action listener to menu items
		fileItem4.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent e)
						{
							dispose();
							
						}
					}
				);

		
		 
		JButton helpItem1 = new JButton("ReadMe");
		helpItem1.setBounds(360, 0, 110, 30);
		contentPane.add(helpItem1);
		
		helpItem1.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						   
						JFrame jf = new JFrame("Read Me");
                		jf.setBounds(750, 200, 500, 500);
                		
                		JTextPane pane = new JTextPane();
                		pane.setEditable(false);
                		SimpleAttributeSet set = new SimpleAttributeSet();
                		StyleConstants.setBold(set, true);
                		
                	   
                		StyleConstants.setFontSize(set, 14);
                	    StyleConstants.setItalic(set, true);
                	    
                	    
                	    pane.setCharacterAttributes(set, true);
                	    Document doc = pane.getStyledDocument();
                	    
                	    String w =
                	    		 "Maoz Tamir 301833232 \n"
                       	    	 +"Nir Jackson 302634084\n\n"+
                	    		"Search Engine project.\n" +
                	    		"This project will example the use of a search engine " 	
                	    		 +"\n first:"+"\n- Load a folder by pressing the correct directory"
                	    		 +"\n second:"+"\n- Start searching the word you are looking for"
                	    		 +"-You can use one of the following operators such as: AND, NOT, OR,(), *, \"" +"\n"
                	    		 +"Add file:\n"+"-you can add an additional file to the Data Base: go to File -> Add file and choose "
                	    		 +"the DOC you wish to add.\n"
                	    		 +"Remove file:\n -Remove file from the Date Base: go to File -> Remove file and choose " +
                	    		 "the file you wish to delete.\n"
                	    		 ;
                	 
                	     
                	    
                	    
                	    try {
							doc.insertString(doc.getLength(),w , set);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                	    
                	    Container cp = jf.getContentPane();
                	    JScrollPane scrollPane = new JScrollPane(pane);
                	    cp.add(scrollPane, BorderLayout.CENTER);

                	    
                	    jf.setVisible(true);	
					}
				}
			);
		 
		
		JLabel lblFilesInDatabase = new JLabel("Files in DataBase:");
		lblFilesInDatabase.setBounds(91,83, 107, 14);
		contentPane.add(lblFilesInDatabase);
		
		
		model = new DefaultListModel();
		list = new JList();
 
		list.setModel(model);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBackground(Color.LIGHT_GRAY);
		list.setBounds(42, 141, 507, 409);
		sp = new JScrollPane( list);
		sp.setSize(507, 409);
		sp.setLocation(42, 141);
		contentPane.add(sp, BorderLayout.CENTER);
	
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(401, 107, 89, 23);
		
		btnSearch.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e)
            {
            	model.clear();
            	printArray.clear();
            	
            	if(textField.getText().toString().contains("AND") || textField.getText().toString().
            			contains("NOT") || textField.getText().toString()
            					.contains("\"" ) ||textField.getText().toString().contains("OR")
            					||textField.getText().toString().contains("(")
            					||textField.getText().toString().contains("*"))
            	{
            		ArrayList<Integer> Results = Search_Result();
            		if(Results.isEmpty()){
            			model.addElement("No Matches");
            		}
            		else{
            		for(int j=0;j<Results.size();j++){
            	        
        				//model.addElement(pf.Fileob.get(Results.get(j)));
            			model.addElement(in.summaryArray.get(Results.get(j)));
        			}
            		}
            		
            	}
            	else{
            	String s = 	textField.getText().toString().toLowerCase();	 
            	printArray.add(s);
				System.out.println("textField: "+s);	 
            	if(s.length()>=1){	 
            	for(int i=0;i<in.DictionaryArray.size();i++){
            		
            		if(in.DictionaryArray.get(i).name.equals(s))
            		{
            			System.out.println("name: "+in.DictionaryArray.get(i).name);
            			
            			for(int j=0;j<in.DictionaryArray.get(i).get_post_hits();j++){

            				
            				model.addElement(in.summaryArray.get(in.DictionaryArray.get(i).offsetArray.
            						get(j).postid ));
            			}
            			
            		}
            	}
            	}
            	}
           
            }

			private ArrayList<Integer> Search_Result() 
			{
					
				SearchResult sr = new SearchResult(textField.getText().toString());
				boolean isBrackets =   false;
				boolean isQuotes   =   false;
				boolean isStar     =   false;
				ArrayList<Integer> Result_Final =new ArrayList<Integer>();
				
				
				for(int i=0;i<sr.queryArray.size();i++)
				{
					if(sr.queryArray.get(i).contains("("))
					{
						isBrackets=true;
						Result_Final = sr.Brackets(in);
						printArray = sr.RemoveNot();
					}
					
					if(sr.queryArray.get(i).contains("*"))
					{
						isStar=true;
						Result_Final=sr.Star(in);
						printArray=sr.queryArray;
						
					}
					
					if(sr.queryArray.get(i).contains("\""))
					{
						isQuotes=true;
						Result_Final=sr.Quotes(in);
						printArray=sr.queryArray;
						
					}
				}
				
				
				if(isBrackets == false && isQuotes ==false && isStar == false)
				{
					Result_Final= sr.GiveMeResult(in);
					printArray  = sr.RemoveNot();

				}
			 
				
				System.out.println("printArray: "+printArray);

				return Result_Final;
			}
        });

		contentPane.add(btnSearch);
		
		JButton btnNameFiles = new JButton("List Of Files");
		btnNameFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model.clear();
				if(pf != null){ 
				for(int j=0;j<pf.Fileob.size();j++)
				{
					model.addElement(pf.Fileob.get(j));
					
				}
				}else{
					model.addElement("No Files In DataBase.");
				}
				
			}
		});
		btnNameFiles.setBounds(91, 49, 120, 23);
		contentPane.add(btnNameFiles);
		
		JButton btnAnd = new JButton("AND");
		btnAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model.clear();
				textField.setText(textField.getText() + " AND " );
				
				
			}
		});
		btnAnd.setBounds(221, 49, 75, 23);
		contentPane.add(btnAnd);
		
		JButton btnOr = new JButton("OR");
		btnOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model.clear();
				textField.setText(textField.getText() + " OR " );
				
			}
		});
		btnOr.setBounds(306, 49, 75, 23);
		contentPane.add(btnOr);
		
		JButton btnNot = new JButton("NOT");
		btnNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				model.clear();
				textField.setText(textField.getText() + " NOT " );
				
			}
		});
		btnNot.setBounds(391, 49, 75, 23);
		contentPane.add(btnNot);
		
		 list.addListSelectionListener(new ListSelectionListener() {

	            @Override
	            public void valueChanged(ListSelectionEvent arg0) {
	            	
	                if (!arg0.getValueIsAdjusting()) {
	                	if(list.isSelectionEmpty()==false){
	                		
	                		JFrame jf = new JFrame("Result...");
	                		jf.setBounds(100, 100, 600, 600);
	                		
	                		JTextPane pane = new JTextPane();
	                		pane.setEditable(false);
	                		SimpleAttributeSet set = new SimpleAttributeSet();
	                		StyleConstants.setBold(set, true);
	                		pane.setCharacterAttributes(set, true);
	                	   
	                		StyleConstants.setFontSize(set, 14);
	                	    StyleConstants.setItalic(set, true);
	                	    
	                	    StyleConstants.setBackground(set, Color.blue);
	                	    Document doc = pane.getStyledDocument();

	                	    
	                	    Container cp = jf.getContentPane();
	                	    JScrollPane scrollPane = new JScrollPane(pane);
	                	    cp.add(scrollPane, BorderLayout.CENTER);

	                	    jf.setSize(600, 600);
	                	    
	                	 String w;
	                	 boolean Written=false;
	                	 String gg=Character.toString(list.getSelectedValue().toString().charAt(10));
	                	 if( Character.toString(list.getSelectedValue().toString().charAt(11)).equals("<")){}
	                	 else{
	                		 gg+= Character.toString(list.getSelectedValue().toString().charAt(11));
	                	 }

	                	File file = new File(pf.Fileob.get(Integer.parseInt(gg)));
	                	try {
	                		
							Scanner input =  new Scanner(file);
							System.out.println(printArray);
							while(input.hasNext() ) 
					    	{
								Written=false;
								w=input.next();
								
								String temp = w.replaceAll("[^a-zA-Z0-9]","");
								temp=temp.toLowerCase();
								
								for(int i=0;i<printArray.size();i++)
								{
									if(temp.equals(printArray.get(i).toString()))
									{
										StyleConstants.setBackground(set, Color.YELLOW);
									
										w+=" ";	
										doc.insertString(doc.getLength(), w, set);
										Written=true;
										
									}
									}
								if(Written==false)
								{
								StyleConstants.setBackground(set, Color.WHITE);
								w+=" ";	
								doc.insertString(doc.getLength(), w, set);
								
								}
					    	}
							input.close();
							
						} catch (FileNotFoundException e) {
							
							e.printStackTrace();
						} catch (BadLocationException e) {
							
							e.printStackTrace();
						}  
	                	
	                	jf.setVisible(true);
	                   
	                }
	                }
	                
	            }
	        });
	}
}
