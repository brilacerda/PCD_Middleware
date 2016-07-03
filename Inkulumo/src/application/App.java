package application;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
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

		Topic topic = (Topic) context.lookup(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY));

		TopicSubscriber subscriber = session.createSubscriber(topic);

		while (true) {
			BytesMessage message = (BytesMessage) subscriber.receive();
			System.out.println(message.readUTF());
		}
	}
}
