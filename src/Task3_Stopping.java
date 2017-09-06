import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Task3_Stopping 
{

	public static void main(String[] args) throws FileNotFoundException ,IOException 
	{
		String inputpath = new File("").getAbsolutePath();
		String outputpath = new File("StemmedCorpus").getAbsolutePath();
		inputpath = inputpath + "\\cacm_stem.txt";
		FileWriter writer;
		File stemdocfile = new File(inputpath);
		
		BufferedReader br = new BufferedReader(new FileReader(stemdocfile));
		  String buffer ="", filetext = "";
		   	while((buffer = br.readLine())!=null)
	   	 {
		   		System.out.println(buffer);
		   		filetext = filetext + buffer;
		   		
	   	 }
		   	
		   	String[] corpusfiles = filetext.split("#");
		   	
		   	for(int i=0 ;i < corpusfiles.length ; i++)
		   	{
		   		if(i==0)
		   		{
		   			continue;
		   		}
		   		String filename = "CACM-"+checkLength(i);
		   		
		   		System.out.println(filename);
		   		corpusfiles[i] = corpusfiles[i].trim();
		   		
		   		corpusfiles[i] = corpusfiles[i].substring(String.valueOf(i).length());
		   		
		   		writer = new FileWriter(new File(outputpath+"\\"+filename+".txt"));
		   		
		   		writer.write(corpusfiles[i]);
		   		writer.flush();
		   		writer.close();
		   		
		   	}
		   	
		   	
		
	}
	
	
	public static String checkLength(int i)
	{
		String doc_id = String.valueOf(i);
		
		if(doc_id.length()==1)
		{
			doc_id = "000"+doc_id;
			return doc_id;
		}
		
		if(doc_id.length()==2)
		{
			doc_id = "00"+doc_id;
			return doc_id;
		}
		
		if(doc_id.length()==3)
		{
			doc_id = "0"+doc_id;
			return doc_id;
		}
		
		else
		{
			return doc_id;
		}
	}

}
