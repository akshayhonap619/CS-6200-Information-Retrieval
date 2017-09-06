import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.lucene.queryparser.classic.ParseException;
import org.omg.CORBA.IdentifierHelper;

public class Evaluator_Superclass {

	public static void main(String[] args) throws IOException, ParseException 
	{
		
	tf_idf_Evaluator id = new tf_idf_Evaluator();
	
	Cosine_Similarity_Evaluation cse = new Cosine_Similarity_Evaluation();
	
	BM25_Evaluator bm = new BM25_Evaluator();
	
	LinkedHashMap<String, ArrayList<String>>  ReleventDocs1 = id.getRetdocs();
	
	LinkedHashMap<String, ArrayList<String>>  ReleventDocs2 = cse.getRetdocs();

	LinkedHashMap<String, ArrayList<String>>  ReleventDocs3 = bm.getRetdocs();

	
	Evaluator e = new Evaluator();
	
	e.evaluatePerformance(ReleventDocs1 , new File("Task1_tf_idf_Eval.csv") );
	
	e.evaluatePerformance(ReleventDocs2 , new File("Task1_Cosine_Eval.csv"));
	
	e.evaluatePerformance(ReleventDocs3 , new File("Task1_BM25_Eval.csv"));
	
	}

}
