import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ngramsGenerator 
{
	static ArrayList<String> ngrams = new ArrayList<String>();
    public static List<String> ngrams(int n, String str) 
    {
        
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public ArrayList<String> getNgrams(String query)  
    {
    	
          
        for (int n = 1; n <= query.length()-1 ; n++) 
        {
           // for (String ngram : ngrams(n, sent))
            //    System.out.println(ngram);
            //System.out.println();
        	ngrams(n ,  query);
        	
        }
        
        for(int i=0; i <ngrams.size(); i++ )
        {
        //	System.out.println(ngrams.get(i));
        }
      
        if(ngrams.contains(""))
        {
        	ngrams.remove(ngrams.indexOf(""));
        }
        
        ArrayList<String> reverserngrams = new ArrayList<>();
        
        for(int i=ngrams.size()-1; i>=0 ; i--)
        {
        	reverserngrams.add(ngrams.get(i));
        }
        
        return reverserngrams;
        
        
    }
}