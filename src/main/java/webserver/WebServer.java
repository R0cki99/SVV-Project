package webserver;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import webserver.controllers.ErrController;
import webserver.controllers.PathController;
import webserver.utils.ObjectFile;

import java.net.*;
import java.io.*;

import java.util.Scanner;

@SuppressFBWarnings("DM_DEFAULT_ENCODING")
public class WebServer extends Thread {
    private Socket clientSocket;
	private ErrController errController = new ErrController();
	private PathController pathController = new PathController();
	private ObjectFile objectFile = new ObjectFile();

	@SuppressFBWarnings("MS_PKGPROTECT")
	public static String SERVER_STATUS = "STOP_SERVER";
	@SuppressFBWarnings({"DM_EXIT", "EI_EXPOSE_REP2", "SC_START_IN_CTOR"})

	public WebServer(Socket clientSoc, String serverRootWeb, String serverMaintenance) {
		clientSocket = clientSoc;
		if(SERVER_STATUS.equals("EXIT")) System.exit(1);
		if(SERVER_STATUS.equals("RUN_SERVER")) start();
		if(SERVER_STATUS.equals("MAINTENANCE_SERVER")) maintenanceServer();
		if(SERVER_STATUS.equals("STOP_SERVER")) StopServer();
	}

	@SuppressFBWarnings({"DM_DEFAULT_ENCODING", "DM_EXIT"})
	public void run() {
		System.out.println("New Thread Started");

		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String path;
			if ((path = pathController.getPath(is.readLine())) != null) {
				File file = objectFile.openFile(path);
				if (file.exists()) {
					try {
						in = new DataInputStream(new FileInputStream(file));
						objectFile.fileFoundHeader(os, (int) file.length(), file);
						objectFile.sendReply(os, in, (int) file.length());
					} catch (Exception e) {
						errController.errorHeader(os, "Can't Read " + path);
					}
					os.flush();
				} else
					errController.errorHeader(os, "Not Found " + path);
			}
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	public static void initializeServer() {

		System.out.println("Enter SERVER STATUS:\t0: STOP\t1: MAINTENANCE\t2: RUN\t9: EXIT\n");
		System.out.println("CURRENT SERVER STATUS: " + SERVER_STATUS);
		Scanner myObj = new Scanner(System.in);
		if(myObj.nextLine().equals("0")) SERVER_STATUS = "STOP_SERVER";
		if(myObj.nextLine().equals("1")) SERVER_STATUS = "MAINTENANCE_SERVER";
		if(myObj.nextLine().equals("2")) SERVER_STATUS = "RUN_SERVER";
        if(myObj.nextLine().equals("9")) SERVER_STATUS = "EXIT";
		System.out.println("\nNEW CURRENT SERVER STATUS: " + SERVER_STATUS + "\n");

		if(!SERVER_STATUS.equals("EXIT")) initializeServer();
	}


	public void maintenanceServer() {
		try {
			DataInputStream in;
			PrintStream os = new PrintStream(clientSocket.getOutputStream());
			File file = objectFile.openFile("src/main/java/html/maintenance/index.html");
			try {
				in = new DataInputStream(new FileInputStream(file));
				objectFile.fileFoundHeader(os, (int) file.length(), file);
				objectFile.sendReply(os, in, (int) file.length());
			} catch (Exception e) {
				errController.errorHeader(os, "Can't read Maintenance html file");
			}
			os.flush();
			clientSocket.close();
		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	private void StopServer() {
        // do nothing
	}
}