package searchengine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Comparator;

/**
 * Ranking interface which sorts a HashMap on a desceding order.
 * Implemented by: TermFrequency and FrequencyInverseDocument classes
 * @author Pedro
 */

public interface Ranking {

    public static HashMap<Page, Double> sortMap(HashMap<Page, Double> map) {
        LinkedHashMap<Page, Double> linkedHMap = new LinkedHashMap<>();
        map.entrySet()
        .stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> linkedHMap.put(x.getKey(), x.getValue()));
        return linkedHMap;
    }

}
