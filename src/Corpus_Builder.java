import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Corpus_Builder 
{

	public static void main(String[] args) throws IOException 
	{
		String basepath = new File("cacm").getAbsolutePath();    
	      File directory = new File(basepath);
	      File[] corpus = directory.listFiles();
	      BufferedReader br;
	      FileWriter writer;
	      String corpuspath = new File("ProjectCorpus").getAbsolutePath();
	      
	      
	    for(int i=0; i<corpus.length; i++)
	    {
	    //	System.out.println(corpus[i]);
	    	br  = new BufferedReader(new FileReader(corpus[i]));
	    	String filetext="", buffer="";
	    	ArrayList<String> sentences = new ArrayList<>();
	    	
	    	    	
	    	String filename= corpus[i].toString();
	    	
	    	filename = filename.substring(filename.indexOf("cacm\\")+5, filename.length()-5);
	    	
	    	System.out.println(filename);
	    	  	
	    	while((buffer = br.readLine())!=null)
        	 {
	    		
	    		buffer= buffer + " ";
        		 filetext = filetext + buffer;
        	 }
	    	
	    	  String fullfiledata = filetext;	
	    	  		  
	    	  	
	    	filetext = filetext.substring(11, filetext.length()-1);
	    	filetext = filetext.substring(0, filetext.length()-14);
	    	filetext = filetext.replace(">   ", "");         
	    	filetext= filetext.replaceAll("//s+", " ");
	    	
	    	String txt_str = filetext;
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


	    	
	    	
	    	
	    	writer = new FileWriter(new File(corpuspath+"\\"+filename+".txt"));
	    	writer.write(txt_str);
	    	writer.flush();
	    	writer.close();
	    }
	    	
	   
	}

}
