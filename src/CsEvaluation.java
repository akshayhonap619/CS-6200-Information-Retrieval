import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class CsEvaluation
{
	private static File f = new File("ProjectCorpus");
	private static String path = f.getAbsolutePath();;    
	private static File folder = new File(path);
	private static File[] listOfFiles = folder.listFiles();
	private static String base_addr = folder.toString();
	
	private static TreeMap<String, Double> idf = new TreeMap<String, Double>();
	private static TreeMap<String, Double> query_vector = new TreeMap<String, Double>();
	private static TreeMap<String, Double> cosine = new TreeMap<String, Double>();
	private static TreeMap<String, ArrayList<Integer>> index_idf_uni = new TreeMap<String, ArrayList<Integer>>();
	
	public static Map<String, Double> sortByValue(Map<String, Double> map) 
	{
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() 
        {
            @SuppressWarnings("rawtypes")
			@Override
            public int compare(Object o1, Object o2) 
            {
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
	
	public TreeMap<String, Double> getCosineScores (String q, String qid , String basepath, String originalq) throws IOException
	{
		BufferedReader br;
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		cal_idf();
	    
		String[] querywrd = originalq.split(" ");
		ArrayList<String> qwords = new ArrayList<>();
		for(int i=0;i<querywrd.length;i++)
		{
			qwords.add(querywrd[i]);
		}
		
	    //building query vector
	    String[] query = q.split(" ");
	    
	    for(int j=0; j<query.length; j++)
	    {
	    	if(query_vector.containsKey(query[j]))
	    	{
				Double doc_freq = query_vector.get(query[j]);										
				query_vector.put(query[j],(doc_freq+1));
			}
			else
			{
				query_vector.put(query[j], (double) 1);
			}
	    }
	    	    
	    for(int i=0; i<listOfFiles.length; i++)
	    {
	    	TreeMap<String, Integer> index_doc_count = new TreeMap<String, Integer>();
	    	String fileContent=""; 
	    	br = new BufferedReader(new FileReader(listOfFiles[i]));
		    String buffer="";
	    	while((buffer=br.readLine())!=null)
	    	{
	    		fileContent= fileContent+buffer;
	    	}	    	
		    String[] unigrams = fileContent.split(" ");
		    
		    for(int j=0; j<unigrams.length; j++)
		    {
		    	if(index_doc_count.containsKey(unigrams[j]))
		    	{
					Integer doc_freq = index_doc_count.get(unigrams[j]);										
					index_doc_count.put(unigrams[j],(doc_freq+1));
				}
				else
				{
					index_doc_count.put(unigrams[j], 1);
				}
		    }
		    //numerator of cosine (dot product)
		    Double weight = 0.0;
		    for(String str : query_vector.keySet())
		    {
		    	double temp=0.0;
		    	if(index_doc_count.containsKey(str))
		    	{
		    		
		    		/*if(qwords.contains(str) == true)
		    		{
		    			temp = temp + (double)index_doc_count.get(str);
				    	temp = temp * Math.pow((double)idf.get(str),10);
		    		}*/
		    	//	else
		    		{	
		    		temp = temp + (double)index_doc_count.get(str);
			    	temp = temp * Math.pow((double)idf.get(str),2);
		    		}
			    	
		    	}
		    	temp = temp * query_vector.get(str);
		    	weight = weight + temp;
	    	}
		    
		    //magnitude of document vector
		    double mag_doc = 0.0;
		    for(String str : index_doc_count.keySet())
		    {
		    	//mag_doc = mag_doc + Math.pow((double)index_doc_count.get(str), 2);
		    	double temp2=0.0;
		    	temp2 = temp2 + (double)index_doc_count.get(str);
		    	temp2 = temp2 * (double)idf.get(str);
		    	mag_doc = mag_doc + Math.pow(temp2,2);
	    	}
		    mag_doc = Math.sqrt(mag_doc);
		    
		    //magnitude of query vector
		    double mag_query = 0.0;
		    for(String str : query_vector.keySet())
		    {
		    	//mag_query = mag_query + Math.pow((double)query_vector.get(str), 2);
		    	double temp3=0.0;
		    	temp3 = temp3 + (double)query_vector.get(str);
		    	if(idf.containsKey(str))
		    	{
		    		temp3 = temp3 * (double)idf.get(str);
		    	}
		    	mag_query = mag_query + Math.pow(temp3,2);
	    	}
		    mag_query = Math.sqrt(mag_query);
		    
		    //computing cosines
		    double cos = weight/(mag_doc*mag_query);
		    cosine.put(listOfFiles[i].toString(), cos);
	    }
	    String mm = qid.substring(0, qid.length()-1);
	    
	    
	    
	    PrintWriter out = new PrintWriter(basepath+"\\"+ qid.trim()+".txt");
	    int q_id = query_id(q); 
	    Map<String, Double> sortedMap = sortByValue(cosine);
	    int rank=0;
	    int count=0;
	    
	    
        for (Entry<String, Double> entry : sortedMap.entrySet()) 
        {
        	if(count<100)
        	{
        		String docID = entry.getKey().substring(base_addr.length()+1, entry.getKey().length()-4);        		
         //   	out.println(q_id +"\tQ0\t"+ docID + "\t" + (++rank) + "\t" + entry.getValue() +"\tGNOE92IP");
            	
            	out.println((++rank) + "\t" + docID + "\t" +  entry.getValue() );
            	count++;
            	
            	
        	}
        	else
        		break;
        }        
	    out.close();
	    return cosine;
	}
	
	public static void cal_idf() throws IOException
	{
		for(int i=0; i<listOfFiles.length; i++)
		{
			TreeMap<String, Integer> index_doc_count = new TreeMap<String, Integer>();
			BufferedReader br = new BufferedReader(new FileReader(listOfFiles[i]));;
	    	String fileContent="";  
		    String buffer="";
	    	while((buffer=br.readLine())!=null)
	    		fileContent= fileContent+buffer;
	    	
		    String[] unigrams = fileContent.split(" ");
		    
		    for(int j=0; j<unigrams.length; j++)
			{	    	
				index_doc_count.put(unigrams[j], 1);					
			}
		    
		    for(String str : index_doc_count.keySet())
			{	    	
		    	ArrayList<Integer> count_per_doc = new ArrayList<Integer>();
				if(index_idf_uni.containsKey(str))
				{										
					count_per_doc = index_idf_uni.get(str);
					count_per_doc.add(i);
					index_idf_uni.put(str, count_per_doc);
				} 
				else
				{
					count_per_doc.add(i);
					index_idf_uni.put(str , count_per_doc);
					
				}
			}	
		    br.close();
		}
		
		double idf_val;
		double df = 0.0;
		for(String str : index_idf_uni.keySet()){
			df = index_idf_uni.get(str).size();
			idf_val = java.lang.Math.log(3204/df);
			idf.put(str, idf_val);
    	}		
	}
	
	public static int query_id(String str)
	{
		if(str.equals("global warming potential"))
			return 1;
		if(str.equals("green power renewable energy"))
			return 2;
		if(str.equals("solar energy california"))
			return 3;
		if(str.equals("light bulb bulbs alternative alternatives"))
			return 4;
		else
			return 5;
	}
}
