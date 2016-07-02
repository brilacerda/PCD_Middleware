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

import org.inkulumo.IKEnvironment;

public class App {

	public static void main(String[] args) throws NamingException, JMSException {

		Context context = new InitialContext(IKEnvironment.instance());

		TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("InkulumoConnectionFactory");
		TopicConnection connection = factory.createTopicConnection();
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topic = (Topic) context.lookup("Middleware");

		TopicPublisher publisher = session.createPublisher(topic);

		publisher.setTimeToLive(30000);

		TextMessage msg = session.createTextMessage();
		msg.setText("Are you using a project pattern?");
		publisher.publish(msg);
	}
}
