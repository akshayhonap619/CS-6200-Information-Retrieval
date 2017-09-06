import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryExpEvaluation 
{

	void expandQuery() throws FileNotFoundException, IOException 
	{
		QueryReader qr = new QueryReader();
		Relevant_docs rd = new Relevant_docs();
		ngramsGenerator ng = new ngramsGenerator();
		StopWordsGenerator sg = new StopWordsGenerator();
		TfBuilder tfb = new TfBuilder();
		
		
		ArrayList<String> StopWords = sg.getStopWords();
		
		String basepath = new File("Corpus_No_Stopping").getAbsolutePath();
		
		LinkedHashMap<String, String> queries = qr.getQueries();
		LinkedHashMap<String, ArrayList<String>> relDocs = rd.getReleveantDocs();
		ArrayList<String> ngrams = new ArrayList<>();
		
		BufferedReader br;
		
		
		
		for(int i=1; i<=64 ; i++)   // All queries
		{
			String tempSummary="", Summary = "";
			String q_no = String.valueOf(i);
			String query = queries.get(q_no);
			
		query = removeStopWords(query, StopWords);
		
		System.out.println(q_no);
		
		if(relDocs.containsKey(q_no)==false)
		{
			CsEvaluation cs2 = new CsEvaluation();
			cs2.getCosineScores(query, q_no, "Task2",query);
			continue;
		}
		
		ArrayList<String> relevantDocs = new ArrayList<>();
			relevantDocs = relDocs.get(q_no);
				
			
			for(int j=0;j<relevantDocs.size();j++)
			{
				ArrayList<String> sentences = new ArrayList<>();
				String filename = basepath+"\\"+relevantDocs.get(j)+".txt";
				
				br = new BufferedReader(new FileReader(new File(filename)));
				
				   String buffer ="", filetext = "";
				   	while((buffer = br.readLine())!=null)
			   	 {
			   		filetext = filetext + buffer;
			   	 }  		
				   	tempSummary = getSummary(filetext, query);
				   	
				   	Summary= Summary + tempSummary;
				
			}
			
		
			Summary= filtertext(Summary);
			Summary = removeStopWords(Summary, StopWords);
			
			
			FileWriter w = new FileWriter(new File("D:\\a.txt"));
		//	w.write(Summary);
		//	w.flush();
		//	w.close();
			
			TreeMap<String, Integer> freqWords = tfb.gettfcount(Summary);
					
			String q1="";
			q1 = expandQuery(query, freqWords);
			
			CsEvaluation  cs = new 	CsEvaluation();
			
			
			cs.getCosineScores(q1, q_no,"Task2",query);
		//	cs.getCosineScores(query, "original", "Task2");

	//		w = new FileWriter(new File("D:\\query.txt"));
		//	w.write(query);
		//	w.flush();
		//	w.close();
			
			
		}
		
		
	}
	public static ArrayList<String> getSentences(String query)
	{
		ArrayList<String> sent = new ArrayList<>();
		Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
	    Matcher reMatcher = re.matcher(query);
	    while (reMatcher.find()) 
	    {
	    	sent.add(reMatcher.group());
	        
	    }
	    return sent;
	}
	
	public static String getSummary (String filetext, String query)
	{
		String Summary ="";
		int summaryCount=0;
		ArrayList<String> sentences = new ArrayList<>();
	   	ArrayList<String> ngrams = new ArrayList<>();
		sentences = getSentences(filetext);
	   	
	   	ngramsGenerator ng = new ngramsGenerator();
	   	
	   	ngrams = ng.getNgrams(query);
	   	
	   	
	   	for(int i=0 ; i<ngrams.size() ; i++)
	   	{
	   		for(int j=0; j< sentences.size(); j++)
	   		{
	   			if(sentences.get(j).contains(ngrams.get(i)))
	   			{
	   				Summary = Summary + sentences.get(j);
	   				summaryCount++;
	   				
	   				if(summaryCount==3)
	   				{
	   					return Summary;
	   				}
	   				
	   			}
	   		}
	   	}
	   	
	   	
	 return Summary;  	
	}
	
	public static String filtertext(String txt_str)
	{
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
	    
	    txt_str= txt_str.replace(".", " ");
	    txt_str=txt_str.replaceAll("\\s+", " "); //multiple spaces

	    
	    return txt_str;
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
	
	public static String expandQuery(String query , TreeMap<String, Integer> docfreq)
	{
		query=query+" ";
		String q2="";
		for(int i=0; i<10; i++)
		{
			q2 = q2 + query + " ";
		}
		q2=q2.trim();
		
		String q3 = q2 + " ";
		for(String k : docfreq.keySet())
		{
			q3 = q3 + k + " ";
		}
		q3 = q3.trim();
		return q3;
	}
	
}
