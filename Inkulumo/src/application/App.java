package application;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.inkulumo.IKEnvironment;
import org.inkulumo.message.IKTextMessage;

public class App {

	public static void main(String[] args) throws NamingException, JMSException {

		Context context = new InitialContext(IKEnvironment.instance());

		TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup("InkulumoConnectionFactory");
		TopicConnection connection = factory.createTopicConnection();
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		final Topic topic = (Topic) context.lookup(IKEnvironment.instance().get(IKEnvironment.ROOMS_TOPIC_KEY));

		TopicSubscriber subscriber = session.createSubscriber(topic);
		final TopicPublisher publisher = session.createPublisher(topic);

		new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner in = new Scanner(System.in);
				while (in.hasNextLine()) {
					try {
						publisher.publish(new IKTextMessage(in.nextLine(), topic));
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}

				in.close();
			}
		}).start();

		while (true) {
			TextMessage message = (TextMessage) subscriber.receive();
			System.out.println(message.getText());
		}
	}
}
