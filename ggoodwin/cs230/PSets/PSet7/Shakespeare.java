/** Gabrielle Goodwin
    12/4/2010
    PSet7
    Shakespeare.java

I think that Shakespeare wrote his own plays. He seems to make different word choices than Bacon.

**/
import java.lang.Object.*;
import java.io.*;
import java.util.*;
import java.lang.*;





public class Shakespeare{
    private static Hashtable<String,Integer> wordTable;

    public Shakespeare(){
	wordTable = new Hashtable<String, Integer>();
    }

    public static Hashtable<String,Integer> readInFile(String filename)throws FileNotFoundException{
	Scanner reader = new Scanner(new File(filename));
	String[] words = null;
	String S;
	String oneword;
	while(reader.hasNext()){
	    S = reader.nextLine();
	    if(S.equals("")){
		S = reader.nextLine();
	    }
	    words = S.split(" ");
	    for (int i = 0; i<words.length;i++){
		oneword = clean(words[i]);
		if(!oneword.equals("")){//gets rid of blank words, my program was counting them at first
		if(wordTable.containsKey(oneword)){
		    int o = wordTable.get(oneword);
		    wordTable.remove(oneword);
		    wordTable.put(oneword,(o +1));
		}
		else{
		    wordTable.put(oneword,1);
		}}
	    }}
	reader.close();
	return wordTable;
    }

	
	

    public static String clean(String s){
	s.trim();
	char[] r = s.toCharArray();
	String word = "";
	for(int i =0; i<r.length;i++){
	    char u = r[i];
	    if(Character.isLetter(u)){
		word = word + r[i];
	    }}
	return word.toLowerCase();
    }
    
	public static MaxHeap<String> createHeap(Hashtable<String,Integer> h){
	    Enumeration<String> e = h.keys();
	    MaxHeap m = new MaxHeap();
	    while(e.hasMoreElements()){
		String s = e.nextElement();
		//	if(h.get(s)>=5){ //Saved time trying to scroll to the top of the document
		PQueueEntry p  = new PQueueEntry(h.get(s), s);
		m.insert(p);
	    }
		
	    return m;
	}

	public static String heapSort(MaxHeap<String> heap){
	    String heapString = "";
	    while(!heap.isEmpty()){
		heapString = heapString +"\n"+ heap.delete().toString();
	    }
 
	    return heapString;
	}
    


	    public static void main(String[] args)throws FileNotFoundException{
		Shakespeare s = new Shakespeare();
		if(args.length > 0){
		    s.readInFile(args[0]);
		    System.out.println("read in file");
		    MaxHeap m = s.createHeap(wordTable);
		    System.out.println(s.heapSort(m));
		    
		}else{
		    System.out.println("Please type in the name of a file to be read.");
		}}
  	
					     
	
		


		    


}//end Shakespeare
	    