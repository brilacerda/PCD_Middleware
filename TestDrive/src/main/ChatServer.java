package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.inkulumo.IKEnvironment;
import org.inkulumo.connection.IKConnectionFactory;
import org.inkulumo.server.IKServer;
import org.inkulumo.session.IKTopic;

public class ChatServer {

	private IKServer server;
	private final int PORT = 12345;

	public ChatServer() throws UnknownHostException, IOException {
		server = new IKServer(PORT);

		try {
			LocateRegistry.createRegistry(1099);
			Context context = new InitialContext(IKEnvironment.instance());
			TopicConnectionFactory connectionFactory = new IKConnectionFactory(InetAddress.getByName("localhost"), PORT);
			context.bind("InkulumoConnectionFactory", connectionFactory);
			
			String roomsTopicName = IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY);
			context.bind(roomsTopicName, new IKTopic(roomsTopicName));
		} catch (NamingException | RemoteException e) {
			e.printStackTrace();
		}
	}

	private void start() throws UnknownHostException, IOException {
		server.start();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatServer server = new ChatServer();
		server.start();
	}
}
