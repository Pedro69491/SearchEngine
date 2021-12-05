package searchengine;

import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The FrequencyInverseDocument calculates the term frequency-inverse document score 
 * and implements the Ranking interface, 
 * returning a HashMap<Page, Double> for one, mulitple and merged words.
 * @author Group 31 
 */

public class FrequencyInverseDocument implements Ranking{

    private TermFrequency term;
    private int sampleSize; 
    private HashMap<Page, Double> mp;
    private HashSet<Page> matchedPages;

   
    public FrequencyInverseDocument(HashSet<Page> matchedPages, List<Page> pages) {
        this.matchedPages = matchedPages;
        this.sampleSize = pages.size();
        term = new TermFrequency(matchedPages);
        mp = new HashMap<>();
    }

    /**
     * This method calculates the Inverse Document frequency, and returns the score 
     * in the type double
     * @return double
     */
    public double inverseFrequency() {
        int numOfMatches = matchedPages.size();
        return Math.log10((double) sampleSize / (double) numOfMatches);
    }

    /**
     * This method calculates the term frequency-inverse document score 
     * gets an HashMap as argument , and returns a sorted HashMap 
     * with the page and score in descending order.
     * @param HashMap<Page, Double> mp
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> frequencyInverseDocument(HashMap<Page, Double> mp) {
        for (Map.Entry<Page, Double> entry : mp.entrySet()) {
            double score = inverseFrequency() * entry.getValue(); 
            mp.put(entry.getKey(), score);
        }
        return Ranking.sortMap(mp);
    }

    /**
     * Method for single words, returns a sorted HashMap with a page as a key, and 
     * term frequency-inverse document score as value.
     * @param word
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> singleWords(String word) {
        mp = term.singleWords(word);
        return frequencyInverseDocument(mp);

    }

    /**
     * Method for multiple words, returns a sorted HashMap with a page as a key, and 
     * term frequency-inverse document score as value.
     * @param List<String> words
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> multipleWords(List<String> words) {
        mp = term.multipleWords(words);
        return frequencyInverseDocument(mp);
    }

    /**
     * Method for merged words by "OR", returns a sorted HashMap with a page as a key, 
     * term frequency-inverse document score as value.
     * @param List<List<String>> words
     * @return HashMap<Page, Double>
     */
    public HashMap<Page, Double> mergedWords(List<List<String>> words) {
        mp = term.mergedWords(words);
        return frequencyInverseDocument(mp);
    }

    
}

