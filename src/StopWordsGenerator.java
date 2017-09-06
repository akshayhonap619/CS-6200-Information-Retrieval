import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWordsGenerator {

	public ArrayList<String> getStopWords() throws IOException 
	{
		String basepath = new File("").getAbsolutePath();
		File stopwordsFile = new File(basepath+"\\common_words");
		
		ArrayList<String> StopWords = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader(stopwordsFile));
		
		String buffer ="", filetext = "";
	   	while((buffer = br.readLine())!=null)
   	 {
	   		StopWords.add(buffer);
	//   		System.out.println(buffer);
	   		
   	 }
		return StopWords;
	}

}
