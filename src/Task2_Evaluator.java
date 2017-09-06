import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Task2_Evaluator {

	public static void main(String[] args) throws IOException 
	{
		
		String basepath = new File("Task2").getAbsolutePath();    
		 File directory = new File(basepath);
	      File[] corpus = directory.listFiles();
	  
		
		
		
		LinkedHashMap<String, ArrayList<String>> relDocs = new LinkedHashMap<>();
		
		
		for (int x=0; x<corpus.length; x++)
		 { 
			BufferedReader br = new BufferedReader(new FileReader(corpus[x]));
		String buffer ="", filetext = "";
		
//		System.out.println(corpus[x]);
		String m = corpus[x].toString().substring(basepath.length()+1, corpus[x].toString().length()-4);
   		System.out.println(m);

		ArrayList<String> t = new ArrayList<>();
		while((buffer = br.readLine())!=null)
	   	{
	   		
			//System.out.println(buffer);
			String[] fnames = buffer.split("\t");
			System.out.println(fnames[1]);
			t.add(fnames[1]);
	   	}

		relDocs.put(m, t);
		
		
		
		}
		
		
		
		Evaluator e = new Evaluator();
		
		
		e.evaluatePerformance(relDocs, new File("Task2_Eval.csv"));
		
		
	}	
}
