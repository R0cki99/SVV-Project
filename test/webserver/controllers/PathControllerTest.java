package webserver.controllers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathControllerTest {

    PathController pathController;

    @SuppressFBWarnings("UWF_UNWRITTEN_FIELD")
    @Test
    public void getPath() {
        assertEquals("Expecting index.html", "src/main/java/html/index/index.html", pathController.getPath("GET / HTTP/1.1"));
        assertEquals("Expecting a.html", "src/main/java/html/TestServer/TestServer/a.html", pathController.getPath("GET /TestServer/a.html HTTP/1.1"));
        assertEquals("Expecting styles.css", "src/main/java/html/styles.css", pathController.getPath("GET /styles.css HTTP/1.1"));
        assertEquals("Expecting wrong string", "src/main/java/html/TestServer/FileNotHere", pathController.getPath("GET /FileNotHere HTTP/1.1"));
        assertEquals("Expecting a.txt", "src/main/java/html/TestServe/TestServer/a.txt", pathController.getPath("GET /TestServer/a.txt HTTP/1.1"));
        assertEquals("Expecting c.css", "src/main/java/html/c.css", pathController.getPath("GET /c.css HTTP/1.1"));
        assertEquals("Expecting yes.jpg", "src/main/java/html/TestServe/TestServer/yes.jpg", pathController.getPath("GET /TestServer/yes.jpg HTTP/1.1"));
        assertEquals("Expecting yes.jpg", "src/main/java/html/TestServe/TestServer/aaa/bbb/yes.jpg", pathController.getPath("GET /TestServer/aaa/bbb/yes.jpg HTTP/1.1"));

    }
}
