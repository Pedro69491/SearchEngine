package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;



public class RankingTest {
    
    @Test
    public void testTheOrder() {
        HashMap<Page, Double> mp = new HashMap<>();
        List<Double> listOfScores = new ArrayList<>();
        mp.put(new Page("url1.com", "title1", Arrays.asList("word1")), 3.5);
        mp.put(new Page("url2.com", "title2", Arrays.asList("word2")), 5.0);
        mp = Ranking.sortMap(mp);
        for (Double score : mp.values()) {
            listOfScores.add(score);
        }
        List<Double> expected = Arrays.asList(5.0, 3.5);
        assertEquals(expected, listOfScores); 
    }
}
