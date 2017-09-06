import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Task3A_Evaluator 
{
	static int qid=0;
	private static Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
	private static Analyzer sAnalyzer = new SimpleAnalyzer(Version.LUCENE_47);
	public static String outputpath = new File("Task3A_Results").getAbsolutePath();
	private IndexWriter writer;
	private ArrayList<File> queue = new ArrayList<File>();
	
	public static String basepath = new File("Index").getAbsolutePath()+"\\Task3AEv";
	
	
	
	
	public static void main(String[] args) throws IOException, ParseException {
		TreeMap <Integer,String> query2=new TreeMap<Integer,String>();
		LinkedHashMap <String, String> query = new LinkedHashMap();
		
		String filepath = new File("StoppedCorpus").getAbsolutePath();
		
		ArrayList<String>qTerms=new ArrayList<String>();
		String indexLocation = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
		//String s = br.readLine();
		String s = basepath;
		Lucene2 indexer = null;
		try {
			indexLocation = s;
			indexer = new Lucene2(s);
		} catch (Exception ex) {
			System.out.println("Cannot create index..." + ex.getMessage());
			System.exit(-1);
		}

		// ===================================================
		// read input from user until he enters q for quit
		// ===================================================
	//	while (!s.equalsIgnoreCase("q")) 
		{
			try {
			//	System.out
			//	.println("Enter the FULL path to add into the index (q=quit): (e.g. /home/mydir/docs or c:\\Users\\mydir\\docs)");
			//	System.out
			//	.println("[Acceptable file types: .xml, .html, .html, .txt]");
				s="";
				basepath="";
				basepath = new File("StoppedCorpus").getAbsolutePath();
				s = basepath;
				if (s.equalsIgnoreCase("q")) 
				{
			//		break;
				}

				// try to add file into the index
				indexer.indexFileOrDirectory(s);
			} catch (Exception e) {
			//	System.out.println("Error indexing " + s + " : "
			//			+ e.getMessage());
			}
		}

		// ===================================================
		// after adding, we always have to call the
		// closeIndex, otherwise the index is not created
		// ===================================================
		indexer.closeIndex();

		// =========================================================
		// Now search
		// =========================================================
		//IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
			//	indexLocation)));
		//IndexSearcher searcher = new IndexSearcher(reader);
		//TopScoreDocCollector collector = TopScoreDocCollector.create(100, true);
	
		
		
		QueryReader qr = new QueryReader(); 
		
		query = qr.getQuerieswithStopWords();
		
		LinkedHashMap<String, ArrayList<String>> Results = new LinkedHashMap<>();
	   	
		
		for(String t:query.keySet())
		{
		  
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
					indexLocation)));
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(100, true);

			qid+=1;
			//System.out.println(query);
		//	System.out.println("Query is  "+query.get(t));
			Query q = new QueryParser(Version.LUCENE_47, "contents",
						sAnalyzer).parse(query.get(t));
				searcher.search(q, collector);
				ScoreDoc[] hits = collector.topDocs().scoreDocs;

				// 4. display results
			/*	text_to_print+="Found " + hits.length + " hits. # ";
				for (int i = 0; i < hits.length; ++i) {
					int docId = hits[i].doc;
					Document d =searcher.doc(docId);
					//text_to_print+=qid+" Q0 "+ d.get("path")+" "+(i + 1)+ " score=" + hits[i].score+"Lucene_System # ";
				
			*/
				// 4. display results
				File answerfile = new File(outputpath+"\\"+qid+".txt");
			//	FileWriter writer = new FileWriter(answerfile);
				ArrayList<String> Docs = new ArrayList<>(); 
				//System.out.println("Found " + hits.length + " hits.");
				for (int i = 0; i < hits.length; ++i) 
				{
				    int docId = hits[i].doc;
				    Document d = searcher.doc(docId);
				   
				    String m = d.get("path");
				    m=m.substring(filepath.length()+1);
				    m=m.substring(0,m.length()-4);
				    
				//    writer.write((i + 1) + ".\t" + m               //  d.get("path")
				//	    + "\t" + hits[i].score);
				    
				 //   writer.write("\n");
				
				    Docs.add(m);
				    System.out.println((i + 1) + ". " + m              //d.get("path")
					    + " score=" + hits[i].score);
				}
				Results.put(t,Docs);
			//	writer.flush();
			//	writer.close();
			    // 5. term stats --> watch out for which "version" of the term
				// must be checked here instead!
				Term termInstance = new Term("contents", s);
				long termFreq = reader.totalTermFreq(termInstance);
				long docCount = reader.docFreq(termInstance);
				System.out.println(s + " Term Frequency " + termFreq
						+ " - Document Frequency " + docCount);

			}
		
		System.out.println( Results.size());
		
	
		FileWriter w = new FileWriter(new File("D:\\zz.txt"));
		
//		for(String k : Results.keySet())
		{
//			w.write(k + "\t" + Results.get(k));
//			w.write(Results.get(k).size());
//			w.write("\n");
//			w.write("\n");
			//	System.out.println(k + "\t" + qw.get(k));
//			System.out.println(k+"   "+Results.get(k).size());
		
		
	}
//		w.flush();
//		w.close();
	
		
		
	Evaluator e = new Evaluator();
	e.evaluatePerformance(Results , new File("Task3A_Eval.csv"));
	
	}
		
		

	
		
	/**
	 * Constructor
	 * 
	 * @param indexDir
	 *            the name of the folder in which the index should be created
	 * @throws java.io.IOException
	 *             when exception creating index.
	 */
	Task3A_Evaluator(String indexDir) throws IOException {

		FSDirectory dir = FSDirectory.open(new File(indexDir));

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47,
				sAnalyzer);

		writer = new IndexWriter(dir, config);
	}

	/**
	 * Indexes a file or directory
	 * 
	 * @param fileName
	 *            the name of a text file or a folder we wish to add to the
	 *            index
	 * @throws java.io.IOException
	 *             when exception
	 */
	public void indexFileOrDirectory(String fileName) throws IOException {
		// ===================================================
		// gets the list of files in a folder (if user has submitted
		// the name of a folder) or gets a single file name (is user
		// has submitted only the file name)
		// ===================================================
		addFiles(new File(fileName));

		int originalNumDocs = writer.numDocs();
		for (File f : queue) {
			FileReader fr = null;
			try {
				Document doc = new Document();

				// ===================================================
				// add contents of file
				// ===================================================
				fr = new FileReader(f);
				doc.add(new TextField("contents", fr));
				doc.add(new StringField("path", f.getPath(), Field.Store.YES));
				doc.add(new StringField("filename", f.getName(),
						Field.Store.YES));

				writer.addDocument(doc);
				System.out.println("Added: " + f);
			} catch (Exception e) {
				System.out.println("Could not add: " + f);
			} finally {
				fr.close();
			}
		}

		int newNumDocs = writer.numDocs();
		System.out.println("");
		System.out.println("************************");
		System.out
		.println((newNumDocs - originalNumDocs) + " documents added.");
		System.out.println("************************");

		queue.clear();
	}

	private void addFiles(File file) {

		if (!file.exists()) {
			System.out.println(file + " does not exist.");
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addFiles(f);
			}
		} else {
			String filename = file.getName().toLowerCase();
			// ===================================================
			// Only index text files
			// ===================================================
			if (filename.endsWith(".htm") || filename.endsWith(".html")
					|| filename.endsWith(".xml") || filename.endsWith(".txt")) {
				queue.add(file);
			} else {
				System.out.println("Skipped " + filename);
			}
		}
	}

	
	public void closeIndex() throws IOException 
	{
		writer.close();
	}
}