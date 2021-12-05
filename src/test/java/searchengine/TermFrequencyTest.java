package searchengine;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class TermFrequencyTest {
    TermFrequency term;
    HashSet<Page> pages;

@BeforeAll
void setUp(){
    pages = new HashSet<>();
    
    pages.add(new Page("url1.com", "title1", Arrays.asList("word1", "word2")));
    pages.add(new Page("url2.com", "title2", Arrays.asList("word2", "word3")));
    pages.add(new Page("url3.com", "title3", Arrays.asList("word5", "word6", "word7")));
    term = new TermFrequency(pages);
}

        
@Test
public void ScoreSingleWords(){
    setUp();
    var actual = term.singleWords("word1");
    double expectedValue = 1.0;
    for (Map.Entry<Page, Double> entry : actual.entrySet()) {
            assertEquals(expectedValue, entry.getValue());
    }
}

@Test
public void ScoreMultipleWords(){
    setUp();
    var actual = term.multipleWords(Arrays.asList("word5", "word6"));
    double expectedValue = 2.0;
    for (Map.Entry<Page, Double> entry : actual.entrySet()) {
        assertEquals(expectedValue, entry.getValue());
    }
}

@Test
public void ScoreMergedWords(){
    setUp();
    var actual = term.mergedWords(Arrays.asList(Arrays.asList("word5", "word6", "word7"), Arrays.asList("word5", "word6")));
    double expectedValue = 3.0;
    for (Map.Entry<Page, Double> entry : actual.entrySet()) {
            assertEquals(expectedValue, entry.getValue());
        }
    }
}
