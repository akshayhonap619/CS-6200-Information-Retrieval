import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Relevant_docs {

	public LinkedHashMap<String, ArrayList<String>> getReleveantDocs() throws IOException,FileNotFoundException  
	{
		String basepath = new File("").getAbsolutePath();    
	    
		basepath = basepath + "\\cacm.rel";
		
		File relDocsFile = new File(basepath);
		
		LinkedHashMap<String, ArrayList<String>> relDocs = new LinkedHashMap<>();
		
		BufferedReader br = new BufferedReader(new FileReader(relDocsFile));
	      FileWriter writer;
	
	      String buffer ="", filetext = "";
	   	while((buffer = br.readLine())!=null)
   	 {
   		
   		//System.out.println(buffer + "\n");
   		
   		String[] linesplit = buffer.split(" ");
   		
   		//System.out.println(linesplit[0]+"\t"+linesplit[2]);
   		
   		if(relDocs.containsKey(linesplit[0]) == false)
   		{
   			ArrayList<String> relDocsList = new ArrayList<>();
   		//	System.out.println(linesplit[0] + "\t" +linesplit[2]);
   			relDocsList.add(linesplit[2]);
   				
   			relDocs.put(linesplit[0],relDocsList);
   		}
   		//if(relDocs.containsKey(linesplit[0]) == true)
   		else
   		{
   			ArrayList<String> relDocsList = relDocs.get(linesplit[0]);
   			relDocsList.add(linesplit[2]);
   			relDocs.put(linesplit[0],relDocsList);
   		}
   		
   	 }
  
	   	for (String k : relDocs.keySet())
	   	{
	   	        if(relDocs.get(k)==null)
	   	        {
	   	        	relDocs.remove(k);
	   	        }
	   	}
	   	relDocs.remove(56);
	   	return relDocs;
	}

}
