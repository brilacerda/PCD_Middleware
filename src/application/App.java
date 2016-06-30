package application;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class App {
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		Context context = new InitialContext();
		
		// Queue Connection Factory
		TopicConnectionFactory factory = (TopicConnectionFactory)context.lookup("Inkulumo");
		
		// Queue connection
		TopicConnection connection = factory.createTopicConnection();
		
		// Create a session (boolean transacted, int acknowledgeMode)
		//https://docs.oracle.com/javaee/7/api/javax/jms/TopicConnection.html#createTopicSession-boolean-int-
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//Queue
		Topic topic = (Topic) context.lookup("Middleware");
		
		//Queue Sender
		TopicPublisher publisher = session.createPublisher(topic);
		
		TextMessage msg = session.createTextMessage();
		msg.setText("Are you using a project pattern?");
		publisher.publish(msg);
	}
	
}
