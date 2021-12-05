package searchengine;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The reader class was created to check the datafiles for the given query
 * @author Group 31 
 */
public class Reader{
    
    private List<Page> pages;

    public Reader() {
        pages = new ArrayList<>();
    }

    /**
     * Look up the given filename in the datafiles 
     * @param filename
     * @return ArrayList<Lines>
     * @throws IOException
     * @throws FileNotFoundException
     */
    public List<String> fileReader(String filename) throws IOException, FileNotFoundException{
      List<String> lines = Files.readAllLines(Paths.get(filename));
      return lines;
    }

    /**
     * Checks if a page exists, creates a page object
     * and add it to the list of pages
     * @param lines
     */ 
    public void checkLines(List<String> lines) {
      int lastIndex = lines.size();
      for (int i = lines.size() - 1; i >= 0; --i) { 
        if (lines.get(i).startsWith("*PAGE")) { 
          if (lastIndex-i > 2) {
            String url = lines.get(i);
            String title = lines.get(i+1);
            List<String> listLines = lines.subList(i+1, lastIndex);
            Page pg = new Page(url, title, listLines);
            pages.add(pg);
            lastIndex = i;
          } 
          lastIndex = i;
        }
      }
    }
    /**
     * Throws exceptions if filename not found 
     * @param filename
     */
    public void exceptionsHandler(String filename) {
      try {
        fileReader(filename);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    }
/**
 * Getter method to get the list of websites that are made in the checkline method 
 * @return ArrayList<Page>
 */
    public List<Page> getPages() {
      return pages;
    }

}

