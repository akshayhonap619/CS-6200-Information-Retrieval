import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Cosine_Similarity_Task1 {

	public static void main(String[] args) throws IOException 
	{
		TreeMap<String, Double> docfreq = new TreeMap<>();
		docfreq=getDocFreq(docfreq);
		
		TreeMap<String, Double> termfreq = new TreeMap<>();
		
		
		TreeMap<String, Double> queryfreq = new TreeMap<>();
		
		LinkedHashMap<String, Double> scores = new LinkedHashMap<>();
		
		  String query = "";
		
		  String[] querywords = new String[2000]; 
	
		 double querycount = 0; 
		  
		 String basepath = new File("ProjectCorpus").getAbsolutePath();
	           
	      File directory = new File(basepath);
	      File[] corpus = directory.listFiles();
	 	  
		QueryReader qr = new QueryReader();
		LinkedHashMap<String, String> queries = qr.getQueries();
		
		System.out.println(queries);
	 
		 
		 
		for(String z : queries.keySet())
		{	
		  
			querywords = queries.get(String.valueOf(z)).split(" "); 
		  
		 
		  for(int i=0; i<querywords.length;i++)
		  {
			  querycount = getCount(querywords[i], queryfreq);
			  
			  queryfreq.put(querywords[i], querycount+1);
			  
		  }
		  
		 // System.out.println(queryfreq);
		  
		  
		  Scanner wordFile;
	      String word = "";     // A word read from the file
	      Integer count;   // The number of occurrences of the word
	      
	   //   String basepath = new File("Corpus").getAbsolutePath();
	      
	 
	      
	      
	      
	     
	//**FOR LOOP TO READ THE DOCUMENTS**
	 for (int x=0; x<corpus.length; x++)
	{ 
		   TreeMap<String, Integer> frequencyData = new TreeMap<String, Integer>( );
		   TreeMap<String, Double> tf_idf = new TreeMap<String, Double>();
	      double docMagnitute=0;
	      double queryMagnitute=0;
		  double Numerator=0; 
		  double score=0;
		  String filename="";
		  
		  
		  try
	      {
	         wordFile = new Scanner(new FileReader(corpus[x]));
	      }
	      catch (FileNotFoundException e)
	      {
	       System.err.println(e);
	         return;
	      }
	  
	      
	      
	      while (wordFile.hasNext( ))
	      {
	          // Read the next word and get rid of the end-of-line marker if needed:
	              word = wordFile.next();
	                
	              if(frequencyData.containsKey(word))
	              {
	            	  frequencyData.put(word, frequencyData.get(word)+1);
	              }
	              else
	              {
	            	  frequencyData.put(word, 1);
	              }
	      }
	
	      
	     for( String indexwords : frequencyData.keySet())
	     {
	    	 tf_idf.put(indexwords, (frequencyData.get(indexwords))*(docfreq.get(indexwords)) );
	     }
	      
	     for(String indexwords : frequencyData.keySet())
	     {
	    	 docMagnitute = docMagnitute +  (tf_idf.get(indexwords) * tf_idf.get(indexwords)) ;
	     }
	     docMagnitute = Math.sqrt(docMagnitute);
	     
	     
	     
	     for(int i = 0; i<querywords.length; i++)
	     {
	    	 queryMagnitute = queryMagnitute + (getCount(querywords[i], docfreq) * getCount(querywords[i], docfreq) * queryfreq.get(querywords[i]) * queryfreq.get(querywords[i]));
	   
	    // System.out.println(getCount(querywords[i], docfreq));
	     }
	     	queryMagnitute = Math.sqrt(queryMagnitute);
	     	
	     	
	     for(int i=0 ; i<querywords.length; i++)
	     {
	    	 
	    	 
	    	 
	    	// Numerator = Numerator + ( getCount(querywords[i], tf_idf) * (queryfreq.get(querywords[i]) * docfreq.get(querywords[i]) ));
	    	
	    	
	    	 Numerator = Numerator + ( getCount(querywords[i], tf_idf) * (queryfreq.get(querywords[i]) *  getcountwords(docfreq, querywords[i]) ));
	    	
	    	 
	    	 
	    	 
	    	 
	     }
	     	
	   score = Numerator/(queryMagnitute*docMagnitute);  	
	     	
	   
	   scores.put(corpus[x].toString(), score);
	
	}
		
		
	 Map<String, Double> sortedMap = sortByValue(scores);
	 
	 String baseoutputpath = new File("Task1-Cosine").getAbsolutePath();
	 
	 FileWriter writer = new FileWriter(new File(baseoutputpath+"\\"+z + ".txt" ));
	 
	 int o=1;
	 for(String indexwords : sortedMap.keySet())
	 {
		
	
		 String filename = indexwords.substring( basepath.length()+1 , indexwords.length()-4);
	
//		 System.out.println(o+"\t"+filename +"    ->  " +sortedMap.get(indexwords));
			
		 
		 //writer.write(indexwords +"    ->     " + sortedMap.get(indexwords)+"\n\n");
		 
		// writer.write(""+getQuerryID(query)+"\t"+"Q0\t"+filename+"\t"+o+"\t"+sortedMap.get(indexwords)+"\t hp\n\n");
		 
		 writer.write(o++ +".\t"+ filename+"\t"+ sortedMap.get(indexwords)+"\n");
		 
		 
		 if(o==101)
		 {
			 break;
		 }
		 //o++; 
	 }
	 writer.flush();
	 writer.close(); 
	 
	}
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static double getcountwords (TreeMap<String, Double> docfreq , String indexwords)
	{
		if(docfreq.containsKey(indexwords))
		{
			return docfreq.get(indexwords);
		}
		return 0;
		
	}
	
	
	
	
	
	
	  public static TreeMap<String, Double> getDocFreq( TreeMap<String, Double> docfreq)
	   {
	      Scanner wordFile = null;
	      String word = "";     // A word read from the file
	      double count;   // The number of occurrences of the word
	      int termfreqcount=0;
	      
	      String basepath = new File("ProjectCorpus").getAbsolutePath();
	      
	      File directory = new File(basepath);
	      File[] corpus = directory.listFiles();
	      
	      
	      
	//**FOR LOOP TO READ THE DOCUMENTS**
	 for (int x=0; x<corpus.length; x++)
	{ 
		   TreeMap<String, Integer> frequencyData = new TreeMap<String, Integer>( );
	      try
	      {
	         wordFile = new Scanner(new FileReader(corpus[x]));
	      }
	      catch (FileNotFoundException e)
	      {
	       System.err.println(e);
	    
	      }
	  
	      
	      
	      while (wordFile.hasNext( ))
	      {
	          // Read the next word and get rid of the end-of-line marker if needed:
	  word = wordFile.next( );
	  //System.out.println("\n "+word);
	    
	    
	   // Get the current count of this word, add one, and then store the new count:
	        frequencyData.put(word, 1);
	        
	      }
	      
	      for(String indexword : frequencyData.keySet())
	      {
	    	 
	    	  count = getCount(indexword, docfreq) + 1;
	          docfreq.put(indexword, count);
	      }
	      
	     }
	
	 		for(String indexwords : docfreq.keySet())
	 		{
	 			double idf = docfreq.get(indexwords);
	 			double newidf = Math.log(3204/idf);
	 			docfreq.put(indexwords, newidf);
	 		}
	 
	 
	 
	 return docfreq;
	   }
	  
	    public static double getCount(String word, TreeMap<String, Double> frequencyData)
	     {
	      if (frequencyData.containsKey(word))
	      {  // The word has occurred before, so get its count from the map
	   return frequencyData.get(word); // Auto-unboxed
	      }
	      else
	      {  // No occurrences of this word
	         return 0;
	      }
	     }

		@SuppressWarnings("unchecked")
		public static Map<String, Double> sortByValue(Map<String, Double> map) {
	        List list = new LinkedList(map.entrySet());
	        Collections.sort(list, new Comparator() 
	        {

	            @SuppressWarnings("rawtypes")
				@Override
	            public int compare(Object o1, Object o2) {
	                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
	            }
	        });
	        Map result = new LinkedHashMap();
	        for (Iterator it = list.iterator(); it.hasNext();) 
	        {
	            Map.Entry entry = (Map.Entry) it.next();
	            result.put(entry.getKey(), entry.getValue());
	        }
	        return result;
	    }
}
