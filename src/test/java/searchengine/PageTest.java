package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PageTest {

    @Test
    public void TestOfGettingPageTitle(){
    List<String> listOfWords = new ArrayList<>();
    listOfWords.add("word1");
    Page page = new Page("url1.com", "title1", listOfWords);
    String title = page.getTitle();
    assertEquals("title1", title);
    }

    @Test
    public void TestOfGettingURL(){
    List<String> listOfWords = new ArrayList<>();
    listOfWords.add("word1");
    Page page = new Page("url1.com", "title1", listOfWords);
    String url = page.getUrl();
    assertEquals("url1.com", url);
    }

    @Test
    public void TestOfGettingListOfWords(){
    List<String> listOfWords = new ArrayList<>();
    listOfWords.add("word1");
    Page page = new Page("url1.com", "title1", listOfWords);
    List<String> words = page.getListOfWords();
    assertEquals("word1", words.get(0));
    }
}
