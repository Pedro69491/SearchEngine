package searchengine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

/**
 * The QueryHandler handles more advanced searchrequests. 
 * It filters the query as one of three types: 
 * one word, multiple words or merged words(containing "OR") query, 
 * returning a sorted HashMap.
 * @author Group31 
 */

public class QueryHandler {

    private Index index;
    private List<Page> pages; 

    public QueryHandler(String filename) throws IOException{
        Reader reader = new Reader();
        List<String> lines = reader.fileReader(filename);
        reader.checkLines(lines); 
        pages = reader.getPages();
        index = new Index(pages); 
    }

/**
 * The getMatchingWebPages method returns a HashMap with the page as the key, and
 * the score of the page as a value.
 * @param String query
 * @return HashMap<Page, Double>
 */
    public HashMap<Page, Double> getMatchingWebPages(String query) {
        List<String> words = Arrays.asList(query.split(" "));
        if(words.size() == 1 && !words.contains("OR")) {
            HashSet<Page> pgs = index.lookup(query);

            FrequencyInverseDocument fID = new FrequencyInverseDocument(pgs, pages);
            System.out.println(fID.singleWords(query)); 

            TermFrequency tf = new TermFrequency(pgs);
            return tf.singleWords(query);

        } else if (words.size() > 1 && !words.contains("OR")) {
            HashSet<Page> matchedPages = multipleWords(words);

            FrequencyInverseDocument fID = new FrequencyInverseDocument(multipleWords(words), pages);
            System.out.println(fID.multipleWords(words));

            TermFrequency tf = new TermFrequency(matchedPages);
            return tf.multipleWords(words);

        } else {
            String sentence = String.join(" ", words);
            String [] list = sentence.split("OR");
            HashSet<Page> collection = new HashSet<>();
            List<List<String>> total = new ArrayList<>();
            for (String pair : list) {
                List<String> listWrd = Arrays.asList(pair.trim().split(" "));
                HashSet<Page> set = multipleWords(listWrd);
                total.add(listWrd);
                collection.addAll(set); 
            }
            FrequencyInverseDocument fID = new FrequencyInverseDocument(collection, pages);
            System.out.println(fID.mergedWords(total));

            TermFrequency tf = new TermFrequency(collection);
            return tf.mergedWords(total); 
            
        }
    }

    /**
     * This method uses the retainAll method  
     * and returns a HashSet of pages
     * containing all the intersected pages 
     * from the elements of the List passed as argument.
     * @param List<String> words
     * @return HashSet<Page>
     */
    public HashSet<Page> multipleWords(List<String> words) {
            /* System.out.println(words); */
            HashSet<Page> set = new HashSet<>();
            set = index.lookup(words.get(0));
            /* System.out.println(set); */
            for (String word : words){
                set.retainAll(index.lookup(word));
            }
            return set;
    }
}



   
