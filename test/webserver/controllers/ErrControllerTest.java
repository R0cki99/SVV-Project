package webserver.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ErrControllerTest {

    private ErrController errController;
    @Before
    public void setUp() {
        errController = new ErrController();
    }

    @Test
    public void ErrorHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10009);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("Open Browser: http://localhost:10009/");
        String errMessage = "Error Message Received";
        assertEquals("Expected this output: ", "Message sent to:" + os + "With the following message" + errMessage, errController.ErrorHeader(os, errMessage));
        assertNotNull(errController);
    }
}