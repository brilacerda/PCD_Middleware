package org.inkulumo.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.inkulumo.connection.IKConnectionActionListener;
import org.inkulumo.exceptions.IKCouldNotConnectToServerException;
import org.inkulumo.exceptions.IKUnimplementedException;
import org.inkulumo.message.IKBytesMessage;
import org.inkulumo.message.IKMessage;
import org.inkulumo.message.IKTextMessage;

public class IKSession implements TopicSession, MessageListener, IKMessageSender {

	private IKConnectionActionListener connectionListener;
	private int acknowledgeMode;
	HashMap<String, List<MessageListener>> topicSubscribers;

	public IKSession(IKConnectionActionListener connectionListener, int acknowledgeMode) {
		this.connectionListener = connectionListener;
		this.acknowledgeMode = acknowledgeMode;
		topicSubscribers = new HashMap<String, List<MessageListener>>();
	}

	@Override
	public TopicSubscriber createSubscriber(Topic topic, String messageSelector, boolean noLocal) throws JMSException {
		IKTopicSubscriber subscriber = new IKTopicSubscriber(topic);
		connectionListener.onSubscribeRequest(topic, this);

		List<MessageListener> listeners = topicSubscribers.get(topic.toString());
		if (listeners == null)
			listeners = new ArrayList<MessageListener>();
		listeners.add(subscriber);
		topicSubscribers.put(topic.toString(), listeners);

		return subscriber;
	}

	@Override
	public TopicPublisher createPublisher(Topic topic) throws JMSException {
		return new IKTopicPublisher(topic, this);
	}

	@Override
	public Topic createTopic(String topicName) throws JMSException {
		connectionListener.onNewTopicRequest(topicName);
		return new IKTopic(topicName);
	}

	@Override
	public void onMessage(Message message) {
		for (MessageListener listener : topicSubscribers.get(((IKMessage) message).getJMSDestination().toString()))
			listener.onMessage(message);
	}

	@Override
	public void send(IKMessage message) throws IKCouldNotConnectToServerException {
		connectionListener.onPublishRequest(message);
	}

	@Override
	public void run() {
		// nothing to do here
	}

	@Override
	public TextMessage createTextMessage() {
		return (TextMessage) new IKTextMessage();
	}

	@Override
	public BytesMessage createBytesMessage() {
		return (BytesMessage) new IKBytesMessage();
	}

	@Override
	public MessageListener getMessageListener() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void setMessageListener(MessageListener messageListener) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MapMessage createMapMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Message createMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ObjectMessage createObjectMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable object) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public StreamMessage createStreamMessage() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TextMessage createTextMessage(String text) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public boolean getTransacted() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public int getAcknowledgeMode() throws JMSException {
		return acknowledgeMode;
	}

	@Override
	public void commit() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void rollback() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void close() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public void recover() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageProducer createProducer(Destination destination) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean NoLocal)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public Queue createQueue(String queueName) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal)
			throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public QueueBrowser createBrowser(Queue queue) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public QueueBrowser createBrowser(Queue queue, String messageSelector) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TemporaryQueue createTemporaryQueue() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TemporaryTopic createTemporaryTopic() throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}

	@Override
	public TopicSubscriber createSubscriber(Topic topic) throws JMSException {
		return createSubscriber(topic, null, false);
	}

	@Override
	public void unsubscribe(String name) throws IKUnimplementedException {
		throw new IKUnimplementedException();
	}
}
