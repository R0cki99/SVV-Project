package webserver.controllers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressFBWarnings("NM_METHOD_NAMING_CONVENTION")
public class ErrControllerTest {

    private ErrController errController;
    @Before
    public void setUp() {
        errController = new ErrController();
    }

    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    @Test
    public void errorHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10009);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("Open Browser: http://localhost:10009/");
        String errMessage = "Error Message Received";
        assertEquals("Expected this output: ", "Message sent to:" + os + "With the following message" + errMessage, errController.errorHeader(os, errMessage));
        assertNotNull(errController);
    }
}