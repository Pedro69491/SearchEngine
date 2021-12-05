package searchengine;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

public class IndexTest {
    private Index index;
    private HashMap<String, HashSet<Page>> hMap;

    @BeforeAll 
    public void setUp(){
        List<Page> webPages1 = new ArrayList<>();
        webPages1.add(new Page("url1.com", "title1", Arrays.asList("word1", "word2")));
        webPages1.add(new Page("url2.com", "title2", Arrays.asList("word2", "word3")));


        index = new Index(webPages1);
        index.build();
        hMap = index.getInvertedIndex();
        
    }

     @Test
    public void testHashMapKeys() {
        setUp();
        List<String> lst = new ArrayList<>();
        for (String s: hMap.keySet()) {
            lst.add(s);
        }
        assertEquals(lst, Arrays.asList("word1", "word3", "word2"));
    } 

    @Test
    public void testLookUpMethodInInvertedIndexClass(){
        setUp();
        assertEquals(1, index.lookup("word3").size()); 
        assertEquals(1, index.lookup("word1").size());

}
}
