package webserver;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import webserver.controllers.ErrController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@SuppressFBWarnings("DM_DEFAULT_ENCODING")
public class WebServerTest {

    WebServer webServer = null;
    @SuppressFBWarnings("URF_UNREAD_FIELD")
    private ErrController errControllerMock =mock(ErrController.class);;
    private PathController pathControllerMock = mock(PathController.class);;
    private ObjectFile objectFileMock = mock(ObjectFile.class);;
    @Before
    public void setUp() {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void webServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10017);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);
    }


    @SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
    @Test
    public void testMaintenanceServerMock() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10018);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);

        String path = "..\\svv-project\\src\\main\\java\\html\\maintenance\\index.html";
        File file = new File(path);
        assertEquals("Expected a good path for the file", file, objectFileMock.openFile(path));

        String errMessage = "ERROR MESSAGE TEST";
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        webServer.maintenanceServer();
    }

    @SuppressFBWarnings({"DLS_DEAD_LOCAL_STORE", "RU_INVOKE_RUN"})
    @Test
    public void testRunMock() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10019);
        Socket clientSocket = serverSocket.accept();
        webServer = new WebServer(clientSocket);

        assertEquals("Expected a good path", "src/main/java/html/index/index.html", pathControllerMock.getPath("GET / HTTP/1.1"));

        String path = "..\\svv-project\\src\\main\\java\\html\\index\\index.html";
        File file = new File(path);
        assertEquals("Expected a good path for the file", file, objectFileMock.openFile(path));

        String errMessage = "ERROR MESSAGE TEST";
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        webServer.run();
    }
}