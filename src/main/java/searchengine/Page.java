package searchengine;
import java.util.List;
  /**
     * The page class was created to handle all of the datafiles
     * @author Group 31
     */

public class Page {

    private List<String> listOfWords;
    private String url;
    private String title;

    public Page(String url, String title, List<String> words){ 
        this.url = url;
        this.title = title;
        listOfWords = words;

    }
    /**
     * Different getter methods was created to call the fields of the class 
     * to other classes.This method returns the title.
     * @return title
     */
    public String getTitle() {
        return title;
    }

     /**
     * This getter method returns the URL.
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * This getter method returns the list of words from a page.
     * @return url
     */
    public List<String> getListOfWords() {
        return listOfWords;
    }

    /**
     * Getter method which returns a string of URL
     */
    public String toString(){
        return this.url;
    }

}
