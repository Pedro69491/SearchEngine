package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class QueryHandlerTest {
    private QueryHandler queryHandler;

    @BeforeAll
    public void setUp() throws IOException {
        queryHandler = new QueryHandler("data\\test-file.txt");
    }

    @Test
    public void TestMultipleWordsMethod() throws IOException {
        setUp();
        assertEquals(1, queryHandler.multipleWords(Arrays.asList("word1", "word2")).size());
    } 
    
    @Test
    public void SingleWordTermFrequency() throws IOException {
        setUp();
        assertEquals(2, queryHandler.getMatchingWebPages("word1").size()); 

        List<Double> expectedList = Arrays.asList(2.0, 1.0);
        List<Double> actualList = new ArrayList<>();
        var mp = queryHandler.getMatchingWebPages("word1");
        for (double entry : mp.values()) {
            actualList.add(entry);
        }
        assertEquals(expectedList, actualList);
    }

    @Test
    public void MultipleWordsTermFrequency() throws IOException {
        setUp();
        assertEquals(1, queryHandler.getMatchingWebPages("word1 word2 title1").size());

        var mp = queryHandler.getMatchingWebPages("word1 word2 title1");
        double expectedValue = 4.0;
        for (double entry : mp.values()) {
            assertEquals(expectedValue, entry);
        }
    }

    @Test
    public void MergedWordsTermFrequency() throws IOException {
        setUp();
        assertEquals(1, queryHandler.getMatchingWebPages("title1 word2 OR title1 word1").size());
        var mp = queryHandler.getMatchingWebPages("title1 word2 OR title1 word1");
        double expectedValue = 3.0;
        for (double entry : mp.values()) {
            assertEquals(expectedValue, entry);
        }
    }

}
