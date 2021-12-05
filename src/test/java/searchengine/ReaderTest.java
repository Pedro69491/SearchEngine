package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


import org.junit.jupiter.api.Test;


public class ReaderTest {

    @Test
    public void TestOfReaderClassWithTestFileTxt() {
        Reader reader = new Reader();
        try {
            List<String> lines = reader.fileReader("data\\test-file.txt");
            reader.checkLines(lines);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assertEquals("*PAGE:http://page2.com", reader.getPages().get(0).getUrl());
            assertEquals("title1", reader.getPages().get(1).getTitle());
        }
    }

}