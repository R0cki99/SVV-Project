package webserver.utils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class ObjectFileTest {

    private ObjectFile objectFile;
    private int fileLength;


    @SuppressFBWarnings({"UWF_UNWRITTEN_FIELD", "DM_DEFAULT_ENCODING"})
    @Test
    public void fileFoundHeader() throws IOException {
        ServerSocket serverSocket = new ServerSocket(10004);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10004/");

        File file = new File("src/main/java/html/index/index.html");
        String expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/html" + " with file length:" + fileLength;
        assertEquals("Expected output on screen after refresh socket 1004", expectedOutput, objectFile.fileFoundHeader(os, fileLength, file));

        file = new File("src/main/java/html/TestServer/yes.jpg");
        expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "image/jpg" + " with file length:" + fileLength;
        assertEquals("Expected output on screen after refresh socket 1004", expectedOutput, objectFile.fileFoundHeader(os, fileLength, file));

        file = new File("..\\svv-project\\src\\main\\java\\html\\index\\TestServer\\ye s.js");
        expectedOutput = "Error when checking the file";
        assertEquals("Expected output on screen after refresh socket 1004", expectedOutput, objectFile.fileFoundHeader(os, fileLength, file));

        file = new File("src/main/java/html/TestServer/a.css");
        expectedOutput = "Message sent to:" + os + " the file" + file + " content-type: " + "text/css" + " with file length:" + fileLength;
        assertEquals("Expected output on screen after refresh socket 1004", expectedOutput, objectFile.fileFoundHeader(os, fileLength, file));
    }

    @SuppressFBWarnings("DMI_HARDCODED_ABSOLUTE_FILENAME")
    @Test
    public void openFileNotHere() {
        assertEquals("Expected result a new file", objectFile.openFile("FileNotHere"), new File("FileNotHere"));
        assertEquals("Expected result a new file", objectFile.openFile("/FileNotHere"), new File("FileNotHere"));
    }
/*
    @Test
    public void sendReply() throws IOException {

        ServerSocket serverSocket = new ServerSocket(10009);
        Socket clientSocket = serverSocket.accept();
        PrintStream os = new PrintStream(clientSocket.getOutputStream());
        System.out.println("OPEN BROWSER: http://localhost:10009/");

        DataInputStream in = new DataInputStream(new FileInputStream(new File("src/main/java/html/index/index.html")));
        assertEquals("Expected output to succeed ", "Successfully sending the reply " + os, objectFile.sendReply(os, in, (int) new File("..\\svv-project\\src\\main\\java\\html\\index\\index.html").length()));


    }*/
}