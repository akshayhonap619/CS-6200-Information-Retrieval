import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Evaluator {

	public void evaluatePerformance(LinkedHashMap<String, ArrayList<String>> RetrievedDocs , File output) throws IOException 
	{
//		Cosine_Similarity_Evaluation cse = new Cosine_Similarity_Evaluation();
		
//		LinkedHashMap<String, ArrayList<String>> RetrievedDocs = new LinkedHashMap<>();
		
//		RetrievedDocs= cse.getReldocs();
		
		Relevant_docs rd = new Relevant_docs();
		
		LinkedHashMap<String, ArrayList<String>> ReleventDocs = rd.getReleveantDocs();
		
		
	/*	FileWriter w = new FileWriter(new File("D:\\zz.txt"));
		
		for(String k : qw.keySet())
		{
			w.write(k + "\t" + qw.get(k));
			w.write(qw.get(k).size());
			w.write("\n");
			w.write("\n");
			//	System.out.println(k + "\t" + qw.get(k));
			System.out.println(k+"   "+qw.get(k).size());
		
		
	}
	//	w.flush();
	//	w.close();
	*/
		
		
	QueryReader qr = new QueryReader();
	LinkedHashMap<String, String> queries = qr.getQueries();
	int z=0;	
	
	ArrayList<Double> MAP = new ArrayList<>();
	ArrayList<Double> MRR = new ArrayList<>();
	
	FileWriter writer = new FileWriter(output);
	
	double map=0;
	
	writer.append("Avg_Pre");
	writer.append(',');
	writer.append("P@t5");
	writer.append(',');
	writer.append("P@t20");
	writer.append(',');
	writer.append("Final-Recall");
	writer.append('\n');
	
	for(String q : queries.keySet()) // Each Query
	{
		double precision = 0 , recall = 0;
		
		double total_relevant , total_retrieved =0 , relevent_retrieved = 0;
		
		ArrayList<String> Relevant = ReleventDocs.get(q);
		ArrayList<String> Retrieved = RetrievedDocs.get(q);
		total_relevant=1;
		double sum_precision=0 , avg_precision=0 ;
		double rr=0;
		int reciprocal_flag=0;
		
		double pat5=0 , pat20=0; 
		
		if(Relevant!=null)
		{	
		total_relevant = Relevant.size();
		}
		System.out.println(q);
		System.out.println("total relevant is  "+total_relevant);
		//System.out.println();
		for(int i=0 ; i < Retrieved.size() ; i++)                                 //All retrieved
		{
			
			if( (Relevant!=null) &&  (Relevant.contains(Retrieved.get(i))== true) )
			{
				
				if(reciprocal_flag==0)
				{
					reciprocal_flag=1;
					rr = 1/(i+1);
					MRR.add(rr);
					System.out.println("Rank is "+(i+1));
				}
				
				relevent_retrieved++;
				total_retrieved++;
				
				precision =  (relevent_retrieved/total_retrieved);
				
				sum_precision= sum_precision + precision;
				
				recall =  (relevent_retrieved/ total_relevant);
			}
			else
			{
				total_retrieved++;
				
				precision =  (relevent_retrieved/total_retrieved);
				
				recall =  (relevent_retrieved/ total_relevant);
			}
			
			if(i==4)
			{
				pat5 = precision;
			}
			
			if(i==19)
			{
				pat20 = precision;
			}
			
			//System.out.println("precision is  "+precision);
			//System.out.println("recall is  "+recall);
			//System.out.println();
		}
		System.out.println("RRR is "+rr);
	
		
		avg_precision = sum_precision / relevent_retrieved;
		
		writer.append(String.valueOf(avg_precision));
		writer.append(',');
		writer.append(String.valueOf(pat5));
		writer.append(',');
		writer.append(String.valueOf(pat20));
		writer.append(',');
		writer.append(String.valueOf(recall));
		writer.append('\n');
		
		MAP.add(avg_precision);	
	} 
		double mp =0;
		
		for(int y = 0 ; y < MAP.size() ; y++)
		{
			mp = mp + MAP.get(y);
		}
		mp = mp / MAP.size();
		
		double mr =0;
		
		for(int y = 0 ; y < MRR.size() ; y++)
		{
			mr = mr + MRR.get(y);
		}
		mr = mr / MAP.size();
		
		writer.append("MEAN AVERAGE PRECISION ->  "+String.valueOf(mp));
		writer.append('\n');
		writer.append("MEAN RECIPROCAL RANK ->  "+String.valueOf(mr));
		writer.append('\n');
		writer.flush();
		writer.close();
}
}