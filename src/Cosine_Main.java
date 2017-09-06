import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Cosine_Main {

	public static void main(String[] args) throws IOException 
	{
		
	CosineSimilarity cs = new CosineSimilarity();
	
	//TreeMap<String, Double> scores = cs.getCosineScores("","");
	
	 //Map<String, Double> sortedMap = sortByValue(scores);
	 
	 
	int i=0; 
//	for(String p : sortedMap.keySet())
	{
		if(i==20){System.exit(0);}
	//	System.out.println(p+"\t"+sortedMap.get(p));
		i++;
	}
 
		
	}
	
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

}
