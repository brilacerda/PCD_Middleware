package main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.inkulumo.IKEnvironment;
import org.inkulumo.connection.IKConnectionFactory;
import org.inkulumo.server.IKServer;
import org.inkulumo.session.IKSession;

public class ChatServer {

	private IKServer server;

	public ChatServer() throws UnknownHostException, IOException {
		server = new IKServer(12345);

		try {
			LocateRegistry.createRegistry(1099);
			Context context = new InitialContext(IKEnvironment.instance());
			TopicConnectionFactory connectionFactory = new IKConnectionFactory(InetAddress.getByName("localhost"), 12345);
			context.bind("InkulumoConnectionFactory", connectionFactory);
		} catch (NamingException | RemoteException e) {
			e.printStackTrace();
		}
	}

	private void init() throws UnknownHostException, IOException {
		server.start();
		createRoomsTopic();
	}

	private void createRoomsTopic() {
		try {
			Context context = new InitialContext(IKEnvironment.instance());
			TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("InkulumoConnectionFactory");
			TopicConnection connection = connectionFactory.createTopicConnection();
			TopicSession session = connection.createTopicSession(false, IKSession.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY));
			context.bind(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY), topic);
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatServer server = new ChatServer();
		server.init();
	}
}
