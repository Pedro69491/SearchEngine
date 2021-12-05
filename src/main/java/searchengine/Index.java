package searchengine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/** 
 * The Index class maps the searchterm to a HashSet of pages 
 * in order to optimize the searchengine. This is done by implementing
 * an Inverted index with a HashMap structure.
 * @author group31
*/

public class Index {
    private HashMap<String, HashSet<Page>> invertedIndex;
    private List<Page> listPages;

public Index(List<Page> list){
    invertedIndex = new HashMap<String, HashSet<Page>>();
    this.listPages = list;
    build();
    }

/**
 * The build method creates an inverted index using a HashMap structure
 * @return HashMap<String,HashSet<Page>> 
 */
public HashMap<String,HashSet<Page>> build() {
    for(Page pg : listPages) {
        for(String word : pg.getListOfWords()) {
        
        invertedIndex.putIfAbsent(word, new HashSet<>());

        invertedIndex.get(word).add(pg);
        } 
    }
    return invertedIndex;
    }

/**
 * This lookUp method are given a searchterm as a parameter and looks through
 * the map that has the word, and returns the HashSet that contains the searchterm
 * @param searchterm
 * @return HashSet<Page>
 */
public HashSet<Page> lookup(String searchterm){
    return invertedIndex.get(searchterm);
}


/**
 * Getter method which allows to call the inverted index. 
 * Only used for testing, since it is the same as calling the build() method above.
 * 
 * @return HashMap<String, HashSet<Page>
 */
public HashMap<String, HashSet<Page>> getInvertedIndex() {
    return invertedIndex;
}


}

