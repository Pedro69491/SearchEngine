package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class FrequencyInverseDocumentTest {

    FrequencyInverseDocument test;
    HashSet<Page> matchedPages;
    List<Page> pagesInTheFile;
    
    @BeforeAll
    void setUp() {
        matchedPages = new HashSet<>();
        matchedPages.add(new Page("url1.com", "title1", Arrays.asList("word1", "word2", "word6")));
        matchedPages.add(new Page("url2.com", "title2", Arrays.asList("word2", "word3")));


        pagesInTheFile = new ArrayList<>();
        pagesInTheFile.add(new Page("url1.com", "title1", Arrays.asList("word1", "word2")));
        pagesInTheFile.add(new Page("url2.com", "title2", Arrays.asList("word2", "word3")));
        pagesInTheFile.add(new Page("url5.com", "title5", Arrays.asList("word5")));
        test = new FrequencyInverseDocument(matchedPages, pagesInTheFile);
    }

    @Test
    public void ScoreSingleWords() {
        setUp();
        var mp = test.singleWords("word1");
        double expectedValue = 0.17609125905568124;
        for (double entry : mp.values()) {
            assertEquals(expectedValue, entry);
        }
    }

    @Test
    public void ScoreMultipleWords(){
        setUp();
        List<Double> expectedList = Arrays.asList( 0.52827377716704372624386702559187, 0.17609125905568124);
        List<Double> actualList = new ArrayList<>();
        var mp = test.multipleWords(Arrays.asList("word1", "word2", "word6"));
        for (double entry : mp.values()) {
            actualList.add(entry);
        }
        assertEquals(expectedList, actualList);
    }

    @Test
    public void ScoreMergedWords(){
        setUp();
        List<Double> expectedList = Arrays.asList(0.3521825181113625, 0.17609125905568124);
        List<Double> actualList = new ArrayList<>();
        var mp = test.mergedWords(Arrays.asList(Arrays.asList("word2", "word3"), Arrays.asList("word2")));
        for (double entry : mp.values()) {
            actualList.add(entry);
        }
        assertEquals(expectedList, actualList);
    }

}
