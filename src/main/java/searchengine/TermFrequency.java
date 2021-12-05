package searchengine;
import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;


/**
 * The TermFrequency class implements the Ranking interface, and 
 * calculates the term frequency for one word, multiple words and merged words, 
 * and returns a HashMap in which the page is the key, and the term frequency score
 * is the value.
 * @author Group31
 */

public class TermFrequency implements Ranking {

    private HashMap<Page, Double> map;
    private HashSet<Page> pages;

    public TermFrequency(HashSet<Page> pages){
        map = new HashMap<>();
        this.pages = pages;
    }

    /**
     * Calculates the term frequency for single words.
     * @param word
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> singleWords(String word) {
        for(Page pg: pages){
            double score = Collections.frequency(pg.getListOfWords(), word);
            if (score != 0) {
                map.put(pg, score);
            }
        }
        return Ranking.sortMap(map);
    }


    /**
     * Calculates the term frequency for multiple words.
     * @param words
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> multipleWords(List<String> words) {
        for (Page pg: pages) {
            double score = 0;
            for (String wrd : words) {
                score += Collections.frequency(pg.getListOfWords(), wrd);
            }
            if(score != 0) {
                map.put(pg, score); 
            }
        }
        return Ranking.sortMap(map);
    }

    /**
     * Calculates the term frequency for merged words by "OR".
     * @param words
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> mergedWords(List<List<String>> words) {
        for (Page pg: pages)  {
            double score = 0;
            for (List<String> wrds : words) {
                double newScore = 0;
                for (String wrd : wrds) {
                    newScore += Collections.frequency(pg.getListOfWords(), wrd);
                }
                if (newScore > score) {
                    score = newScore;
                }
            }
            if (score != 0) {
                map.put(pg, score);   
            }
        }
        return Ranking.sortMap(map);

    }

}
