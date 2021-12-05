package searchengine; 

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Arrays;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * The WebServer class define how the search engine is running, and what 
 * information is sent back when a word query is searched
 */

public class WebServer {
  static final int PORT = 8080;
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8;

  List<Page> pages = new ArrayList<>();
  HttpServer server;
  QueryHandler queryHandler; 

  WebServer(int port, String filename) throws IOException {

    queryHandler = new QueryHandler(filename); 

    server = HttpServer.create(new InetSocketAddress(port), BACKLOG); 
    server.createContext("/", io -> respond(io, 200, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> search(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, 200, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, 200, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, 200, "text/css", getFile("web/style.css")));
    server.start();
    String msg = " WebServer running on http://localhost:" + port + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }
  
  /**
   * This method displays the URL and title of the webpages searched in the 
   * website by the QueryHandler class
   * @param io
   */
  void search(HttpExchange io) { 
    var array = io.getRequestURI().getRawQuery().split("=|\\+");
    var modifiedArray = Arrays.copyOfRange(array, 1, array.length);
    String searchTerm = String.join(" ", modifiedArray).replaceAll("%20"," ");
    var response = new ArrayList<String>();
    HashMap<Page, Double> map = queryHandler.getMatchingWebPages(searchTerm);
    for (Page pg : map.keySet()) {
      response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",
      pg.getUrl().substring(6), pg.getTitle()));
    }
    var bytes = response.toString().getBytes(CHARSET);
    respond(io, 200, "application/json", bytes);
  }


/**
 * This methods makes sure that a page is added if it contains a searchterm
 * if it is empty it prints out the statement.
 * @param searchTerm
 * @return List<Page>
 */
  List<Page> search(String searchTerm) {
    var result = new ArrayList<Page>();
    for (var page : pages) {
      if (page.getListOfWords().contains(searchTerm)) {
        result.add(page);
      }
    }
    if(result.isEmpty()){
      System.out.println("No web page contains the query word.");
    }
    return result;
  }

  /**
   * returns an array of bytes from the file 
   * @param filename
   * @return byte[]
   */
  byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }

  /**
   * Creates a complete response with headers and body 
   * @param io
   * @param code
   * @param mime
   * @param response
   */
  void respond(HttpExchange io, int code, String mime, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
      io.sendResponseHeaders(200, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }

  public static void main(final String... args) throws IOException {
    var filename = Files.readString(Paths.get("config.txt")).strip();
    new WebServer(PORT, filename); 
  }
}
