import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CorpusBuilder2 {

	public static void main(String[] args) throws IOException 
	{
		String basepath = new File("cacm").getAbsolutePath();    
	      File directory = new File(basepath);
	      File[] corpus = directory.listFiles();
	      BufferedReader br;
	      FileWriter writer;
	      String corpuspath = new File("Corpus_No_Stopping").getAbsolutePath();
	      
	      
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
	    	
	    	byte[] bf = filetext.getBytes(); 
		    filetext="";
	    	 filetext = new String(bf, "UTF-8");
	    	  		  
	    	  	
	    	filetext = filetext.substring(11, filetext.length()-1);
	    	filetext = filetext.substring(0, filetext.length()-14);
	    	filetext = filetext.replace(">   ", "");         
	    	filetext= filetext.replaceAll("//s+", "/s");
	    	filetext= filetext.replaceAll("//s+", "/s");
	    	filetext= filetext.replaceAll("//s+", " ");
	    	String txt_str2 = filetext;
			txt_str2 = txt_str2.toLowerCase();
			
	    	
	    	writer = new FileWriter(new File(corpuspath+"\\"+filename+".txt"));
	    	writer.write(txt_str2);
	    	writer.flush();
	    	writer.close();
	    }
	    	
	   
	}

}
