import java.util.TreeMap;

public class TfBuilder {

	public TreeMap<String, Integer> gettfcount(String args) 
	{
		TreeMap<String, Integer> docfreq = new TreeMap<>();
		
		String[] splitter = args.split(" ");
		
		for(int i=0; i< splitter.length ; i++)
		{
			if(docfreq.containsKey(splitter[i]))
			{
				int count = docfreq.get(splitter[i]);
				docfreq.put(splitter[i],count+1);
				
			}
			else
			{
				docfreq.put(splitter[i],1);
			}
		}
		return docfreq;
	}

}
