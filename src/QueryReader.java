import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class QueryReader {

	public LinkedHashMap<String, String> getQueries() throws IOException,FileNotFoundException 
	{
		
		String basepath = new File("").getAbsolutePath();    
		File queryFile = new File(basepath+"\\cacm.query");
		
		
		String filetext="", buffer="";
	
		LinkedHashMap<String, String> queryarray = new LinkedHashMap<>();
	//String queryarray[] = new String[100];
		Scanner scan = new Scanner(queryFile);  
		
		
	/*	
		for(int i=0;i<64;i++)
		{
		
		scan.useDelimiter("@");  
		//String content = scan.next(); 
		//System.out.println(content);
			content[i] = scan.next();
			i++;
			System.out.println(content[i]);
		}
		scan.close();
	*/	
		
		for(int i=0; i<64 ; i++)
		{	
		scan.useDelimiter("@");  
		String content = scan.next(); 
		
		
		
		String txt_str = content;
		txt_str = txt_str.toLowerCase();
		txt_str=txt_str.replaceAll("\\[[0-9]+\\]"," "); // Remove references
	    txt_str=txt_str.replaceAll(("\""),""); // double quotes
	    txt_str=txt_str.replaceAll("(:)+|(;)", ""); // colon and semi colon
	    txt_str=txt_str.replaceAll("('\\s)+|(\\s')", " "); // space-apos, apos-space
	    txt_str=txt_str.replaceAll("(`\\s)+|(~)+|(;\\s)+|(;)"," "); // colons(with space and without), tilde removed
	    txt_str=txt_str.replaceAll("“", "");
	    txt_str=txt_str.replaceAll("”", ""); 		           
	    txt_str=txt_str.replaceAll("(\\()+|(\\)+|(\\{)+|(\\})+|(\\[)+|(\\]))",""); // brackets		                 
	    txt_str=txt_str.replaceAll("(, )+|(\\.\\s)", " "); // Comma and fullstop
	    txt_str=txt_str.replace("?", " ");
	    txt_str=txt_str.replace("!", " "); 
	    txt_str=txt_str.replaceAll("—", " ");           
	    txt_str=txt_str.replaceAll("-\\s", " ");
	    txt_str=txt_str.replace("..", "");
	    txt_str=txt_str.replace("...", "");
	    txt_str=txt_str.replace("....", "");
	    txt_str=txt_str.replace(".....", "");
	    txt_str=txt_str.replace("^", "");
	    txt_str=txt_str.replace("*", "");
	    txt_str=txt_str.replaceAll("\\s+", " "); //multiple spaces

		txt_str = txt_str.substring(1);
	    queryarray.put( String.valueOf(i+1), txt_str);
		
		}
		
		return queryarray;
	}

	
	public LinkedHashMap<String, String> getQuerieswithStopWords() throws IOException,FileNotFoundException 
	{
		
		String basepath = new File("").getAbsolutePath();    
		File queryFile = new File(basepath+"\\cacm.query");
		
		
		String filetext="", buffer="";
	
		LinkedHashMap<String, String> queryarray = new LinkedHashMap<>();
	//String queryarray[] = new String[100];
		Scanner scan = new Scanner(queryFile);  
		
		
	/*	
		for(int i=0;i<64;i++)
		{
		
		scan.useDelimiter("@");  
		//String content = scan.next(); 
		//System.out.println(content);
			content[i] = scan.next();
			i++;
			System.out.println(content[i]);
		}
		scan.close();
	*/	
		
		for(int i=0; i<64 ; i++)
		{	
		scan.useDelimiter("@");  
		String content = scan.next(); 
		
		
		
		String txt_str = content;
		txt_str = txt_str.toLowerCase();
		txt_str=txt_str.replaceAll("\\[[0-9]+\\]"," "); // Remove references
	    txt_str=txt_str.replaceAll(("\""),""); // double quotes
	    txt_str=txt_str.replaceAll("(:)+|(;)", ""); // colon and semi colon
	    txt_str=txt_str.replaceAll("('\\s)+|(\\s')", " "); // space-apos, apos-space
	    txt_str=txt_str.replaceAll("(`\\s)+|(~)+|(;\\s)+|(;)"," "); // colons(with space and without), tilde removed
	    txt_str=txt_str.replaceAll("“", "");
	    txt_str=txt_str.replaceAll("”", ""); 		           
	    txt_str=txt_str.replaceAll("(\\()+|(\\)+|(\\{)+|(\\})+|(\\[)+|(\\]))",""); // brackets		                 
	    txt_str=txt_str.replaceAll("(, )+|(\\.\\s)", " "); // Comma and fullstop
	    txt_str=txt_str.replace("?", " ");
	    txt_str=txt_str.replace("!", " "); 
	    txt_str=txt_str.replaceAll("—", " ");           
	    txt_str=txt_str.replaceAll("-\\s", " ");
	    txt_str=txt_str.replace("..", "");
	    txt_str=txt_str.replace("...", "");
	    txt_str=txt_str.replace("....", "");
	    txt_str=txt_str.replace(".....", "");
	    txt_str=txt_str.replace("^", "");
	    txt_str=txt_str.replace("*", "");
	    txt_str=txt_str.replaceAll("\\s+", " "); //multiple spaces

		txt_str = txt_str.substring(1);
		
		
		StopWordsGenerator sg = new StopWordsGenerator();
		ArrayList<String> StopWords = sg.getStopWords();
		removeStopWords(txt_str, StopWords);
		
	    queryarray.put( String.valueOf(i+1), txt_str);
		
		}
		
		return queryarray;
	}

	public static String removeStopWords(String query, ArrayList<String> StopWords)
	{
		String finalquery="";
		
		String[] splitline = query.split(" "); 
		
		for(int i=0; i<splitline.length ; i++)
		{
			if(StopWords.contains(splitline[i]) ==false)
			{
				finalquery = finalquery + splitline[i] + " ";
			}
		}
		
		finalquery = finalquery.substring(0, finalquery.length()-1);
		return finalquery;
	}
	
	
	
}
