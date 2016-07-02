package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.inkulumo.IKEnvironment;
import org.inkulumo.connection.IKConnectionFactory;
import org.inkulumo.net.SmartSocket;

public class ChatServer {
	
	private ServerSocket serverSocket;
	
	public ChatServer() throws UnknownHostException, IOException {
		try {
			LocateRegistry.createRegistry(1099);
			Context context = new InitialContext(IKEnvironment.instance());
			context.bind("InkulumoConnectionFactory", new IKConnectionFactory(InetAddress.getByName("localhost"), 12345));
		} catch (NamingException | RemoteException e) {
			e.printStackTrace();
		}
		
		serverSocket = new ServerSocket(12345);
	}
	
	private void init() throws UnknownHostException, IOException {
		while (true) {
			SmartSocket socket = new SmartSocket(serverSocket.accept());
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatServer server = new ChatServer();
		server.init();
	}
}
